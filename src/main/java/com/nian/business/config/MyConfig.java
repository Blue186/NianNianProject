package com.nian.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {
    @Bean
    public String test(){
        return "test";
    }

    @Bean
    public String test3(){
        return "test1";
    }
}
