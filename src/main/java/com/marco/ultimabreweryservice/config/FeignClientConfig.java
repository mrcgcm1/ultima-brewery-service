package com.marco.ultimabreweryservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

@Configuration
public class FeignClientConfig {

    @Bean
    public BasicAuthenticationInterceptor interceptor(@Value("${com.marco.beer.inventory-user}") String user,
                                                      @Value("${com.marco.beer.inventory-password}") String password){
        return new BasicAuthenticationInterceptor(user,password);
    }
}
