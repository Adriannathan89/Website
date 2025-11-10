package com.Project.User;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserConfig {
    @Bean
    public UserService userBean() {
        return new UserService();
    }

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }
}
