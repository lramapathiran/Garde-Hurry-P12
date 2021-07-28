package com.lavanya.api.mapper;

import com.lavanya.api.dto.CommentDto;
import com.lavanya.api.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment commentDtoToComment (CommentDto commentDto);
    CommentDto commentDtoToComment (Comment comment);

    List<CommentDto> listCommentToCommentDto(List<Comment> commentsReceived);
    List<Comment> listCommentDtoToComment(List<CommentDto> commentDtosReceived);
}
