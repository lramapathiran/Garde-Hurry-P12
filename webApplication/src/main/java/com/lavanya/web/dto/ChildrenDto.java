package com.lavanya.web.dto;

import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Bean representing a data transfer Object ChildrenDto.
 * ChildrenDto has all attributes required to display a children.
 * @author lavanya
 */
public class ChildrenDto {

    private Integer id;
    private String name;
    private Integer age;
    private UserDto user;

    @Enumerated(EnumType.STRING)
    School school;

    private List<ChildcareDto> childcareDtos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<ChildcareDto> getChildcareDtos() {
        return childcareDtos;
    }

    public void setChildcareDtos(List<ChildcareDto> childcareDtos) {
        this.childcareDtos = childcareDtos;
    }
}
