package com.example.springbootfilepermown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootFilePermOwnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFilePermOwnApplication.class, args);
    }

}
