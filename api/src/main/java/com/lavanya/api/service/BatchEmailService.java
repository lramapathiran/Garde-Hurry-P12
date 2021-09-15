package com.lavanya.api.service;

import com.lavanya.api.model.Notification;
import com.lavanya.api.model.User;
import com.lavanya.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service provider for all business functionalities related to Notification class.
 * @author lavanya
 */
@Service
public class BatchEmailService {

    @Autowired
    UserRepository userRepository;

    public List<Notification> getListOfUserWithProfileNotValidatedYet() {

        List<User> users = userRepository.findListOfUserWithProfileNotValidatedYet();

        List<Notification> notifications = new ArrayList<>();


        if (users != null) {
            for (User user:users) {
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String fullId = firstName + " " + lastName;
                String email = user.getEmail();

                Notification userTowarn = new Notification(fullId, email);
                notifications.add(userTowarn);
            }
        }

        return notifications;
    }
}
