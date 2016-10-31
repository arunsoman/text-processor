package com.flytxt.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
/*
@EnableAutoConfiguration
@SpringBootConfiguration

@EnableJpaRepositories("com.flytxt.tp")
@EntityScan("com.flytxt.tp")
 */
@ComponentScan(basePackageClasses={Job.class, JobRepo.class})

public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
