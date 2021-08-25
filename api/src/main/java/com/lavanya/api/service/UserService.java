package com.lavanya.api.service;

import com.lavanya.api.configs.JwtTokenProvider;
import com.lavanya.api.dto.AuthBodyDto;
import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.error.UserAlreadyExistException;
import com.lavanya.api.mapper.ChildcareMapper;
import com.lavanya.api.mapper.ChildrenMapper;
import com.lavanya.api.mapper.CommentMapper;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.AuthBody;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service provider for all business functionalities related to User class.
 * @author lavanya
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChildrenService childrenService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ChildrenMapper childrenMapper;

    @Autowired
    ChildcareMapper childcareMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    /**
     * method to retrieve a particular user identified by its username.
     * @param username as the email of the user of interest to identify in database.
     * @return User object.
     */
    public User findUserByUsername(String username) {

        User user = userRepository.findByEmail(username);

        return user;
    }

    /**
     * method to save a newly registered user.
     * @param userDto that needs to be saved in database.
     */
    public UserDto saveUser(UserDto userDto) throws UserAlreadyExistException{

        if (this.emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "Il existe déjà un email avec l'addresse: "
                            + userDto.getEmail());
        }

        userDto.setActive(true);
        userDto.setRoles("USER");
        userDto.setValidated(false);
        User user = userMapper.INSTANCE.userDtoToUser(userDto);

        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User userSaved = userRepository.save(user);

        return userMapper.INSTANCE.userToUserDto(userSaved);
    }

    public String generateToken(AuthBodyDto data) {
        String username = data.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String role = this.userRepository.findByEmail(username).getRoles();
        String fullName = this.userRepository.findByEmail(username).getFirstName() + " " + this.userRepository.findByEmail(username).getLastName();
        String token = jwtTokenProvider.createToken(username, fullName, role);

        return token;
    }

    /**
     * method to update password of an already registered user.
     * @param user that needs to be updated in database.
     */
    public void updateUserPassword(User user) {

//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * method to update an already registered user.
     * @param userDto that needs to be updated in database.
     */
    public void updateUser(UserDto userDto) {

        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(this.getUser(user.getEmail()).getPassword()));
        user.setRoles(this.getUser(user.getEmail()).getRoles());
        user.setActive(true);
        user.setValidated(this.getUser(user.getEmail()).getValidated());
        userRepository.save(user);
    }

    /**
     * method to tag an user profile as verified by updating its profile.
     * @param userDto that needs to be updated in database.
     */
    public void validateUserProfileByAdmin(UserDto userDto) {

        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        user.setValidated(true);
        userRepository.save(user);
    }

    /**
     * method to create a UserDetail object based of an user of interest.
     * @param email to retrieve from database the user of interest to generate the UserDetails.
     * @throws UsernameNotFoundException thrown if the user to be retrieved is not found in database.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("utilisateur inexistant");
        }
    }

    /**
     * method to generate list of authorities of a User of interest.
     * @param role of the user used to be added in the list of GrantedAuthority.
     * @return list of GrantedAuthority.
     */
    private List<GrantedAuthority> getUserAuthority(String role) {

        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        return grantedAuthorities;
    }

    /**
     * method to generate the UserDetails after authentication.
     * @param authorities as list of GrantedAuthority associated to the user connected.
     * @param user that is authenticated.
     * @return UserDetails.
     */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    /**
     * method to retrieve a particular user identified by its id.
     * @param username, username of the user of interest to identify in database.
     * @return Optional User object.
     */
    public UserDto getUser (String username) {

        User user = userRepository.findByEmail(username);
        UserDto userDto = userMapper.INSTANCE.userToUserDto(user);

        userDto.setChildrenDtos(childrenMapper.listChildrenToListChildrenDto(user.getChildrens()));
        userDto.setChildcareDtosRequests(childcareMapper.listChildcareToListChildcareDto(user.getChildcareRequests()));
        userDto.setChildcareDtosMissions(childcareMapper.listChildcareToListChildcareDto(user.getChildcareMissions()));
        userDto.setCommentDtosMade(commentMapper.listCommentToCommentDto(user.getCommentsMade()));
        userDto.setCommentDtosReceived(commentMapper.listCommentToCommentDto(user.getCommentsReceived()));

        return userDto;

    }

    /**
     * method to retrieve a particular user identified by its id.
     * @param userId, id of the user of interest to identify in database.
     * @return Optional User object.
     */
    public UserDto getUserById (int userId) {

        User user = userRepository.findById(userId).get();
        UserDto userDto = userMapper.INSTANCE.userToUserDto(user);

        userDto.setChildrenDtos(childrenMapper.listChildrenToListChildrenDto(user.getChildrens()));
        userDto.setChildcareDtosRequests(childcareMapper.listChildcareToListChildcareDto(user.getChildcareRequests()));
        userDto.setChildcareDtosMissions(childcareMapper.listChildcareToListChildcareDto(user.getChildcareMissions()));
        userDto.setCommentDtosMade(commentMapper.listCommentToCommentDto(user.getCommentsMade()));
        userDto.setCommentDtosReceived(commentMapper.listCommentToCommentDto(user.getCommentsReceived()));

        return userDto;

    }

    /**
     * method to retrieve all users saved in database and displayed with pagination.
     * @param pageNumber, int to access to the number of User Page to display.
     * @return Page of UserDto.
     */
    public Page<User> getAllUsers(int pageNumber) {
        Sort sort = Sort.by("lastName").ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 10, sort);

        Page<User> userPage = userRepository.findAll(pageable);
//        Page<UserDto> userDtoPage = userMapper.pageUserToPageUserDto(userPage);
        return userPage;
    }

    public List<UserDto> getAllUsersInList() {
        List<User> listOfUsers = userRepository.findAllByOrderByLastName();
        return userMapper.listUserToListUserDto(listOfUsers);
    }

    /**
     * method to verify if a user to be saved has entered an email already existing in database.
     * @param email, email to verify its existence or not in database.
     * @return boolean, return true or false for when an email already exists in database.
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUserByAdmin(UserDto userDto) {
        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        userRepository.delete(user);
    }


    public void deleteUser(int userDtoToDeleteId) {

        User user = userRepository.findById(userDtoToDeleteId).get();
        userRepository.delete(user);
    }

    public void updateUserProfileValidationStatus(UserDto userDto) {
        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        userRepository.save(user);

    }
}
