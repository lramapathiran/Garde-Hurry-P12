package com.lavanya.api.mapper;

import com.lavanya.api.dto.UserDto;
import com.lavanya.api.dto.UserToRegister;
import com.lavanya.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    UserRegistrationMapper INSTANCE = Mappers.getMapper(UserRegistrationMapper.class);

    User userToRegisterToUser(UserToRegister userToRegister);

    UserToRegister userToUserToRegister(User user);
}
