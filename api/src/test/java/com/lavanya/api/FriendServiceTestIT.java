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
        int userWhoInviteId = 3;
        int userInvitedId = 95;

        FriendDto friendDto = friendService.findFriendRelationshipByBothUsersId(userInvitedId,userWhoInviteId);

        assertEquals(111,friendDto.getId());
    }
}
