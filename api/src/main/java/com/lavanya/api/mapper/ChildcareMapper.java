package com.lavanya.api.mapper;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.model.Childcare;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChildcareMapper {

    ChildcareMapper INSTANCE = Mappers.getMapper(ChildcareMapper.class);

    @Mapping(source = "userDtoInNeed", target = "userInNeed")
    Childcare childcareDtoToChildcare(ChildcareDto childcareDto);
    @Mapping(source = "userInNeed", target = "userDtoInNeed")
    ChildcareDto childcareToChildcareDto(Childcare childcare);

    List<Childcare> listChildcareDtoToListChildcare(List<ChildcareDto> childcareDtosRequests);
    List<ChildcareDto> listChildcareToListChildcareDto(List<Childcare> childcareRequests);

    List<Childcare> ChildcareDtosToChildcares(List<ChildcareDto> childcareDtos);
    List<ChildcareDto> ChildcaresToChildcareDtos(List<Childcare> childcares);

//    List<Children> listChildrenDtoToListChildren(List<ChildrenDto> childrenDtos);
//    List<ChildrenDto> listChildrenToListChildrenDto(List<Children> childrens);
}
