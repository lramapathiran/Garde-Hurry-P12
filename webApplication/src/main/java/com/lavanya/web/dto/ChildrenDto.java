package com.lavanya.web.dto;

import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ChildrenDto {

    private Integer id;
    private String name;
    private Integer age;
    private UserDto user;

    @Enumerated(EnumType.STRING)
    School school;

    private List<ChildcareDto> childcareList;
}
