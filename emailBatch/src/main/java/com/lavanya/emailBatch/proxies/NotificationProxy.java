package com.lavanya.emailBatch.proxies;

import com.lavanya.emailBatch.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * interface required to communicate with api module and make all the requests related to Notification object.
 * @author lavanya
 */
@FeignClient(name = "notificationApi", url = "localhost:9090")
public interface NotificationProxy {

    @GetMapping(value = "/notifications/profile/validation", consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    List<NotificationDto> getListOfUsersToWarnForProfileValidation();
}
