package com.lavanya.api;

import com.lavanya.api.dto.UserDto;
import com.lavanya.api.error.UserAlreadyExistException;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.User;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.lavanya.api.service.UserService;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class UserServiceTestIT {

   @Autowired
   private UserService userService;

   @Autowired
   private UserMapper userMapper;

    @Test
    public void saveUserSucceedTest() {
        UserDto userDto = new UserDto();
        userDto.setPassword("blabla");
        userDto.setFirstName("Linda");
        userDto.setLastName("Morêt");
        userDto.setEmail("lmoret@gmail.com");
        userDto.setAddress("1 rue des roses");
        userDto.setArea("les charmettes");
        userDto.setCity("Nantes");
        userDto.setSituation(false);

        UserDto savedUser = userService.saveUser(userDto);
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(userDto);
    }

    @Test
    public void findUserByUsernameTest() {

        User userInDatabase = userService.findUserByUsername("l.fernand@gmail.com");

        Assert.assertEquals("l.fernand@gmail.com",userInDatabase.getEmail());
    }

    @Test
    public void saveUserFailedWithAlreadyExistingEmailTest() {
        UserDto userDto = new UserDto();
        userDto.setPassword("blabla");
        userDto.setFirstName("Linda");
        userDto.setLastName("Morêt");
        userDto.setEmail("l.fernand@gmail.com");
        userDto.setAddress("1 rue des roses");
        userDto.setArea("les charmettes");
        userDto.setCity("Nantes");
        userDto.setSituation(false);

        Assertions.assertThrows(UserAlreadyExistException.class, () -> {
            userService.saveUser(userDto);
        });

    }

    @Test
    public void updateUserTest() {
        User user = userService.findUserByUsername("l.fernand@gmail.com");
        UserDto userDto = userMapper.userToUserDto(user);

        String userAddress = userDto.getAddress();

        userDto.setAddress("2 rue des Roses");

        userService.updateUser(userDto);

        User userUpdated = userService.findUserByUsername("l.fernand@gmail.com");
        String updatedUserAddress = userUpdated.getAddress();

        Assert.assertEquals("1 rue des Roses",userAddress);
        Assert.assertEquals("2 rue des Roses",updatedUserAddress);

    }

    @Test
    public void validateUserProfileByAdminTest() {

        User user = userService.findUserByUsername("l.fernand@gmail.com");
        user.setValidated(false);
        Boolean userProfileValidation = user.getValidated();

        UserDto userDto = userMapper.userToUserDto(user);

        userService.validateUserProfileByAdmin(userDto);

        User userValidated = userService.findUserByUsername("l.fernand@gmail.com");
        Boolean userProfileActivation = userValidated.getValidated();

        Assert.assertNotEquals(userProfileValidation,userProfileActivation);

    }

//    @Test
//    public void getUserByIdTest() {
//
//        UserDto userDto = userService.getUserById(3);
//
//        String username = userDto.getEmail();
//
//        Assert.assertEquals("l.fernand@gmail.com", username);
//
//    }

    @Test
    public void getListUsersTest() {
        Page<User> userList = userService.getAllUsers(1);
        assertFalse(userList.isEmpty());
    }

    @Test
    public void emailExitsSucceedTest() {
        Boolean response = userService.emailExists("l.fernand@gmail.com");
        Assert.assertEquals(true,response);
    }

    @Test
    public void emailExitsFailTest() {
        Boolean response = userService.emailExists("l.ferrrrrnand@gmail.com");
        Assert.assertEquals(false,response);
    }

//    @Test
//    public void deleteUserTest() {
//        UserDto userDto = userService.getUserById(3);
//        String username = userDto.getEmail();
//        Boolean response = userService.emailExists(username);
//        Assert.assertEquals(true,response);
//
//        userService.deleteUserByAdmin(userDto);
//
//        Boolean newResponse = userService.emailExists(username);
//        Assert.assertEquals(false,newResponse);
//    }
}
