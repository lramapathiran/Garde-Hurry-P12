package com.lavanya.api;

import com.lavanya.api.dto.CommentDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.service.ChildcareService;
import com.lavanya.api.service.CommentService;
import com.lavanya.api.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class CommentServiceTestIT {

    @Autowired
    CommentService commentService;

    @Autowired
    ChildcareService childcareService;

    @Autowired
    UserService userService;

    @Test
    public void saveCommentTestByChildParent(){

        CommentDto commentDto = new CommentDto();
        commentDto.setContent("blablablabla");

        CommentDto commentSaved = commentService.saveComment(commentDto, 144, "childParent");

        UserDto userDto = childcareService.getChildcareById(144).getUserDtoInNeed();

        Assert.assertTrue(userDto.getUuid() == commentSaved.getUserComment().getUuid());
        Assert.assertTrue(commentSaved.getContent() == "blablablabla");


    }

    @Test
    public void saveCommentTestByUserWatching(){

        CommentDto commentDto = new CommentDto();
        commentDto.setContent("blablablabla");

        CommentDto commentSaved = commentService.saveComment(commentDto, 144, "babySitter");

        UserDto userDto = childcareService.getChildcareById(144).getUserDtoWatching();

        Assert.assertTrue(userDto.getUuid() == commentSaved.getUserComment().getUuid());
        Assert.assertTrue(commentSaved.getContent() == "blablablabla");


    }

    @Test
    public void geCommentsOnUserByUserIdTest(){

        UserDto userDto = userService.getUser("l.fernand@gmail.com");

        CommentDto commentDto = new CommentDto();
        commentDto.setContent("blablablabla");

        CommentDto commentSaved = commentService.saveComment(commentDto, 144, "childParent");

        List<CommentDto> list = commentService.geCommentsOnUserByUserId(userDto);

        Assert.assertFalse(list.isEmpty());
    }


}
