package com.lavanya.api.dto;

import com.lavanya.api.enums.School;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class ChildrenDto {

    private Integer id;
    private String name;
    private Integer age;
    private UserDto user;

    @Enumerated(EnumType.STRING)
    School school;

    private List<ChildcareDto> childcareList;

}