package com.lavanya.api.repository;

import com.lavanya.api.model.Friend;
import com.lavanya.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository extending JPA repository for persistence of Friend object.
 * @author lavanya
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    /**
     * Query to verify if two users have a friend relation.
     * @param userConnected for the user connected and recognised in DB as the user who initiated the friend request.
     * @param userProfileVisited for the user with we need to check a friend relation and recognised in DB as the user who accepted the friend request.
     * @return boolean true or false.
     */
    @Query("select count(u)>0 from Friend u where ?1 = u.userWhoInvite and ?2 = u.userInvited")
    Boolean existsByUserWhoInviteAndByUserInvited (User userConnected, User userProfileVisited);

    /**
     * Query to verify if two users have a friend relation.
     * @param userConnected for the user connected and recognised in DB as the user who accepted the friend request.
     * @param userProfileVisited for the user with we need to check a friend relation and recognised in DB as the user who initiated the friend request.
     * @return boolean true or false.
     */
    @Query("select count(u)>0 from Friend u where ?1 = u.userInvited and ?2 = u.userWhoInvite")
    Boolean existsByUserInvitedAndUserWhoInvite (User userConnected, User userProfileVisited);

    /**
     * Query to retrieve list of friends requests a user received.
     * @param userConnectedId id of the user of interest for which the list of friends is retrieved.
     * @return list of friend.
     */
    List<Friend> findByUserInvitedIdOrderByDateDesc(int userConnectedId);

    /**
     * Query to retrieve a specific Friend entity beween two users.
     * @param userInvitedId id of one of the user of interest and recognised in DB as the user who accepted the friend request.
     * @param userWhoInviteId id of the second user of interest and recognised in DB as the user who initiated the friend request.
     * @return a friend.
     */
    Friend findByUserInvitedIdAndUserWhoInviteId(int userInvitedId, int userWhoInviteId);

    /**
     * Query to retrieve list of friends where a specific user is associated.
     * @param userConnected as the user of interest for which the list of friends is retrieved.
     * @return list of friend.
     */
    @Query("select u from Friend u where isAccepted = 1 and ?1 = u.userInvited  or ?1 = u.userWhoInvite")
    List<Friend> findByUserInvitedIdOrUserWhoInviteId(User userConnected);

    /**
     * Query to retrieve the total amount of friends of a particular user.
     * @param user for which we need to determine the amount of friends.
     * @return Integer for the resulting count.
     */
    @Query(value = "select count(*) from Friend u where u.isAccepted = 1 and u.userWhoInvite = ?1 or u.userInvited = ?1")
    Integer countOfFriends(User user);
}
