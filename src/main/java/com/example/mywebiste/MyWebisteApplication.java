package com.example.mywebiste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.mywebiste.Model")
public class MyWebisteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWebisteApplication.class, args);
    }

}
