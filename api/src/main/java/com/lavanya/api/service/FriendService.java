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
import java.util.UUID;

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

    @Autowired
    UserRepository userRepository;

    /**
     * method to save an awaited friend relationship between two users.
     * @param friendDto to save in database.
     * @return the entity Friend saved in DB
     */
    public Friend save(FriendDto friendDto) {

        User userWhoInvite = userRepository.findUserByUuid(friendDto.getUserWhoInvite().getUuid());
        User userInvited = userRepository.findUserByUuid(friendDto.getUserInvited().getUuid());

        Friend friend = friendMapper.friendDtoToFriend(friendDto);

        LocalDate date = LocalDate.now();
        friend.setDate(date);

        friend.setUserWhoInvite(userWhoInvite);
        friend.setUserInvited(userInvited);
        friend.setAccepted(false);

        return friendRepository.save(friend);
    }

    /**
     * method to verify if a friend relationship exists between two users even if this relation is still awaiting a validation from one of the parties.
     * @param username of the user concerned.
     * @param userProfileVisitedId UUID of the second user involved
     * @return a boolean true or false
     */
    public Boolean isUsersFriends(String username, UUID userProfileVisitedId) {

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

    /**
     * method to retrieve list of all Friends requests sent by a user of interest but not accepted yet.
     * @param username of the user of interest for whom we need the list of friends request he sent
     * @return list of FriendDtos.
     */
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

    /**
     * method to validate a friend request as accepted by the user who was invited.
     * @param id of the friend entity to accept.
     */
    public void updateFriend(int id) {
        Friend friend = friendRepository.findById(id).get();
        friend.setAccepted(true);
        friendRepository.save(friend);
    }

    /**
     * method to delete a friend request not accepted yet or friend relation already accepted.
     * @param id of the friend entity of interest.
     */
    public void deleteFriend(int id) {
        Friend friend = friendRepository.findById(id).get();
        friendRepository.delete(friend);
    }

    /**
     * method to retrieve a friend entity using both users involved.
     * @param userInvitedId uuid of the user involved in the friend relationship.
     * @param username of the user connected which is the second user involved in the friend relationship.
     * @return FriendDto.
     */
    public FriendDto findFriendRelationshipByBothUsersId(UUID userInvitedId, String username){

        User userWhoInvite =  userService.findUserByUsername(username);
        User userInvited = userRepository.findUserByUuid(userInvitedId);

        Friend friend1 = friendRepository.findByUserInvitedIdAndUserWhoInviteId(userInvited.getId(),userWhoInvite.getId());
        Friend friend2 = friendRepository.findByUserInvitedIdAndUserWhoInviteId(userWhoInvite.getId(),userInvited.getId());

        FriendDto friendDto = new FriendDto();
        if(friend1 == null && friend2 !=null) {
            friendDto = friendMapper.INSTANCE.friendToFriendDto(friend2);
        }

        if(friend1 != null && friend2 ==null) {
            friendDto = friendMapper.INSTANCE.friendToFriendDto(friend1);
        }

        return friendDto;
    }

    /**
     * method to retrieve list of all Friends of the user connected.
     * @param username of the user of interest for whom we need the list of friends.
     * @return list of FriendDtos.
     */
    public List<FriendDto> getListOfAllFriendsByUser(String username){

        User user = userService.findUserByUsername(username);
        List<Friend> list = friendRepository.findByUserInvitedIdOrUserWhoInviteId(user);


        return friendMapper.INSTANCE.listFriendToListFriendDto(list);
    }

    /**
     * method to count the amount of Friends the user connected has.
     * @param username of the user of interest for whom we need the count of friends.
     * @return Integer.
     */
    public Integer getcountOfFriendsByUser(String username){
        User user = userService.findUserByUsername(username);
        return friendRepository.countOfFriends(user);
    }
}
