package com.lavanya.api.service;

import com.lavanya.api.dto.CommentDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.mapper.ChildcareMapper;
import com.lavanya.api.mapper.CommentMapper;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.Childcare;
import com.lavanya.api.model.Comment;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.ChildcareRepository;
import com.lavanya.api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service provider for all business functionalities related to Comment class.
 * @author lavanya
 */
@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ChildcareRepository childcareRepository;

    @Autowired
    UserMapper userMapper;

    public CommentDto saveComment(CommentDto commentDto, int childcareId, String feedbackAuthor) {

        Comment comment = commentMapper.commentDtoToComment(commentDto);
        comment.setTime(LocalDateTime.now());

        Childcare childcare = childcareRepository.findById(childcareId).get();

        if(feedbackAuthor.equals("childParent")){
            comment.setUserComment(childcare.getUserInNeed());
            comment.setUserCommented(childcare.getUserWatching());
            childcare.setInNeedComment(true);
        }else{
            comment.setUserComment(childcare.getUserWatching());
            comment.setUserCommented(childcare.getUserInNeed());
            childcare.setInChargeComment(true);
        }

        Childcare childcareSaved = childcareRepository.save(childcare);
        comment.setChildcare(childcareSaved);

        Comment commentSaved = commentRepository.save(comment);
        CommentDto commentDtoSaved = commentMapper.INSTANCE.commentToCommentDto(commentSaved);

        return  commentDtoSaved;
    }

    public List<CommentDto> geCommentsOnUserByUserId(UserDto userDto) {

        User user = userMapper.userDtoToUser(userDto);
        List<Comment> list = commentRepository.findByUserCommentedOrderByTimeDesc(user);
        List<CommentDto> listDtos = commentMapper.listCommentToCommentDto(list);

        return listDtos;
    }
}
