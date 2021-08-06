package com.lavanya.api.repository;

import com.lavanya.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository extending JPA repository for persistence of User object.
 * @author lavanya
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {



    /**
     * Query to retrieve a specific user using his username.
     * @param email used to identify the user searched in database.
     * @return the User of interest.
     */
    User findByEmail(String email);

    /**
     * Query to retrieve a specific user using his email address.
     * @param email used to identify the user searched in database.
     * @return boolean false or true if user exists or not.
     */
    Boolean existsByEmail(String email);

    /**
     * Query to retrieve a specific user using his email address.
     * @param email used to identify the user searched in database.
     * @return the User of interest.
     */
//    Optional<User> findByEmail(String email);

    /**
     * Query to retrieve list of all users registered and ordered by lastname.
     * @return list of all Users.
     */
    List<User> findAllByOrderByLastName();

}
