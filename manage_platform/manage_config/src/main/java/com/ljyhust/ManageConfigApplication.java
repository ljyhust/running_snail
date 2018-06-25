package com.ljyhust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ManageConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageConfigApplication.class, args);
	}
}
