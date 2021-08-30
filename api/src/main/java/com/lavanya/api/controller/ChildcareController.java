package com.lavanya.api.controller;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.error.ChildrenToWatchAlreadyExistException;
import com.lavanya.api.service.ChildcareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.saveChildcare(childcareDto, username);
    }

    @GetMapping("/childcare/{id}")
    ChildcareDto getChildcareById(@PathVariable("id") int childcareId){
        return childcareService.getChildcareById(childcareId);
    }

    @PostMapping("/saveChildrenToWatch/{childrenToWatchId}/{childcareId}")
    void saveChildrenToWatchToChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId){

        try{
            childcareService.saveChildrenToWatch(childrenToWatchId,childcareId);
        }catch(Exception e){
            throw new ChildrenToWatchAlreadyExistException("Cet enfant a déjà été ajouté pour cette garde, impossible de l'ajouter une seconde fois");
        }

    }

    @PostMapping("/deleteChildrenToWatch/{childrenToWatchId}/{childcareId}")
    void deleteChildrenToWatchInChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId) {
        childcareService.deleteChildrenToWatch(childrenToWatchId,childcareId);
    }

    @PostMapping("validate/request/childcare/{childcareId}")
    void completeChildcareRequest(@PathVariable("childcareId") int childcareId) {
        childcareService.completeRequest(childcareId);
    }

    @PostMapping("/validate/childcare")
    void validateOrNotChildcare(@RequestBody ChildcareDto childcareDto){
        childcareService.updateChildcareValidationStatus(childcareDto);
    }

    @PostMapping("/deleteChildcare/{childcareId}")
    void deleteChildcare(@PathVariable("childcareId") int childcareId) {
        childcareService.deleteChildcare(childcareId);
    }

    @PostMapping("/accomplish/childcare/{childcareId}")
    void accomplishChildcare(@PathVariable("childcareId") int childcareId){
        childcareService.accomplishChildcare(childcareId);
    }

    @GetMapping("/userInNeed/childcares/Uncommented")
    List<ChildcareDto> getChildcaresOfUserInNeedNotCommented(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getChildcaresListOfUserInNeedUnCommented(username);
    }

    @GetMapping("/userInCharge/childcares/Uncommented")
    List<ChildcareDto> getChildcaresOfUserInChargeNotCommented(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getChildcaresListOfUserInChargeUnCommented(username);
    }

    @GetMapping("/user/count/positiveBadges")
    Integer countOfPositiveBadgesByUserId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedOfUserWatching(username);
    }

    @GetMapping("/user/count/negativeBadges")
    Integer countOfNegativeBadgesByUserId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedOfUserInNeed(username);
    }

    @GetMapping("/userInCharge/count/childcaresToComment")
    Integer countOfChildcaresToCommentByUserInChargeId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedAndNotCommentedOfUserWatching(username);
    }

    @GetMapping("/userInNeed/count/childcaresToComment")
    Integer countOfChildcaresToCommentByUserInNeedId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedAndNotCommentedOfUserInNeed(username);
    }

    @GetMapping("/userInCharge/count/childcaresToValidate")
    Integer countOfChildcaresToValidateByUserInChargeId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresToValidateOfUserInCharge(username);
    }

}
