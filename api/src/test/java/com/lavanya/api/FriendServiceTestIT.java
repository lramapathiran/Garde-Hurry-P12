package com.lavanya.api;

import com.lavanya.api.service.FriendService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class FriendServiceTestIT {

    @Autowired
    FriendService friendService;

    @Autowired
    UserService userService;

    @Test
    public void isUsersFriendsSucceed() {

        int userConnected = 95;
        int userProfileVisitedId = 3;

        Boolean response = friendService.isUsersFriends(userConnected,userProfileVisitedId);

        Assert.assertTrue(response);

    }

    @Test
    public void isUsersFriendsFailed() {

        int userConnected = 3;
        int userProfileVisitedId = 100;

        Boolean response = friendService.isUsersFriends(userConnected,userProfileVisitedId);

        Assert.assertFalse(response);
    }
}
