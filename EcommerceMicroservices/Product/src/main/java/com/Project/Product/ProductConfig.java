package com.Project.Product;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {
    @Bean
    public ProductService productBean() {
        return new ProductService();
    }

    @Bean
    ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}
