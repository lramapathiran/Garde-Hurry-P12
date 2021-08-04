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

    @Query("select count(u)>0 from Friend u where ?1 = u.userWhoInvite and ?2 = u.userInvited")
    Boolean existsByUserWhoInviteAndByUserInvited (User userConnected, User userProfileVisited);

    @Query("select count(u)>0 from Friend u where ?1 = u.userInvited and ?2 = u.userWhoInvite")
    Boolean existsByUserInvitedAndUserWhoInvite (User userConnected, User userProfileVisited);

    List<Friend> findByUserInvitedIdOrderByDateDesc(int userConnectedId);

    Friend findByUserInvitedIdAndUserWhoInviteId(int userInvitedId, int userWhoInviteId);
}
