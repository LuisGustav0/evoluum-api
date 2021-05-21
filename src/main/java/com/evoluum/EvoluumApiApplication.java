package com.evoluum;

import com.evoluum.core.config.property.AppProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCaching
@EnableCircuitBreaker
@SpringBootApplication
@EnableConfigurationProperties(AppProperty.class)
public class EvoluumApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvoluumApiApplication.class, args);
    }
}
