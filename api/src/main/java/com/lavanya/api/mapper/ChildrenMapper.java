package com.lavanya.api.mapper;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.model.Children;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChildrenMapper {

    ChildrenMapper INSTANCE = Mappers.getMapper(ChildrenMapper.class);

    Children childrenDtoToChildren(ChildrenDto childrenDto);
    ChildrenDto childrenToChildrenDto(Children children);
}
