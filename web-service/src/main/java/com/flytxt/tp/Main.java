package com.flytxt.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
/*
@EnableAutoConfiguration
@SpringBootConfiguration

@EnableJpaRepositories("com.flytxt.tp")
@EntityScan("com.flytxt.tp")
 */
@EnableJpaRepositories
public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
