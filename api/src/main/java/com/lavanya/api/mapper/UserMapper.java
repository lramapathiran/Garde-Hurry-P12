package com.lavanya.api.mapper;

import com.lavanya.api.dto.ChildrenDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.model.Children;
import com.lavanya.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);

    List<User> listUserDtoToListUser(List<UserDto> userDtos);
    List<UserDto> listUserToListUserDto(List<User> users);

//    Page<User> pageUserDtoToPageUser(Page<UserDto> pageUserDtos);
//    Page<UserDto> pageUserToPageUserDto(Page<User> pageUsers);

}
