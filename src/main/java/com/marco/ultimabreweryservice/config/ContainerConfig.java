package com.marco.ultimabreweryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.OracleContainer;

@Configuration
@Profile(value={"oracle"})
public class ContainerConfig {

    @Bean
    OracleContainer getContainer(){
        OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe:21-slim-faststart")
                .withDatabaseName("testDB")
                .withUsername("testUser")
                .withPassword("testPassword");

        return  oracle;
    }

}
