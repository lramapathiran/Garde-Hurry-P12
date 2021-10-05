package com.lavanya.api.service;

import com.lavanya.api.configs.JwtTokenProvider;
import com.lavanya.api.dto.AuthBodyDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.dto.UserToRegister;
import com.lavanya.api.error.UserAlreadyExistException;
import com.lavanya.api.mapper.*;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Service provider for all business functionalities related to User class.
 * @author lavanya
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRegistrationMapper userRegistrationMapper;

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
     * @param userToRegister that needs to be saved in database.
     * @return UserDto
     */
    public UserDto saveUser(UserToRegister userToRegister) throws UserAlreadyExistException{

        if (this.emailExists(userToRegister.getEmail())) {
            throw new UserAlreadyExistException(
                    "Il existe déjà un email avec l'addresse: "
                            + userToRegister.getEmail());
        }else{

            User user = userRegistrationMapper.INSTANCE.userToRegisterToUser(userToRegister);
            user.setActive(true);
            user.setRoles("USER");
            user.setValidated(false);


            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setUuid(UUID.randomUUID());

            User userSaved = userRepository.save(user);

            return userMapper.INSTANCE.userToUserDto(userSaved);
        }
    }

    /**
     * method to generate token when login.
     * @param data with login credentials data.
     * @return String as the resulting token.
     */
    public String generateToken(AuthBodyDto data) {
        String username = data.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String role = this.userRepository.findByEmail(username).getRoles();
        String fullName = this.userRepository.findByEmail(username).getFirstName() + " " + this.userRepository.findByEmail(username).getLastName();
        String token = jwtTokenProvider.createToken(username, fullName, role);

        return token;
    }


    /**
     * method to update an already registered user.
     * @param userDto that needs to be updated in database.
     */
    public void updateUser(UserDto userDto) {

        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        User originalUser = this.userRepository.findByEmail(user.getEmail());

        user.setPassword(originalUser.getPassword());
        user.setId(originalUser.getId());
        user.setRoles(originalUser.getRoles());
        user.setActive(true);
        user.setValidated(originalUser.getValidated());

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
     * @return  UserDetails
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
     * method to retrieve a particular user identified by its username.
     * @param username, username of the user of interest to identify in database.
     * @return UserDto object.
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
     * method to retrieve a particular user identified by its uuid.
     * @param userId, uuid of the user of interest to identify in database.
     * @return UserDto object.
     */
    public UserDto getUserById (UUID userId) {

        User user = userRepository.findUserByUuid(userId);
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
     * @return Page of Users.
     */
    public Page<User> getAllUsers(int pageNumber) {
        Sort sort = Sort.by("lastName").ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 10, sort);

        Page<User> userPage = userRepository.findAll(pageable);
        return userPage;
    }

    /**
     * method to retrieve list of all users saved in database.
     * @return list of UserDtos.
     */
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

    /**
     * method to validate or not the profile of an already registered user.
     * @param userDto that needs to have its profile validated or not in database.
     */
    public void updateUserProfileValidationStatus(UserDto userDto) {
        User user = userRepository.findUserByUuid(userDto.getUuid());
        user.setValidated(userDto.getValidated());
        userRepository.save(user);

    }

    /**
     * method to retrieve a list of users resulting after filtering and displayed with pagination.
     * @param pageNumber, int to access to the number of Users Page to display.
     * @param keyword, keyword which the search is based on to filter elements in the list of users.
     * @return Page of User resulting after filtering.
     */
    public Page<UserDto> getAllUsersFiltered(int pageNumber, String keyword) {

        List<User> usersList = userRepository.findFilteredUser(keyword);
        List<UserDto> userDtosList = userMapper.listUserToListUserDto(usersList);

        List<UserDto> listUserDtos;

        Pageable pageable = PageRequest.of(pageNumber - 1, 5);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        int toIndex = Math.min(startItem + pageSize, userDtosList.size());
        listUserDtos = userDtosList.subList(startItem, toIndex);

        Page<UserDto> page = new PageImpl<UserDto>(listUserDtos, PageRequest.of(currentPage, pageSize), userDtosList.size());

        return page;
    }

}
