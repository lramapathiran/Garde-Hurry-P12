package com.lavanya.api;

import com.lavanya.api.dto.FriendDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.model.Friend;
import com.lavanya.api.service.FriendService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FriendServiceTestIT {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @Test
    public void saveTest(){
        FriendDto friendDto = new FriendDto();

        UserDto user1 = userService.getUser("l.fernand@gmail.com");
        UserDto user2 = userService.getUser("smariesainte@gmail.com");

        friendDto.setUserInvited(user1);
        friendDto.setUserWhoInvite(user2);

        Friend savedFriend = friendService.save(friendDto);


        assertThat(savedFriend.getUserInvited()).usingRecursiveComparison().ignoringFields("id","uuid","password","childrens", "sentFriendInvitations",
                "receivedFriendInvitations", "childcareRequests", "childcareMissions", "commentsMade", "commentsReceived").isEqualTo(friendDto.getUserInvited());
        assertThat(savedFriend.getUserWhoInvite()).usingRecursiveComparison().ignoringFields("id","uuid","password","childrens", "sentFriendInvitations",
                "receivedFriendInvitations", "childcareRequests", "childcareMissions", "commentsMade", "commentsReceived").isEqualTo(friendDto.getUserWhoInvite());

    }

    @Test
    public void isUsersFriendsTestSucceed() {

        String userConnectedUsername = "s.Monthy@gmail.com";
        UUID userProfileVisitedId = userService.getUser(userConnectedUsername).getUuid();

        Boolean response = friendService.isUsersFriends("l.fernand@gmail.com",userProfileVisitedId);

        Assert.assertTrue(response);

    }

    @Test
    public void isUsersFriendsTestFailed() {

        String userConnectedUsername = "smarcy@gmail.com";
        UUID userProfileVisitedId = userService.getUser(userConnectedUsername).getUuid();

        Boolean response = friendService.isUsersFriends("l.fernand@gmail.com",userProfileVisitedId);

        Assert.assertFalse(response);
    }

    @Test
    public void getListOfFriendRequestsTest() {

        FriendDto friendDto = new FriendDto();

        UserDto user1 = userService.getUser("l.fernand@gmail.com");
        UserDto user2 = userService.getUser("smariesainte@gmail.com");

        friendDto.setUserInvited(user1);
        friendDto.setUserWhoInvite(user2);

        friendService.save(friendDto);
        String userConnectedUsername = "l.fernand@gmail.com";
        List<FriendDto> list = friendService.getListOfFriendRequests(userConnectedUsername);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(false,list.get(0).getAccepted());
    }

    @Test
    public void updateFriendTest() {
        FriendDto friendDto = new FriendDto();

        UserDto user1 = userService.getUser("l.fernand@gmail.com");
        UserDto user2 = userService.getUser("smariesainte@gmail.com");

        friendDto.setUserInvited(user1);
        friendDto.setUserWhoInvite(user2);

        Friend savedFriend = friendService.save(friendDto);

        friendService.updateFriend(savedFriend.getId());

        Assert.assertNotEquals(friendDto.getAccepted(),savedFriend.getAccepted());

    }

    @Test
    public void deleteFriendTest() {
        int friendToDeleteId = 111;

        UserDto user1 =  userService.getUser("l.fernand@gmail.com");
        UUID userProfileVisitedId = userService.getUser(user1.getEmail()).getUuid();

        Boolean friendsRelation1 = friendService.isUsersFriends("s.Monthy@gmail.com",userProfileVisitedId);

        friendService.deleteFriend(friendToDeleteId);

        Boolean friendsRelation2 = friendService.isUsersFriends("s.Monthy@gmail.com",userProfileVisitedId);

        Assert.assertNotEquals(friendsRelation1,friendsRelation2);
    }

    @Test
    public void findFriendRelationshipByBothUsersIdTest() {
        String userConnectedUsername = "l.fernand@gmail.com";

        String userToCheckUsername = "s.Monthy@gmail.com";
        UUID userInvitedId = userService.getUser(userToCheckUsername).getUuid();

        FriendDto friendDto = friendService.findFriendRelationshipByBothUsersId(userInvitedId,userConnectedUsername);

        assertEquals(111,friendDto.getId());

    }

    @Test
    public void getListOfAllFriendsByUserTest() {
        List<FriendDto> list = friendService.getListOfAllFriendsByUser("l.fernand@gmail.com");
        assertFalse(list.isEmpty());
    }

    @Test
    public void getcountOfFriendsByUserTest(){
        Integer count = friendService.getcountOfFriendsByUser("l.fernand@gmail.com");
        assertTrue(count>0);
    }
}
