package com.lavanya.web.proxies;

import com.lavanya.web.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NotificationEmailBatch", url = "localhost:6060")
public interface NotificationProxy {

    @PostMapping(value = "/send/notification/newProfile", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void alertEmailForNewUserToValidateProfile(@RequestBody NotificationDto notificationDto);

    @PostMapping(value="/send/friendInvitation", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void sendFriendInvitation(@RequestBody NotificationDto notificationDto);

    @PostMapping(value="/send/childcareNotification", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void sendChildcareNotificationToUserInCharge(@RequestBody NotificationDto notificationDto);

    @PostMapping(value="/send/childcareNotification/acceptance", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void sendChildcareAcceptanceNotificationToUserInNeed(@RequestBody NotificationDto notificationDto);

    @PostMapping(value="/send/childcareNotification/refusal", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    void sendChildcareRefusalNotificationToUserInNeed(@RequestBody NotificationDto notificationDto);
}
