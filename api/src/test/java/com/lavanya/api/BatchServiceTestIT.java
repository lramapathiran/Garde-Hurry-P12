package com.lavanya.api;

import com.lavanya.api.model.Notification;
import com.lavanya.api.service.BatchEmailService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class BatchServiceTestIT {

    @Autowired
    BatchEmailService batchEmailService;

    @Test
    public void getListOfUserWithProfileNotValidatedYetTest() {
        List<Notification> list = batchEmailService.getListOfUserWithProfileNotValidatedYet();

        Assert.assertFalse(list.isEmpty());
    }
}
