package com.lavanya.api.controller;

import com.lavanya.api.dto.CommentDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.service.CommentService;
import com.lavanya.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Rest Controller to control all the requests related to Comment object.
 * @author lavanya
 */
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @PostMapping("saveComment/{id}")
    CommentDto saveComment(@RequestBody CommentDto commentDto, @PathVariable("id") int childcareId, @RequestParam ("feedbackAuthor") String feedbackAuthor) {
        return commentService.saveComment(commentDto,childcareId,feedbackAuthor);
    }

    @GetMapping("user/comments/{id}")
    List<CommentDto> getListOfCommentsByUserId(@PathVariable("id") UUID userId){

        UserDto userDto = userService.getUserById(userId);
        return commentService.geCommentsOnUserByUserId(userDto);
    }


}
