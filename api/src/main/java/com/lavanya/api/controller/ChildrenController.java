package com.lavanya.api.controller;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.service.ChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller to control all the requests related to User object.
 * @author lavanya
 */
@RestController
public class ChildrenController {

    @Autowired
    ChildrenService childrenService;

    @PostMapping("/saveChildren")
    public void saveChildren(@RequestBody ChildrenDto childrenDto){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        childrenService.saveChildren(childrenDto,username);

    }

    @GetMapping("/children/{childrenId}")
    ChildrenDto getChildrenById(@PathVariable int childrenId) {
        return childrenService.getChildrenById(childrenId);
    }

    @PostMapping("/delete/children")
    void deleteChildren(@RequestBody ChildrenDto childrenDto){
        childrenService.deleteChildren(childrenDto);
    }

}
