package com.lavanya.emailBatch.controller;

import com.lavanya.emailBatch.dto.NotificationDto;
import com.lavanya.emailBatch.email.EmailService;
import com.lavanya.emailBatch.proxies.NotificationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to control all the requests related to Notification object.
 * @author lavanya
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationProxy notificationProxy;

    @Autowired
    EmailService emailService;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);
    }

    /**
     * POST requests for send/notifications/profile/validation.
     * this method is used to send email notification via gmail smtp to recipients who have not provide official documents to validate their profile.
     */
    @Scheduled(cron = "${cron.expression}")
    @PostMapping("/send/notifications/profile/validation")
    public void alertEmailForUserWithNoProfileValidated(){

        List<NotificationDto> list = notificationProxy.getListOfUsersToWarnForProfileValidation();
        if(list != null) {
            for(NotificationDto notice:list) {

                String email = notice.getToEmail();
                String name = notice.getToFullId();
                String subject ="Alerte: Votre profil n'est toujours pas validé!";
                String text = "Bonjour " + name + ", \n" +
                        "Votre profil n'est malheureusement toujours pas validé. Merci de nous envoyer au plus vite une copie de votre pièce d'identité " +
                        "ainsi qu'un justificatif de domicile à l'adresse suivante servicesadministratifs@gardehurry.com. Pour rappel un profil validé est un profil plus rassurant pour les autres membres de la communauté! " +
                        "Cela ne fera qu'encourager la prise de contact! \n" +
                        "Cordialement, \n" +
                        "L'équipe Garde Hurry";

                emailService.sendSimpleMessage(email,subject, text);

            }
        }

    }

    /**
     * POST requests for send/notification/newProfile.
     * this method is used to send email notification via gmail smtp to recipients who have not provide official documents to validate their profile.
     */
    @PostMapping("/send/notification/newProfile")
    public void alertEmailForNewUserToValidateProfile(@RequestBody NotificationDto notificationDto){

        String email = notificationDto.getToEmail();
        String name = notificationDto.getToFullId();
        String subject ="Félicitations: Il vous reste une dernière étape avant de valider votre profil!";
        String text = "Bonjour " + name + ", \n" +
                "Bienvenue dans la communauté Garde Hurry. Votre profil n'est pas encore validé. Merci de nous envoyer au plus vite une copie de votre pièce d'identité " +
                "ainsi qu'un justificatif de domicile à l'adresse suivante servicesadministratifs@gardehurry.com. Un profil validé est un profil plus rassurant pour les autres membres de la communauté! " +
                "Cela ne fera qu'encourager la prise de contact! \n" +
                "Cordialement, \n" +
                "L'équipe Garde Hurry";

        emailService.sendSimpleMessage(email,subject, text);
    }
}
