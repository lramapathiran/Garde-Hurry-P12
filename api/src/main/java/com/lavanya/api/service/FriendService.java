package com.lavanya.api.service;

import com.lavanya.api.dto.FriendDto;
import com.lavanya.api.dto.UserDto;
import com.lavanya.api.mapper.FriendMapper;
import com.lavanya.api.mapper.UserMapper;
import com.lavanya.api.model.Friend;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.FriendRepository;
import com.lavanya.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service provider for all business functionalities related to Friend class.
 * @author lavanya
 */
@Service
public class FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * method to save an awaited friend relationship between two users.
     * @param friendDto to save in database.
     */
    public void save(FriendDto friendDto) {


        UserDto userDtoWhoInvite = friendDto.getUserWhoInvite();
        UserDto userDtoInvited = friendDto.getUserInvited();

        User userWhoInvite = userMapper.userDtoToUser(userDtoWhoInvite);
        User userInvited = userMapper.userDtoToUser(userDtoInvited);

        Friend friend = friendMapper.friendDtoToFriend(friendDto);

        LocalDate date = LocalDate.now();
        friend.setDate(date);

        friend.setUserWhoInvite(userWhoInvite);
        friend.setUserInvited(userInvited);
        friend.setAccepted(false);

        friendRepository.save(friend);

    }

    /**
     * method to verify if a friend relationship exists between two users.
     * @param userConnectedId id of one the user concerned.
     * @param userProfileVisitedId id of the second user involved
     */
    public Boolean isUsersFriends(int userConnectedId, int userProfileVisitedId) {

        User userWhoInvite =  userRepository.findById(userConnectedId).get();
        User userInvited = userRepository.findById(userProfileVisitedId).get();

        Boolean response1 = friendRepository.existsByUserWhoInviteAndByUserInvited(userWhoInvite,userInvited);
        Boolean response2 = friendRepository.existsByUserInvitedAndUserWhoInvite(userWhoInvite, userInvited);
        if(response1 || response2) {
            return true;
        }else{
            return false;
        }
    }

    public List<FriendDto> getListOfFriendRequests(int userConnectedId) {
        List<Friend> friendsList = friendRepository.findByUserInvitedIdOrderByDateDesc(userConnectedId);
        List<FriendDto> friendDtos = friendMapper.listFriendToListFriendDto(friendsList);

        return friendDtos;
    }

}
