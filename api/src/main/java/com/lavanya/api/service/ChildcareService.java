package com.lavanya.api.service;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.error.ChildrenToWatchAlreadyExistException;
import com.lavanya.api.mapper.ChildcareMapper;
import com.lavanya.api.model.Childcare;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.ChildcareRepository;
import com.lavanya.api.repository.ChildrenRepository;
import com.lavanya.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

/**
 * Service provider for all business functionalities related to Childcare class.
 * @author lavanya
 */
@Service
public class ChildcareService {

    @Autowired
    ChildcareMapper childcareMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChildcareRepository childcareRepository;

    @Autowired
    ChildrenRepository childrenRepository;

    /**
     * method to save a Childcare in database.
     * @param username of the user who requested the childcare.
     * @param childcareDto object that needs to be mapped to childcare entity and saved in DB.
     * @return ChildcareDto
     */
    public ChildcareDto saveChildcare(ChildcareDto childcareDto, String username) {

        User userInNeed = userService.findUserByUsername(username);
        User userWatching = userRepository.findUserByUuid(childcareDto.getUserDtoWatching().getUuid());

        Childcare childcare = childcareMapper.childcareDtoToChildcare(childcareDto);
        childcare.setUserInNeed(userInNeed);
        childcare.setUserWatching(userWatching);
        childcare.setComplete(false);
        childcare.setAccomplished(false);
        childcare.setInChargeComment(false);
        childcare.setInNeedComment(false);
        Childcare childcareSaved = childcareRepository.save(childcare);
        ChildcareDto childcareDtoSaved = childcareMapper.INSTANCE.childcareToChildcareDto(childcareSaved);

        return childcareDtoSaved;

    }

    /**
     * method to retrieve a particular childcare from DB using its id.
     * @param childcareId id of the childcare of interest.
     * @return ChildcareDto object.
     */
    public ChildcareDto getChildcareById(int childcareId) {

        Childcare childcare = childcareRepository.findById(childcareId).get();
        UUID id = childcare.getUserInNeed().getUuid();
        UserDto userDto = userService.getUserById(id);

        ChildcareDto childcareDto = childcareMapper.INSTANCE.childcareToChildcareDto(childcare);
        childcareDto.setUserDtoInNeed(userDto);
        return childcareDto;

    }

    /**
     * method to add a Children to watch to a childcare and save it in DB.
     * @param childrenToWatchId id of the children to watch.
     * @param childcareId id of the childcare for which the children needs to be associated.
     */
    public void saveChildrenToWatch(int childrenToWatchId, int childcareId) throws ChildrenToWatchAlreadyExistException{

        Childcare childcare = childcareRepository.findById(childcareId).get();
        Children children = childrenRepository.findById(childrenToWatchId).get();
        List<Childcare> childcares = children.getChildcares();

        Childcare childcareToCheck = childcares.stream()
                .filter(childcareToMatch -> childcare.equals(childcareToMatch.getId()))
                .findAny()
                .orElse(null);

        if(childcareToCheck == null) {
            children.getChildcares().add(childcare);
            childrenRepository.save(children);
        }else{
            throw new ChildrenToWatchAlreadyExistException(

                    "L'enfant " + children.getName() + " a déjà été ajouté dans cette garde, impossible de l'ajouter une seconde fois"
            );
        }


    }

    /**
     * method to delete a Children to watch associated to childcare.
     * @param childrenToWatchId id of the children to delete from childcare.
     * @param childcareId id of the childcare for which the children needs to be deleted.
     */
    public void deleteChildrenToWatch(int childrenToWatchId, int childcareId) {

        Childcare childcare = childcareRepository.findById(childcareId).get();
        Children children = childrenRepository.findById(childrenToWatchId).get();

        children.getChildcares().remove(childcare);

        childrenRepository.save(children);

    }

    /**
     * method to add all the information not added yet to an already existing and uncomplete childcare.
     * @param childcareId id of the childcare that needs to be completed.
     */
    public void completeRequest(int childcareId) {
        Childcare childcare = childcareRepository.findById(childcareId).get();
        childcare.setComplete(true);
        childcareRepository.save(childcare);

    }

