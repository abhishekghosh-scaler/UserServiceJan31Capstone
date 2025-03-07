package com.scaler.userservicejan31capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceJan31CapstoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceJan31CapstoneApplication.class, args);
    }

}
