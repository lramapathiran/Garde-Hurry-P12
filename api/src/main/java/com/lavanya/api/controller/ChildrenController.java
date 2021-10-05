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

    /**
     * POST requests for /saveChildren endpoint.
     * method to save a user child.
     * @param childrenDto that needs to be saved in database.
     */
    @PostMapping("/saveChildren")
    public void saveChildren(@RequestBody ChildrenDto childrenDto){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        childrenService.saveChildren(childrenDto,username);

    }

    /**
     * GET requests for /children/{childrenId} endpoint.
     * method to retrieve a particular children from database using its id.
     * @param childrenId id of the children of interest.
     * @return childrenDto
     */
    @GetMapping("/children/{childrenId}")
    public ChildrenDto getChildrenById(@PathVariable int childrenId) {
        return childrenService.getChildrenById(childrenId);
    }

    /**
     * POST requests for /delete/children endpoint.
     * method to delete a particular children from database.
     * @param childrenDto to delete.
     */
    @PostMapping("/delete/children")
    public void deleteChildren(@RequestBody ChildrenDto childrenDto){
        childrenService.deleteChildren(childrenDto);
    }

}
