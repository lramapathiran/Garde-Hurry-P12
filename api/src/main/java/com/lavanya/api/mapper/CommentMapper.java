package com.lavanya.api.mapper;

import com.lavanya.api.dto.CommentDto;
import com.lavanya.api.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "childcareDto", target = "childcare")
    Comment commentDtoToComment (CommentDto commentDto);
    @Mapping(source = "childcare", target = "childcareDto")
    CommentDto commentToCommentDto (Comment comment);

    List<CommentDto> listCommentToCommentDto(List<Comment> commentsReceived);
    List<Comment> listCommentDtoToComment(List<CommentDto> commentDtosReceived);
}
