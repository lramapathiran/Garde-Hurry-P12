package com.lavanya.api;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.enums.School;
import com.lavanya.api.model.Children;
import com.lavanya.api.service.ChildrenService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ChildrenServiceTestIT {

    @Autowired
    ChildrenService childrenService;

    @Autowired
    UserService userService;

    @Test
    public void saveChildrenTest(){
        String username = "l.fernand@gmail.com";

        ChildrenDto childrenDto = new ChildrenDto();
        childrenDto.setAge(6);
        childrenDto.setName("Raphaël");
        childrenDto.setSchool(School.P);

        Children childrenSaved = childrenService.saveChildren(childrenDto,username);

        String childrenDtoName = childrenSaved.getName();

        Assert.assertTrue(childrenDtoName == "Raphaël");
    }

    @Test
    public void getChildrenById() {
        String username = "l.fernand@gmail.com";

        ChildrenDto childrenDto = new ChildrenDto();
        childrenDto.setAge(6);
        childrenDto.setName("Raphaël");
        childrenDto.setSchool(School.P);

        Children childrenSaved = childrenService.saveChildren(childrenDto,username);

        ChildrenDto childrenDto1 = childrenService.getChildrenById(childrenSaved.getId());

        Assert.assertTrue(childrenSaved.getId() == childrenDto1.getId());
    }
}
