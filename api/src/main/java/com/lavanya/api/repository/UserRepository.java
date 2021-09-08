package com.lavanya.api.repository;

import com.lavanya.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Query to retrieve list of all users registered and ordered by lastname.
     * @return list of all Users.
     */
    List<User> findAllByOrderByLastName();

    /**
     * Query to retrieve a list of users using pagination based or not with the following filter criteria:
     * keyword that can be lastname, city, area.
     * @param keyword to specify the element to filter the book search with.
     * @return Page of Book.
     */
    @Query("select u from User u where ?1 is null or concat(u.lastName, u.city, u.area) LIKE %?1% order by lastName")
    List<User> findFilteredUser(String keyword);

    /**
     * Query to retrieve a specific user identified by its unique uuid value.
     * @return user.
     */
    User findUserByUuid(UUID uuid);

}
