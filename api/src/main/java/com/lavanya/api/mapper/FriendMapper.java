package com.lavanya.api.mapper;

import com.lavanya.api.dto.FriendDto;
import com.lavanya.api.model.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    Friend friendDtoToFriend(FriendDto friendDto);
    FriendDto friendToFriendDto(Friend friend);
    List<FriendDto> listFriendToListFriendDto(List<Friend> sentInvitations);
    List<Friend> listFriendDtoToListFriend(List<FriendDto> sentFriendDtoInvitations);
}
