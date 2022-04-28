package com.nsai.spoldzielnia.mieszkancyzarzadcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MieszkancyZarzadcyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MieszkancyZarzadcyApplication.class, args);
    }

}
