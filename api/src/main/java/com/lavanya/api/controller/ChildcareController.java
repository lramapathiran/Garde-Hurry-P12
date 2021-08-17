package com.lavanya.api.controller;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.error.ChildrenToWatchAlreadyExistException;
import com.lavanya.api.service.ChildcareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/childcare/{id}")
    ChildcareDto getChildcareById(@PathVariable("id") int childcareId){
        return childcareService.getChildcareById(childcareId);
    }

    @PostMapping(value="/saveChildrenToWatch/{childrenToWatchId}/{childcareId}")
    void saveChildrenToWatchToChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId){

        try{
            childcareService.saveChildrenToWatch(childrenToWatchId,childcareId);
        }catch(Exception e){
            throw new ChildrenToWatchAlreadyExistException("Cet enfant a déjà été ajouté pour cette garde, impossible de l'ajouter une seconde fois");
        }

    }

    @PostMapping(value="/deleteChildrenToWatch/{childrenToWatchId}/{childcareId}")
    void deleteChildrenToWatchInChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId) {
        childcareService.deleteChildrenToWatch(childrenToWatchId,childcareId);
    }

    @PostMapping(value="validate/request/childcare/{childcareId}")
    void completeChildcareRequest(@PathVariable("childcareId") int childcareId) {
        childcareService.completeRequest(childcareId);
    }

    @PostMapping(value="/validate/childcare")
    void validateOrNotChildcare(@RequestBody ChildcareDto childcareDto){
        childcareService.updateChildcareValidationStatus(childcareDto);
    }

    @PostMapping(value="/deleteChildcare/{childcareId}")
    void deleteChildcare(@PathVariable("childcareId") int childcareId) {
        childcareService.deleteChildcare(childcareId);
    }


}
