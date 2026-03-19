package com.aetherhold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AetherholdApplication {

    public static void main(String[] args) {
        SpringApplication.run(AetherholdApplication.class, args);
    }
}

