package com.ljyhust.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
public class SnailDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnailDemoApplication.class, args);
    }
}