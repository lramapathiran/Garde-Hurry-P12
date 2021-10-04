package com.lavanya.api.repository;

import com.lavanya.api.model.Childcare;
import com.lavanya.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository extending JPA repository for persistence of Childcare object.
 * @author lavanya
 */
@Repository
public interface ChildcareRepository extends JpaRepository<Childcare, Integer> {

    /**
     * Query to retrieve the list of childcares accepted by user in charge but not yet commented by the user in need.
     * @param userInNeed for which we need to determine the list of childcares.
     * @return list of childcares.
     */
    @Query("select u from Childcare u where inNeedComment = 0 and isValidated = 1 and ?1 = u.userInNeed")
    List<Childcare> findChildcaresListOfUserInNeedNotCommentedYet(User userInNeed);

    /**
     * Query to retrieve the list of childcares accepted by user in charge that he has not yet commented.
     * @param userInCharge for which we need to determine the list of childcares.
     * @return list of childcares.
     */
    @Query("select u from Childcare u where inChargeComment = 0 and isValidated = 1 and ?1 = u.userWatching")
    List<Childcare> findChildcaresListOfUserInChargeNotCommentedYet(User userInCharge);

    /**
     * Query to retrieve the total amount of childcares accomplished by a particular user who whatched over children.
     * @param user for which we need to determine the amount of childcares made.
     * @return Integer for the resulting count.
     */
    @Query(value = "select count(*) from Childcare u where u.isAccomplished = 1 and u.userWatching = ?1")
    Integer numberOfChildcaresAccomplishedByUserWatchingId(User user);

    /**
     * Query to retrieve the total amount of childcares asked by a particular user who made the request and accomplished.
     * @param user for which we need to determine the amount of childcares asked and accomplished.
     * @return Integer for the resulting count.
     */
    @Query(value = "select count(*) from Childcare u where u.isAccomplished = 1 and u.userInNeed = ?1")
    Integer numberOfChildcaresAskedByUserInNeedIdAndAccomplished(User user);

    /**
     * Query to retrieve the total amount of childcares accomplished but not commented yet for the user who whatched over the children.
     * @param user for which we need to determine the amount of childcares made.
     * @return Integer for the resulting count.
     */
    @Query(value = "select count(*) from Childcare u where u.isAccomplished = 1 and inChargeComment = 0 and u.userWatching = ?1")
    Integer numberOfChildcaresAccomplishedByUserWatchingIdNotCommentedYet(User user);

    /**
     * Query to retrieve the total amount of childcares accomplished but not commented yet for the user who required the childcare.
     * @param user for which we need to determine the amount of childcares made.
     * @return Integer for the resulting count.
     */
    @Query(value = "select count(*) from Childcare u where u.isAccomplished = 1 and inNeedComment = 0 and u.userInNeed = ?1")
    Integer numberOfChildcaresAccomplishedByUserInNeedIdNotCommentedYet(User user);

    /**
     * Query to retrieve the total amount of childcares awaiting for validation by the user in charge.
     * @param user for which we need to determine the amount of childcares awaiting.
     * @return Integer for the resulting count.
     */
    @Query(value = "select count(*) from Childcare u where u.isValidated = null and u.userWatching = ?1")
    Integer numberOfChildcaresToValidateByUserInCharge(User user);
}
