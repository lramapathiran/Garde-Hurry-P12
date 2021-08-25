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

        String userConnectedUsername = "s.Monthy@gmail.com";
        int userProfileVisitedId = 3;

        Boolean response = friendService.isUsersFriends(userConnectedUsername,userProfileVisitedId);

        Assert.assertTrue(response);

    }

    @Test
    public void isUsersFriendsTestFailed() {

        String userConnectedUsername = "l.fernand@gmail.com";
        int userProfileVisitedId = 100;

        Boolean response = friendService.isUsersFriends(userConnectedUsername,userProfileVisitedId);

        Assert.assertFalse(response);
    }

    @Test
    public void findFriendRelationshipByBothUsersIdTest() {
        String userConnectedUsername1 = "l.fernand@gmail.com";
        int userInvitedId1 = 95;

        String userConnectedUsername2 = "s.Monthy@gmail.com";
        int userInvitedId2 = 3;



        FriendDto friendDto1 = friendService.findFriendRelationshipByBothUsersId(userInvitedId1,userConnectedUsername1);
        FriendDto friendDto2 = friendService.findFriendRelationshipByBothUsersId(userInvitedId2,userConnectedUsername2);

        assertEquals(111,friendDto1.getId());
        assertEquals(111,friendDto2.getId());

    }
}
