package com.lavanya.api;

import com.lavanya.api.error.UserAlreadyExistException;
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

    @Test
    public void saveUserSucceedTest() {
        User user = new User();
        user.setPassword("blabla");
        user.setFirstName("Linda");
        user.setLastName("Morêt");
        user.setEmail("lmoret@gmail.com");
        user.setAddress("1 rue des roses");
        user.setArea("les charmettes");
        user.setCity("Nantes");
        user.setSituation(false);
        user.setChildren(Collections.emptyList());

        User savedUser = userService.saveUser(user);
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    public void findUserByUsernameTest() {

        User userInDatabase = userService.findUserByUsername("l.fernand@gmail.com");

        Assert.assertEquals("l.fernand@gmail.com",userInDatabase.getEmail());
    }

    @Test
    public void saveUserFailedWithAlreadyExistingEmailTest() {
        User user = new User();
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
    public void updateUserTest() {
        User user = userService.findUserByUsername("l.fernand@gmail.com");
        String userAddress = user.getAddress();

        user.setAddress("2 rue des Roses");

        userService.updateUser(user);

        User userUpdated = userService.findUserByUsername("l.fernand@gmail.com");
        String updatedUserAddress = userUpdated.getAddress();

        Assert.assertEquals("1 rue des Roses",userAddress);
        Assert.assertEquals("2 rue des Roses",updatedUserAddress);

    }

    @Test
    public void validateUserProfileByAdminTest() {

        User user = userService.findUserByUsername("l.fernand@gmail.com");
        Boolean userProfileValidation = user.getValidated();

        userService.validateUserProfileByAdmin(user);

        User userUpdated = userService.findUserByUsername("l.fernand@gmail.com");
        Boolean userProfileActivation = user.getValidated();

        Assert.assertNotEquals(userProfileValidation,userProfileActivation);

    }

    @Test
    public void getUserByIdTest() {

        Optional<User> optional = userService.getUserById(3);

        String username = optional.get().getEmail();

        Assert.assertEquals("l.fernand@gmail.com", username);

    }

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

    @Test
    public void deleteUserTest() {
        Optional<User> user = userService.getUserById(3);
        String username = user.get().getEmail();
        Boolean response = userService.emailExists(username);
        Assert.assertEquals(true,response);

        userService.deleteUserByAdmin(user.get());

        Optional<User> userDeleted = userService.getUserById(3);
        Assertions.assertFalse(userDeleted.isPresent());
    }
}
