package com.flytxt.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaRepositories("com.flytxt.tp.repo")
@EntityScan("com.flytxt.tp.domain")
@ComponentScan(basePackages={"com.flytxt.tp"})

public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
