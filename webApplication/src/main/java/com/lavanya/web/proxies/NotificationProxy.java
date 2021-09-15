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
}
