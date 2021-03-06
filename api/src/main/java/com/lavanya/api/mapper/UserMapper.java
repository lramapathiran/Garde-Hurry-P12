package com.lavanya.api.mapper;

import com.lavanya.api.dto.UserDto;
import com.lavanya.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    List<User> listUserDtoToListUser(List<UserDto> userDtos);
    List<UserDto> listUserToListUserDto(List<User> users);
}
