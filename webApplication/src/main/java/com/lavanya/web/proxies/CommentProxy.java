package com.lavanya.web.proxies;

import com.lavanya.web.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.UUID;

/**
 * interface required to communicate with api module and make all the requests related to Comment object.
 * @author lavanya
 */
@FeignClient(name = "commentdApi", url = "localhost:9090")
public interface CommentProxy {

    @PostMapping(value = "saveComment/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    CommentDto saveComment(@RequestBody CommentDto commentDto, @PathVariable ("id") int childcareId, @RequestParam("feedbackAuthor") String feedbackAuthor, @RequestHeader(name = "Authorization") String token);

    @GetMapping("user/comments/{id}")
    List<CommentDto> getListOfCommentsByUserId(@PathVariable("id") UUID userId, @RequestHeader(name = "Authorization") String token);
}
