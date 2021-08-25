package com.lavanya.web.comparator;

import com.lavanya.web.dto.UserDto;

import java.util.Comparator;

public class UserDtoLastNameComparator implements Comparator<UserDto> {

    @Override
    public int compare(UserDto b1, UserDto b2) {
        if(b1.getLastName().compareToIgnoreCase(b2.getLastName())>=1){
            return 1;
        }
        if(b1.getLastName().compareToIgnoreCase(b2.getLastName())==0){
            return 0;
        }
        else{
            return -1;
        }
    }

}
