package com.lavanya.emailBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmailBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailBatchApplication.class, args);
    }

}
