package com.lavanya.api.service;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.mapper.ChildcareMapper;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.Childcare;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.ChildcareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;

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
    ChildcareRepository childcareRepository;


    public ChildcareDto saveChildcare(ChildcareDto childcareDto) {

        Childcare childcare = childcareMapper.childcareDtoToChildcare(childcareDto);
        childcare.setValidated(false);
        Childcare childcareSaved = childcareRepository.save(childcare);
        ChildcareDto childcareDtoSaved = childcareMapper.INSTANCE.childcareToChildcareDto(childcareSaved);

        return childcareDtoSaved;

    }
}
