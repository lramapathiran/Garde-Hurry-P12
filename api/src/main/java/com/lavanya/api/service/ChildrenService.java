package com.lavanya.api.service;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.mapper.ChildrenMapper;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
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
    UserService userService;

    @Autowired
    ChildrenRepository childrenRepository;

    @Autowired
    ChildrenMapper childrenMapper;

    /**
     * method to save a user child.
     * @param childrenDto that needs to be saved in database.
     * @param username of the user who is the parent of the children
     */
    public void saveChildren(ChildrenDto childrenDto, String username) {

        User childrenParent = userService.findUserByUsername(username);

        Children children = childrenMapper.INSTANCE.childrenDtoToChildren(childrenDto);
        children.setUser(childrenParent);

        childrenRepository.save(children);

    }

    public ChildrenDto getChildrenById(int childrenId) {

        Children children = childrenRepository.findById(childrenId).get();
        return childrenMapper.childrenToChildrenDto(children);
    }

    public void deleteChildren(ChildrenDto childrenDto) {

        Children children = childrenMapper.INSTANCE.childrenDtoToChildren(childrenDto);
        childrenRepository.delete(children);

    }
}
