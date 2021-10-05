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

    /**
     * POST requests for /saveChildcare endpoint.
     * method to save a Childcare in database.
     * @param childcareDto object that needs to be mapped to childcare entity and saved in DB.
     * @return ChildcareDto
     */
    @PostMapping("/saveChildcare")
    public ChildcareDto saveChildcare(@RequestBody ChildcareDto childcareDto){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.saveChildcare(childcareDto, username);
    }

    /**
     * GET requests for /childcare/{id} endpoint.
     * method to retrieve a particular childcare from DB using its id.
     * @param childcareId id of the childcare of interest.
     * @return ChildcareDto object.
     */
    @GetMapping("/childcare/{id}")
    public ChildcareDto getChildcareById(@PathVariable("id") int childcareId){
        return childcareService.getChildcareById(childcareId);
    }

    /**
     * POST requests for saveChildrenToWatch/{childrenToWatchId}/{childcareId} endpoint.
     * method to add a Children to watch to a childcare and save it in DB.
     * @param childrenToWatchId id of the children to watch.
     * @param childcareId id of the childcare for which the children needs to be associated.
     */
    @PostMapping("/saveChildrenToWatch/{childrenToWatchId}/{childcareId}")
    public void saveChildrenToWatchToChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId){

        try{
            childcareService.saveChildrenToWatch(childrenToWatchId,childcareId);
        }catch(Exception e){
            throw new ChildrenToWatchAlreadyExistException("Cet enfant a déjà été ajouté pour cette garde, impossible de l'ajouter une seconde fois");
        }

    }

    /**
     * POST requests for /deleteChildrenToWatch/{childrenToWatchId}/{childcareId} endpoint.
     * method to delete a Children to watch associated to childcare.
     * @param childrenToWatchId id of the children to delete from childcare.
     * @param childcareId id of the childcare for which the children needs to be deleted.
     */
    @PostMapping("/deleteChildrenToWatch/{childrenToWatchId}/{childcareId}")
    public void deleteChildrenToWatchInChildcare(@PathVariable("childrenToWatchId") int childrenToWatchId, @PathVariable("childcareId") int childcareId) {
        childcareService.deleteChildrenToWatch(childrenToWatchId,childcareId);
    }

    /**
     * POST requests for validate/request/childcare/{childcareId} endpoint.
     * method to add all the information not added yet to an already existing and uncomplete childcare.
     * @param childcareId id of the childcare that needs to be completed.
     */
    @PostMapping("validate/request/childcare/{childcareId}")
    public void completeChildcareRequest(@PathVariable("childcareId") int childcareId) {
        childcareService.completeRequest(childcareId);
    }

    /**
     * POST requests for /validate/childcare endpoint.
     * method to update the validation status of an already existing childcare as validated/accepted or not by the user in charge.
     * @param childcareDto thats needs to be updated.
     */
    @PostMapping("/validate/childcare")
    public void validateOrNotChildcare(@RequestBody ChildcareDto childcareDto){
        childcareService.updateChildcareValidationStatus(childcareDto);
    }

    /**
     * POST requests for /deleteChildcare/{childcareId} endpoint.
     * method to delete a childcare from DB.
     * @param childcareId id of the childcare to delete.
     */
    @PostMapping("/deleteChildcare/{childcareId}")
    public void deleteChildcare(@PathVariable("childcareId") int childcareId) {
        childcareService.deleteChildcare(childcareId);
    }

    /**
     * POST requests for /accomplish/childcare/{childcareId} endpoint.
     * method to mark a childcare as accomplished by the user in charge.
     * @param childcareId id of the childcare to mark as accomplished.
     */
    @PostMapping("/accomplish/childcare/{childcareId}")
    public void accomplishChildcare(@PathVariable("childcareId") int childcareId){
        childcareService.accomplishChildcare(childcareId);
    }

    /**
     * GET requests for /userInNeed/childcares/Uncommented endpoint.
     * method to retrieve list of all ChildcareDtos made by a user in need and not commented yet by him.
     * @return list of ChildcareDtos.
     */
    @GetMapping("/userInNeed/childcares/Uncommented")
    public List<ChildcareDto> getChildcaresOfUserInNeedNotCommented(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getChildcaresListOfUserInNeedUnCommented(username);
    }

    /**
     * GET requests for /userInCharge/childcares/Uncommented endpoint.
     * method to retrieve list of all ChildcareDtos made by a user in charge and not commented yet by him.
     * @return list of ChildcareDtos.
     */
    @GetMapping("/userInCharge/childcares/Uncommented")
    public List<ChildcareDto> getChildcaresOfUserInChargeNotCommented(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getChildcaresListOfUserInChargeUnCommented(username);
    }

    /**
     * GET requests for /user/count/positiveBadges endpoint.
     * method to count the amount of all childcares accomplished by a user to count his positive badges.
     * @return Integer.
     */
    @GetMapping("/user/count/positiveBadges")
    public Integer countOfPositiveBadgesByUserId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedOfUserWatching(username);
    }

    /**
     * GET requests for /user/count/negativeBadges endpoint.
     * method to count the amount of all childcares accomplished for a user to count his negative badges.
     * @return Integer.
     */
    @GetMapping("/user/count/negativeBadges")
    public Integer countOfNegativeBadgesByUserId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedOfUserInNeed(username);
    }

    /**
     * GET requests for /userInCharge/count/childcaresToComment endpoint.
     * method to count the amount of all childcares accomplished but not commented yet by a user of interest identified as the user in charge.
     */
    @GetMapping("/userInCharge/count/childcaresToComment")
    public Integer countOfChildcaresToCommentByUserInChargeId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedAndNotCommentedOfUserWatching(username);
    }

    /**
     * GET requests for /userInNeed/count/childcaresToComment endpoint.
     * method to count the amount of all childcares accomplished but not commented yet by a user of interest identified as the user in need.
     * @return Integer.
     */
    @GetMapping("/userInNeed/count/childcaresToComment")
    public Integer countOfChildcaresToCommentByUserInNeedId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresAccomplishedAndNotCommentedOfUserInNeed(username);
    }

    /**
     * GET requests for /userInCharge/count/childcaresToValidate endpoint.
     * method to count the amount of all childcares a user in charge needs to validate/accept.
     * @return Integer.
     */
    @GetMapping("/userInCharge/count/childcaresToValidate")
    public Integer countOfChildcaresToValidateByUserInChargeId(){

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return childcareService.getCountOfChildcaresToValidateOfUserInCharge(username);
    }

}
