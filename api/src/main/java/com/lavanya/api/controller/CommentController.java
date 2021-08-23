package com.lavanya.api.controller;

import com.lavanya.api.dto.CommentDto;
import com.lavanya.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller to control all the requests related to Comment object.
 * @author lavanya
 */
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("saveComment/{id}")
    CommentDto saveComment(@RequestBody CommentDto commentDto, @PathVariable("id") int childcareId, @RequestParam ("feedbackAuthor") String feedbackAuthor) {
        return commentService.saveComment(commentDto,childcareId,feedbackAuthor);
    }


}
