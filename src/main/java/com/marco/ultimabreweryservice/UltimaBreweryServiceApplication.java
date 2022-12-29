package com.marco.ultimabreweryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@EnableEurekaClient
@SpringBootApplication
public class UltimaBreweryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UltimaBreweryServiceApplication.class, args);
    }

}
