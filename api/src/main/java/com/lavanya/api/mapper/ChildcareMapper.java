package com.lavanya.api.mapper;

import com.lavanya.api.dto.ChildcareDto;
import com.lavanya.api.model.Childcare;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChildcareMapper {

    ChildcareMapper INSTANCE = Mappers.getMapper(ChildcareMapper.class);

    Childcare childcareDtoToChildcare(ChildcareDto childcareDto);
    ChildcareDto childcareToChildcareDto(Childcare childcare);

    List<Childcare> listChildcareDtoToListChildcare(List<ChildcareDto> ChildcareDtosRequests);

    List<ChildcareDto> listChildcareToListChildcareDto(List<Childcare> ChildcareRequests);

    List<Childcare> ChildcareDtosToChildcares(List<ChildcareDto> ChildcareDtos);

    List<ChildcareDto> ChildcaresToChildcareDtos(List<Childcare> Childcares);
}
