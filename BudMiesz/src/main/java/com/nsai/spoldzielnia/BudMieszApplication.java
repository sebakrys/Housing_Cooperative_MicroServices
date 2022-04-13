package com.nsai.spoldzielnia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BudMieszApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudMieszApplication.class, args);
    }

}
