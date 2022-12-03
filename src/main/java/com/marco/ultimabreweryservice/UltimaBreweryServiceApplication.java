package com.marco.ultimabreweryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
public class UltimaBreweryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UltimaBreweryServiceApplication.class, args);
    }

}
