package com.lavanya.web.proxies;

import com.lavanya.web.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.stream.events.Comment;

@FeignClient(name = "commentdApi", url = "localhost:9090")
public interface CommentProxy {

    @PostMapping(value = "saveComment/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    CommentDto saveComment(@RequestBody CommentDto commentDto, @PathVariable ("id") int childcareId, @RequestParam("feedbackAuthor") String feedbackAuthor);
}
