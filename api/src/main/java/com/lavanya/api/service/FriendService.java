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
import java.util.ArrayList;
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
    UserService userService;

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
     * method to verify if a friend relationship exists between two users even if this relation is still awaiting a validation from one of the parties.
     * @param username of the user concerned.
     * @param userProfileVisitedId id of the second user involved
     */
    public Boolean isUsersFriends(String username, int userProfileVisitedId) {

        User userWhoInvite =  userService.findUserByUsername(username);

        UserDto userDtoInvited = userService.getUserById(userProfileVisitedId);
        User userInvited = userMapper.userDtoToUser(userDtoInvited);


        Boolean response1 = friendRepository.existsByUserWhoInviteAndByUserInvited(userWhoInvite,userInvited);
        Boolean response2 = friendRepository.existsByUserInvitedAndUserWhoInvite(userWhoInvite, userInvited);
        if(response1 || response2) {
            return true;
        }else{
            return false;
        }
    }

    public List<FriendDto> getListOfFriendRequests(String username) {

        User userInvited =  userService.findUserByUsername(username);

        List<Friend> friendsList = friendRepository.findByUserInvitedIdOrderByDateDesc(userInvited.getId());
        List<Friend> friendsRequestsList = new ArrayList<>();
        for(Friend friend : friendsList){
            if(!friend.getAccepted()){
                friendsRequestsList.add(friend);
            }

        }
        List<FriendDto> friendDtos = friendMapper.INSTANCE.listFriendToListFriendDto(friendsRequestsList);

        return friendDtos;
    }

    public void updateFriend(int id) {
        Friend friend = friendRepository.findById(id).get();
        friend.setAccepted(true);
        friendRepository.save(friend);
    }

    public void deleteFriend(int id) {
        Friend friend = friendRepository.findById(id).get();
        friendRepository.delete(friend);
    }

    public FriendDto findFriendRelationshipByBothUsersId(int userInvitedId, String username){

        User userWhoInvite =  userService.findUserByUsername(username);
        Friend friend1 = friendRepository.findByUserInvitedIdAndUserWhoInviteId(userInvitedId,userWhoInvite.getId());
        Friend friend2 = friendRepository.findByUserInvitedIdAndUserWhoInviteId(userWhoInvite.getId(),userInvitedId);

        FriendDto friendDto = new FriendDto();
        if(friend1 == null && friend2 !=null) {
            friendDto = friendMapper.INSTANCE.friendToFriendDto(friend2);
        }

        if(friend1 != null && friend2 ==null) {
            friendDto = friendMapper.INSTANCE.friendToFriendDto(friend1);
        }

        return friendDto;
    }

    public List<FriendDto> getListOfAllFriendsByUser(String username){

        User user = userService.findUserByUsername(username);
        List<Friend> list = friendRepository.findByUserInvitedIdOrUserWhoInviteId(user);


        return friendMapper.INSTANCE.listFriendToListFriendDto(list);
    }

    public Integer getcountOfFriendsByUser(String username){
        User user = userService.findUserByUsername(username);
        return friendRepository.countOfFriends(user);
    }
}
