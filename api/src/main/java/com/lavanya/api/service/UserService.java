package com.lavanya.api.service;

import com.lavanya.api.error.UserAlreadyExistException;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    @Autowired
//    private PasswordEncoder bCryptPasswordEncoder;

    /**
     * method to retrieve a particular user identified by its username.
     * @param email of the user of interest to identify in database.
     * @return User object.
     */
    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * method to save a newly registered user.
     * @param user that needs to be saved in database.
     */
    public User saveUser(User user) throws UserAlreadyExistException{

        if (this.emailExists(user.getEmail())) {
            throw new UserAlreadyExistException(
                    "Il existe déjà un email avec l'addresse: "
                            + user.getEmail());
        }

        user.setActive(true);
        user.setRoles("ADMIN");
        user.setValidated(false);
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
     * @param user that needs to be updated in database.
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }

    /**
     * method to tag an user profile as verified by updating its profile.
     * @param user that needs to be updated in database.
     */
    public void validateUserProfileByAdmin(User user) {

        user.setValidated(true);
        userRepository.save(user);
    }

    /**
     * method to retrieve a particular user identified by its id.
     * @param id, id of the user of interest to identify in database.
     * @return Optional User object.
     */
    public Optional<User> getUserById (Integer id) {

        return userRepository.findById(id);

    }

    /**
     * method to retrieve all users saved in database and displayed with pagination.
     * @param pageNumber, int to access to the number of User Page to display.
     * @return Page of User.
     */
    public Page<User> getAllUsers(int pageNumber) {
        Sort sort = Sort.by("lastName").ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, 10, sort);

        return userRepository.findAll(pageable);
    }

    /**
     * method to verify if a user to be saved has entered an email already existing in database.
     * @param email, email to verify its existence or not in database.
     * @return boolean, return true or false for when an email already exists in database.
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUserByAdmin(User user) {
        userRepository.delete(user);
    }

}
