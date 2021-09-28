package com.lavanya.api;

import com.lavanya.api.dto.AuthBodyDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.dto.UserToRegister;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class UserServiceTestIT {

   @Autowired
   private UserService userService;

   @Autowired
   private UserMapper userMapper;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Test
    public void saveUserSucceedTest() {
        UserToRegister user = new UserToRegister();
        user.setPassword("blabla");
        user.setFirstName("Linda");
        user.setLastName("Morêt");
        user.setEmail("lmoret@gmail.com");
        user.setAddress("1 rue des roses");
        user.setArea("les charmettes");
        user.setCity("Nantes");
        user.setSituation(false);

        UserDto savedUser = userService.saveUser(user);
        Assertions.assertTrue(savedUser.getEmail() == user.getEmail());
    }

    @Test
    public void findUserByUsernameTest() {

        User userInDatabase = userService.findUserByUsername("l.fernand@gmail.com");

        Assert.assertEquals("l.fernand@gmail.com",userInDatabase.getEmail());
    }

    @Test
    public void saveUserFailedWithAlreadyExistingEmailTest() {
        UserToRegister user = new UserToRegister();
        user.setPassword("blabla");
        user.setFirstName("Linda");
        user.setLastName("Morêt");
        user.setEmail("l.fernand@gmail.com");
        user.setAddress("1 rue des roses");
        user.setArea("les charmettes");
        user.setCity("Nantes");
        user.setSituation(false);

        Assertions.assertThrows(UserAlreadyExistException.class, () -> {
            userService.saveUser(user);
        });

    }

    @Test
    public void generateTokenTest(){
        AuthBodyDto data = new AuthBodyDto();
        data.setUsername("l.fernand@gmail.com");
        data.setPassword("l.fernand@gmail.com");

        String token = userService.generateToken(data);

        Assert.assertTrue(token.length()>0);

    }

    @Test
    public void updateUserTest() {
        User user = userService.findUserByUsername("l.fernand@gmail.com");
        UserDto userDto = userMapper.userToUserDto(user);

        String userAddress = userDto.getAddress();

        userDto.setAddress("2 rue des roses");

        userService.updateUser(userDto);

        User userUpdated = userService.findUserByUsername("l.fernand@gmail.com");
        String updatedUserAddress = userUpdated.getAddress();

        Assert.assertEquals("1 rue des roses",userAddress);
        Assert.assertEquals("2 rue des roses",updatedUserAddress);

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

    @Test
    public void getUserTest() {
        UserDto userDto = userService.getUser("l.fernand@gmail.com");
        String email = userDto.getEmail();

        Assert.assertEquals("l.fernand@gmail.com",email);

    }

    @Test
    public void getUserByIdTest() {

        UserDto userDtoByUsername = userService.getUser("l.fernand@gmail.com");
        UserDto userDtoById = userService.getUserById(userDtoByUsername.getUuid());

        String username = userDtoById.getEmail();

        Assert.assertEquals("l.fernand@gmail.com", username);

    }

    @Test
    public void getAllUsersTest() {
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

    @Test
    public void deleteUserByAdminTest() {
        UserDto userDto = userService.getUser("l.fernand@gmail.com");
        String username = userDto.getEmail();
        Boolean response = userService.emailExists(username);
        Assert.assertEquals(true,response);

        userService.deleteUserByAdmin(userDto);

        Boolean newResponse = userService.emailExists(username);
        Assert.assertEquals(false,newResponse);
    }

    @Test
    public void deleteUserTest() {
        UserDto userDto = userService.getUser("l.fernand@gmail.com");
        String username = userDto.getEmail();
        Boolean response = userService.emailExists(username);
        Assert.assertEquals(true,response);

        UUID userDtoToDeleteId = userDto.getUuid();
        userService.deleteUser(userDtoToDeleteId);

        Boolean newResponse = userService.emailExists(username);
        Assert.assertEquals(false,newResponse);
    }

    @Test
    public void updateUserProfileValidationStatusTest() {

        User user = userService.findUserByUsername("l.fernand@gmail.com");
        user.setValidated(false);
        Boolean userProfileValidation = user.getValidated();

        UserDto userDto = userMapper.userToUserDto(user);

        userDto.setValidated(true);
        userService.updateUserProfileValidationStatus(userDto);

        User userValidated = userService.findUserByUsername("l.fernand@gmail.com");
        Boolean userProfileActivation = userValidated.getValidated();

        Assert.assertNotEquals(userProfileValidation,userProfileActivation);

    }

    @Test
    public void getAllUsersFilteredTest() {
        Page<UserDto> userList = userService.getAllUsersFiltered(1,"Fernand");
        assertFalse(userList.isEmpty());
        Assert.assertTrue(userList.getTotalElements()==1);
    }
}
