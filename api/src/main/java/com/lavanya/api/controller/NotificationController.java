package com.lavanya.api.controller;

import com.lavanya.api.model.Notification;
import com.lavanya.api.service.BatchEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest Controller to control all the requests related to Notification object.
 * @author lavanya
 */
@RestController
public class NotificationController {

    @Autowired
    BatchEmailService batchEmailService;

    /**
     * GET requests for /notifications/profile/validation.
     *
     * @return list of Notifications object which is the list of users that have not yet provided official documents to validate their profile.
     */
    @GetMapping("/notifications/profile/validation")
    List<Notification> getListOfUsersToWarnForProfileValidation(){

        List<Notification> notifications = new ArrayList<>();

        notifications = batchEmailService.getListOfUserWithProfileNotValidatedYet();

        if(notifications == null) {
            System.out.println("Aucun rappel à faire pour les utilisateurs dont le profil reste invalidé!");
        }

        return notifications;

    }
}
