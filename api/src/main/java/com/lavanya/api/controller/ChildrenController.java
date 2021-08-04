package com.lavanya.api.controller;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.service.ChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        childrenService.saveChildren(childrenDto);

    }

}
