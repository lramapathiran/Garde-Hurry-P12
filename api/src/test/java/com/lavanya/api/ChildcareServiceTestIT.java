package com.lavanya.api;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.service.ChildcareService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void saveChildcareTest() {

        UserDto userDtoWatching = userService.getUser("l.fernand@gmail.com");
        UserDto userInNeed = userService.getUser("s.Monthy@gmail.com");
        ChildcareDto childcareDto = new ChildcareDto();
        childcareDto.setUserDtoWatching(userDtoWatching);
        childcareDto.setUserDtoInNeed(userInNeed);
        childcareDto.setNumberOfChildren(1);
        childcareDto.setTimeStart(LocalTime.of(10, 00, 00));
        childcareDto.setTimeEnd(LocalTime.of(12, 00, 00));
        childcareDto.setDate(LocalDate.of(2021, 10, 8));
        childcareDto.setDescription("blabla");

        ChildcareDto childcareDtoSaved = childcareService.saveChildcare(childcareDto,"s.Monthy@gmail.com");

        assertThat(childcareDtoSaved).hasFieldOrPropertyWithValue("description", "blabla");
        assertThat(childcareDtoSaved).hasFieldOrPropertyWithValue("date",LocalDate.of(2021, 10, 8));

    }

    @Test
    public void completeRequestTest(){

        UserDto userDtoWatching = userService.getUser("l.fernand@gmail.com");
        UserDto userInNeed = userService.getUser("s.Monthy@gmail.com");
        ChildcareDto childcareDto = new ChildcareDto();
        childcareDto.setUserDtoWatching(userDtoWatching);
        childcareDto.setUserDtoInNeed(userInNeed);
        childcareDto.setNumberOfChildren(1);
        childcareDto.setTimeStart(LocalTime.of(10, 00, 00));
        childcareDto.setTimeEnd(LocalTime.of(12, 00, 00));
        childcareDto.setDate(LocalDate.of(2021, 10, 8));
        childcareDto.setDescription("blabla");

        ChildcareDto childcareDtoSaved = childcareService.saveChildcare(childcareDto,"s.Monthy@gmail.com");

        Assert.assertTrue(childcareDtoSaved.getComplete() == false);

        childcareService.completeRequest(childcareDtoSaved.getId());

        ChildcareDto childcareDtoSavedUpdated = childcareService.getChildcareById(childcareDtoSaved.getId());

        Assert.assertTrue(childcareDtoSavedUpdated.getComplete() == true);
    }

    @Test
    public void updateChildcareValidationStatusTest() {
        UserDto userDtoWatching = userService.getUser("l.fernand@gmail.com");
        UserDto userInNeed = userService.getUser("s.Monthy@gmail.com");
        ChildcareDto childcareDto = new ChildcareDto();
        childcareDto.setUserDtoWatching(userDtoWatching);
        childcareDto.setUserDtoInNeed(userInNeed);
        childcareDto.setNumberOfChildren(1);
        childcareDto.setTimeStart(LocalTime.of(10, 00, 00));
        childcareDto.setTimeEnd(LocalTime.of(12, 00, 00));
        childcareDto.setDate(LocalDate.of(2021, 10, 8));
        childcareDto.setDescription("blabla");

        ChildcareDto childcareDtoSaved = childcareService.saveChildcare(childcareDto,"s.Monthy@gmail.com");

        Assert.assertTrue(childcareDtoSaved.getValidated()  == null);
        childcareDtoSaved.setValidated(true);

        childcareService.updateChildcareValidationStatus(childcareDtoSaved);

        ChildcareDto childcareDtoUpdated = childcareService.getChildcareById(childcareDtoSaved.getId());

        Assert.assertTrue(childcareDtoUpdated.getValidated()  == true);
    }

    @Test
    public void accomplishChildcare(){
        UserDto userDtoWatching = userService.getUser("l.fernand@gmail.com");
        UserDto userInNeed = userService.getUser("s.Monthy@gmail.com");
        ChildcareDto childcareDto = new ChildcareDto();
        childcareDto.setUserDtoWatching(userDtoWatching);
        childcareDto.setUserDtoInNeed(userInNeed);
        childcareDto.setNumberOfChildren(1);
        childcareDto.setTimeStart(LocalTime.of(10, 00, 00));
        childcareDto.setTimeEnd(LocalTime.of(12, 00, 00));
        childcareDto.setDate(LocalDate.of(2021, 10, 8));
        childcareDto.setDescription("blabla");

        ChildcareDto childcareDtoSaved = childcareService.saveChildcare(childcareDto,"s.Monthy@gmail.com");

        Assert.assertTrue(childcareDtoSaved.getAccomplished()  == false);

        childcareService.accomplishChildcare(childcareDtoSaved.getId());

        ChildcareDto childcareDtoUpdated = childcareService.getChildcareById(childcareDtoSaved.getId());

        Assert.assertTrue(childcareDtoUpdated.getAccomplished()  == true);
    }

    @Test
    public void getChildcaresListOfUserInNeedUnCommentedTest(){
        List<ChildcareDto> list = childcareService.getChildcaresListOfUserInNeedUnCommented("s.Monthy@gmail.com");
        assertFalse(list.isEmpty());
    }

    @Test
    public void getChildcaresListOfUserInChargeUnCommentedTest(){
        List<ChildcareDto> list = childcareService.getChildcaresListOfUserInChargeUnCommented("l.fernand@gmail.com");
        assertFalse(list.isEmpty());
    }

    @Test
    public void getCountOfChildcaresAccomplishedOfUserWatchingTest() {
        Integer count = childcareService.getCountOfChildcaresAccomplishedOfUserWatching("l.fernand@gmail.com");
        assertTrue(count != null);
    }

    @Test
    public void getCountOfChildcaresAccomplishedOfUserInNeedTest() {
        Integer count = childcareService.getCountOfChildcaresAccomplishedOfUserInNeed("l.fernand@gmail.com");
        assertTrue(count != null);
    }

    @Test
    public void getCountOfChildcaresAccomplishedAndNotCommentedOfUserWatchingTest(){
        Integer count = childcareService.getCountOfChildcaresAccomplishedAndNotCommentedOfUserWatching("l.fernand@gmail.com");
        assertTrue(count != null);
    }

    @Test
    public void getCountOfChildcaresAccomplishedAndNotCommentedOfUserInNeedTest(){
        Integer count = childcareService.getCountOfChildcaresAccomplishedAndNotCommentedOfUserInNeed("l.fernand@gmail.com");
        assertTrue(count != null);
    }

    @Test
    public void getCountOfChildcaresToValidateOfUserInChargeTest(){
        Integer count = childcareService.getCountOfChildcaresToValidateOfUserInCharge("l.fernand@gmail.com");
        assertTrue(count != null);
    }
}
