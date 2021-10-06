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

    /**
     * POST requests for saveComment/{id} endpoint.
     * method to save a comment.
     * @param commentDto that needs to mapped to comment entity to be saved in DB.
     * @param childcareId id of the childcare for which the comment is made.
     * @param feedbackAuthor determines if the author of the comment is the user in charge or the user in need.
     * @return CommentDto
     */
    @PostMapping("saveComment/{id}")
    public CommentDto saveComment(@RequestBody CommentDto commentDto, @PathVariable("id") int childcareId, @RequestParam ("feedbackAuthor") String feedbackAuthor) {
        return commentService.saveComment(commentDto,childcareId,feedbackAuthor);
    }

    /**
     * GET requests for user/comments/{id} endpoint.
     * method to retrieve list of all comments made on a particular user.
     * @param userId uuid of the user of interest for whom we need the list of comments.
     * @return list of CommentDtos.
     */
    @GetMapping("user/comments/{id}")
    public List<CommentDto> getListOfCommentsByUserId(@PathVariable("id") UUID userId){

        UserDto userDto = userService.getUserById(userId);
        return commentService.geCommentsOnUserByUserId(userDto);
    }


}
