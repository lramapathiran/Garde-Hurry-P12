package com.lavanya.api;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.model.Childcare;
import com.lavanya.api.service.ChildcareService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ChildcareServiceTestIT {

    @Autowired
    ChildcareService childcareService;

    @Autowired
    UserService userService;

    @Test
    public void getChildcareByIdTest(){

        ChildcareDto childcareDto = childcareService.getChildcareById(141);

        Assert.assertTrue(childcareDto.getId()==141);
        Assert.assertNotNull(childcareDto);

    }

//    @Test
//    public void saveChildcareTest() {
//
//        UserDto userDtoWatching = userService.getUser("l.fernand@gmail.com");
//        ChildcareDto childcareDto = new ChildcareDto();
//        childcareDto.setUserDtoWatching(userDtoWatching);
//        childcareDto.setNumberOfChildren(1);
//        childcareDto.setTimeEnd();
//
//
//
//    }


}
