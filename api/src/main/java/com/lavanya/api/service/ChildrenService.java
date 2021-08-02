package com.lavanya.api.service;

import com.lavanya.api.model.Children;
import com.lavanya.api.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service provider for all business functionalities related to Children class.
 * @author lavanya
 */
@Service
public class ChildrenService {

    @Autowired
    ChildrenRepository childrenRepository;

    /**
     * method to save a user child.
     * @param children that needs to be saved in database.
     */
    public void saveChildren(Children children) {

        childrenRepository.save(children);

    }
}
