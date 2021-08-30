package com.lavanya.api.service;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.error.ChildrenToWatchAlreadyExistException;
import com.lavanya.api.mapper.ChildcareMapper;
import com.lavanya.api.mapper.ChildrenMapper;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.Childcare;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.ChildcareRepository;
import com.lavanya.api.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Service provider for all business functionalities related to Childcare class.
 * @author lavanya
 */
@Service
public class ChildcareService {

    @Autowired
    ChildcareMapper childcareMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    ChildrenMapper childrenMapper;

    @Autowired
    ChildcareRepository childcareRepository;

    @Autowired
    ChildrenRepository childrenRepository;

    public ChildcareDto saveChildcare(ChildcareDto childcareDto, String username) {

        User userInNeed = userService.findUserByUsername(username);

        Childcare childcare = childcareMapper.childcareDtoToChildcare(childcareDto);
        childcare.setUserInNeed(userInNeed);
        childcare.setComplete(false);
        childcare.setAccomplished(false);
        childcare.setInChargeComment(false);
        childcare.setInNeedComment(false);
        Childcare childcareSaved = childcareRepository.save(childcare);
        ChildcareDto childcareDtoSaved = childcareMapper.INSTANCE.childcareToChildcareDto(childcareSaved);

        return childcareDtoSaved;

    }

    public ChildcareDto getChildcareById(int childcareId) {

        Childcare childcare = childcareRepository.findById(childcareId).get();
        int id = childcare.getUserInNeed().getId();
        UserDto userDto = userService.getUserById(id);

        ChildcareDto childcareDto = childcareMapper.INSTANCE.childcareToChildcareDto(childcare);
        childcareDto.setUserDtoInNeed(userDto);
        return childcareDto;

    }

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

    public void deleteChildrenToWatch(int childrenToWatchId, int childcareId) {

        Childcare childcare = childcareRepository.findById(childcareId).get();
        Children children = childrenRepository.findById(childrenToWatchId).get();

        children.getChildcares().remove(childcare);

        childrenRepository.save(children);

    }

    public void completeRequest(int childcareId) {
        Childcare childcare = childcareRepository.findById(childcareId).get();
        childcare.setComplete(true);
        childcareRepository.save(childcare);

    }

    public void deleteChildcare(int childcareId) {
        Childcare childcare = childcareRepository.findById(childcareId).get();
        childcareRepository.delete(childcare);
    }

    public void updateChildcareValidationStatus(ChildcareDto childcareDto) {
        Childcare childcare = childcareMapper.INSTANCE.childcareDtoToChildcare(childcareDto);
        childcareRepository.save(childcare);
    }

    public void accomplishChildcare(int childcareId) {
        Childcare childcare = childcareRepository.findById(childcareId).get();
        childcare.setAccomplished(true);
        childcareRepository.save(childcare);
    }

    public List<ChildcareDto> getChildcaresListOfUserInNeedUnCommented(String username) {
        User userInNeed = userService.findUserByUsername(username);
        List<Childcare> list = childcareRepository.findChildcaresListOfUserInNeedNotCommentedYet(userInNeed);
        List<ChildcareDto> listDto = childcareMapper.listChildcareToListChildcareDto(list);

        return listDto;
    }

    public List<ChildcareDto> getChildcaresListOfUserInChargeUnCommented(String username) {

        User userInCharge = userService.findUserByUsername(username);
        List<Childcare> list = childcareRepository.findChildcaresListOfUserInChargeNotCommentedYet(userInCharge);
        List<ChildcareDto> listDto = childcareMapper.listChildcareToListChildcareDto(list);

        return listDto;
    }

    public Integer getCountOfChildcaresAccomplishedOfUserWatching(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAccomplishedByUserWatchingId(user);
    }

    public Integer getCountOfChildcaresAccomplishedOfUserInNeed(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAskedByUserInNeedIdAndAccomplished(user);
    }

    public Integer getCountOfChildcaresAccomplishedAndNotCommentedOfUserWatching(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAccomplishedByUserWatchingIdNotCommentedYet(user);
    }

    public Integer getCountOfChildcaresAccomplishedAndNotCommentedOfUserInNeed(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresAccomplishedByUserInNeedIdNotCommentedYet(user);
    }

    public Integer getCountOfChildcaresToValidateOfUserInCharge(String username){
        User user = userService.findUserByUsername(username);
        return childcareRepository.numberOfChildcaresToValidateByUserInCharge(user);
    }
}
