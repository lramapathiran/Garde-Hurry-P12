package com.lavanya.emailBatch.controller;

import com.lavanya.emailBatch.dto.NotificationDto;
import com.lavanya.emailBatch.email.EmailService;
import com.lavanya.emailBatch.proxies.NotificationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Controller to control all the requests related to Notification object.
 * @author lavanya
 */
@RestController
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
     * this method is used to send email notification via gmail smtp to recipients who have not provided official documents to validate their profile.
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
     * this method is used to send email notification via gmail smtp to recipients who have just created a new user account and need to provide official documents to validate their profile.
     * @param notificationDto contient toutes les informations nécessaire à l'envoie de la notification.
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

    /**
     * POST requests for send/friendInvitation.
     * this method is used to send email notification via gmail smtp to recipient who has a request of friend from another user.
     * @param notificationDto contient toutes les informations nécessaire à l'envoie de la notification
     */
    @PostMapping(value="/send/friendInvitation")
    public void sendFriendInvitation(@RequestBody NotificationDto notificationDto){

        String email = notificationDto.getToEmail();
        String name = notificationDto.getFromFullId();
        String subject ="Vous avez une nouvelle demande d'amis";
        String text = name + " souhaite faire partie de vos amis. Rendez-vous sur votre espace pour accepter ou non sa demande! \n" +
                "Cordialement, \n" +
                "L'équipe Garde Hurry";

        emailService.sendSimpleMessage(email,subject, text);

    }

    /**
     * POST requests for send/childcareNotification.
     * this method is used to send email notification via gmail smtp to recipient who has been requested for a childcare from another user in need.
     * @param notificationDto contient toutes les informations nécessaire à l'envoie de la notification
     */
    @PostMapping(value="/send/childcareNotification")
    public void sendChildcareNotificationToUserInCharge(@RequestBody NotificationDto notificationDto) {
        String email = notificationDto.getToEmail();
        String name = notificationDto.getFromFullId();
        String date = notificationDto.getDate().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        String subject = name + " a besoin de vous pour une garde";
        String text = name + " voudrait que vous gardiez son/ses enfant(s) le " + date + " de " + notificationDto.getTimeStart() + " à " + notificationDto.getTimeEnd() +
                ". Rendez-vous sur votre espace pour accepter ou non sa demande!  \n" +
                "Cordialement, \n" +
                "L'équipe Garde Hurry";

        emailService.sendSimpleMessage(email,subject, text);
    }

    /**
     * POST requests for send/childcareNotification/acceptance.
     * this method is used to send email notification via gmail smtp to recipient who has its request for a childcare accepted from the user asked for help.
     * @param notificationDto contient toutes les informations nécessaire à l'envoie de la notification
     */
    @PostMapping(value="/send/childcareNotification/acceptance")
    public void sendChildcareAcceptanceNotificationToUserInNeed(@RequestBody NotificationDto notificationDto) {
        String email = notificationDto.getToEmail();
        String name = notificationDto.getFromFullId();
        String date = notificationDto.getDate().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        String subject = name + " a accepté votre demande de garde";
        String text = name + " est disponible le " + date + " de " + notificationDto.getTimeStart() + " à " + notificationDto.getTimeEnd() + " pour effectuer la garde demandée \n" +
                "Cordialement, \n" +
                "L'équipe Garde Hurry";

        emailService.sendSimpleMessage(email,subject, text);
    }

    /**
     * POST requests for send/childcareNotification/refusal.
     * this method is used to send email notification via gmail smtp to recipient who has its request for a childcare refused from the user asked for help.
     * @param notificationDto contient toutes les informations nécessaire à l'envoie de la notification.
     */
    @PostMapping(value="/send/childcareNotification/refusal")
    public void sendChildcareRefusalNotificationToUserInNeed(@RequestBody NotificationDto notificationDto) {
        String email = notificationDto.getToEmail();
        String name = notificationDto.getFromFullId();
        String date = notificationDto.getDate().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        String subject = name + " a refusé votre demande de garde";
        String text = name + " ne peut effectuer la garde demandée le " + date + " de " + notificationDto.getTimeStart() + " à " + notificationDto.getTimeEnd() + " \n" +
                "Cordialement, \n" +
                "L'équipe Garde Hurry";

        emailService.sendSimpleMessage(email,subject, text);
    }


}