    /**
     * method to delete a childcare from DB.
     * @param childcareId id of the childcare to delete.
     */
    public void deleteChildcare(int childcareId) {
        Childcare childcare = childcareRepository.findById(childcareId).get();
        childcareRepository.delete(childcare);
    }

    /**
     * method to update the validation status of an already existing childcare as validated/accepted or not by the user in charge.
     * @param childcareDto thats needs to be updated.
     */
    public void updateChildcareValidationStatus(ChildcareDto childcareDto) {
        Childcare childcare = childcareRepository.findById(childcareDto.getId()).get();
        childcare.setValidated(childcareDto.getValidated());
        childcareRepository.save(childcare);
    }

    /**
     * method to mark a childcare as accomplished by the user in charge.
     * @param childcareId id of the childcare to mark as accomplished.
     */
    public void accomplishChildcare(int childcareId) {
        Childcare childcare = childcareRepository.findById(childcareId).get();
        childcare.setAccomplished(true);
        childcareRepository.save(childcare);
    }

    /**
     * method to retrieve list of all ChildcareDtos made by a user in need and not commented yet by him.
     * @param username of the user connected identified as a user in need and for who we need the list.
     * @return list of ChildcareDtos.
     */
    public List<ChildcareDto> getChildcaresListOfUserInNeedUnCommented(String username) {
        User userInNeed = userService.findUserByUsername(username);
        List<Childcare> list = childcareRepository.findChildcaresListOfUserInNeedNotCommentedYet(userInNeed);
        List<ChildcareDto> listDto = childcareMapper.listChildcareToListChildcareDto(list);

        return listDto;
    }

    /**
     * method to retrieve list of all ChildcareDtos made by a user in charge and not commented yet by him.
     * @param username of the user connected identified as a user in charge and for who we need the list.
     * @return list of ChildcareDtos.
     */
    public List<ChildcareDto> getChildcaresListOfUserInChargeUnCommented(String username) {

        User userInCharge = userService.findUserByUsername(username);
        List<Childcare> list = childcareRepository.findChildcaresListOfUserInChargeNotCommentedYet(userInCharge);
        List<ChildcareDto> listDto = childcareMapper.listChildcareToListChildcareDto(list);

        return listDto;
    }

    /**
     * method to count the amount of all childcares accomplished by a user of interest identified as the user in charge.
     * @param username of the user of interest for whom we need the count of childcares.
     * @return Integer.
     */
    public Integer getCountOfChildcaresAccomplishedOfUserWatching(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAccomplishedByUserWatchingId(user);
    }

    /**
     * method to count the amount of all childcares accomplished for a user of interest identified as the user in need.
     * @param username of the user of interest for whom we need the count of childcares.
     * @return Integer.
     */
    public Integer getCountOfChildcaresAccomplishedOfUserInNeed(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAskedByUserInNeedIdAndAccomplished(user);
    }

    /**
     * method to count the amount of all childcares accomplished but not commented yet by a user of interest identified as the user in charge.
     * @param username of the user of interest for whom we need the count of childcares.
     * @return Integer.
     */
    public Integer getCountOfChildcaresAccomplishedAndNotCommentedOfUserWatching(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAccomplishedByUserWatchingIdNotCommentedYet(user);
    }

    /**
     * method to count the amount of all childcares accomplished but not commented yet by a user of interest identified as the user in need.
     * @param username of the user of interest for whom we need the count of childcares.
     * @return Integer.
     */
    public Integer getCountOfChildcaresAccomplishedAndNotCommentedOfUserInNeed(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAccomplishedByUserInNeedIdNotCommentedYet(user);
    }

    /**
     * method to count the amount of all childcares a user in charge needs to validate/accept.
     * @param username of the user of interest for whom we need the count of childcares to validate.
     * @return Integer.
     */
    public Integer getCountOfChildcaresToValidateOfUserInCharge(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresToValidateByUserInCharge(user);
    }
}
