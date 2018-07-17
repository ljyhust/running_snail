package com.ljyhust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Administrator on 2018/7/17.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ManageGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageGateWayApplication.class, args);
    }
}
