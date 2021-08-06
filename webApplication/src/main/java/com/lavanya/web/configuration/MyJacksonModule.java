package com.lavanya.web.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

public class MyJacksonModule extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Page.class, PageMixIn.class);
    }
}

@Configuration
class MyJacksonConfiguration {

    @Bean
    public Module myJacksonModule() {
        return new MyJacksonModule();
    }
}
