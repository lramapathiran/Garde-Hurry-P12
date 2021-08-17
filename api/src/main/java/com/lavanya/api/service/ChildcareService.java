package com.lavanya.api.service;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.ChildrenDto;
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

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
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
    UserService userService;

    @Autowired
    ChildrenMapper childrenMapper;

    @Autowired
    ChildcareRepository childcareRepository;

    @Autowired
    ChildrenRepository childrenRepository;

    public ChildcareDto saveChildcare(ChildcareDto childcareDto) {

        Childcare childcare = childcareMapper.childcareDtoToChildcare(childcareDto);
        childcare.setValidated(false);
        childcare.setComplete(false);
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
}
