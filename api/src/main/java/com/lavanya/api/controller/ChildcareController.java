package com.lavanya.api.controller;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.service.ChildcareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller to control all the requests related to Childcare object.
 * @author lavanya
 */
@RestController
public class ChildcareController {

    @Autowired
    ChildcareService childcareService;

    @PostMapping("/saveChildcare")
    ChildcareDto saveChildcare(@RequestBody ChildcareDto childcareDto){
        return childcareService.saveChildcare(childcareDto);
    }
}
