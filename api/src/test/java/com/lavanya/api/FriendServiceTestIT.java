package com.lavanya.api;

import com.lavanya.api.dto.FriendDto;
import com.lavanya.api.service.FriendService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class FriendServiceTestIT {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @Test
    public void isUsersFriendsTestSucceed() {

        int userConnected = 95;
        int userProfileVisitedId = 3;

        Boolean response = friendService.isUsersFriends(userConnected,userProfileVisitedId);

        Assert.assertTrue(response);

    }

    @Test
    public void isUsersFriendsTestFailed() {

        int userConnected = 3;
        int userProfileVisitedId = 100;

        Boolean response = friendService.isUsersFriends(userConnected,userProfileVisitedId);

        Assert.assertFalse(response);
    }

    @Test
    public void findFriendRelationshipByBothUsersIdTest() {
        int userWhoInviteId1 = 3;
        int userInvitedId1 = 95;

        int userWhoInviteId2 = 95;
        int userInvitedId2 = 3;



        FriendDto friendDto1 = friendService.findFriendRelationshipByBothUsersId(userInvitedId1,userWhoInviteId1);
        FriendDto friendDto2 = friendService.findFriendRelationshipByBothUsersId(userInvitedId2,userWhoInviteId2);

        assertEquals(111,friendDto1.getId());
        assertEquals(111,friendDto2.getId());

    }
}
