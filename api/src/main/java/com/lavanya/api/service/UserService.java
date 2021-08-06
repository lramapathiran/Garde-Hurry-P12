package com.lavanya.api.service;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.error.UserAlreadyExistException;
import com.lavanya.api.mapper.ChildrenMapper;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service provider for all business functionalities related to User class.
 * @author lavanya
 */
@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChildrenService childrenService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ChildrenMapper childrenMapper;

//    @Autowired
//    private PasswordEncoder bCryptPasswordEncoder;


    /**
     * method to retrieve a particular user identified by its username.
     * @param email of the user of interest to identify in database.
     * @return User object.
     */
    public UserDto findUserByUsername(String email) {

        User user = userRepository.findByEmail(email);

        return userMapper.INSTANCE.userToUserDto(user);
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

        User userSaved = userRepository.save(user);

        return userMapper.INSTANCE.userToUserDto(userSaved);
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
        user.setPassword(this.getUserById(user.getId()).getPassword());
        user.setRoles(this.getUserById(user.getId()).getRoles());
        user.setActive(true);
        user.setValidated(this.getUserById(user.getId()).getValidated());
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
     * method to retrieve a particular user identified by its id.
     * @param id, id of the user of interest to identify in database.
     * @return Optional User object.
     */
    public UserDto getUserById (Integer id) {

        Optional<User> user = userRepository.findById(id);
        UserDto userDto = userMapper.INSTANCE.userToUserDto(user.get());

        userDto.setChildrenDtos(childrenMapper.listChildrenToListChildrenDto(user.get().getChildrens()));

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
}
