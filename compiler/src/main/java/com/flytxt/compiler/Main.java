package com.flytxt.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaRepositories("com.flytxt.compiler.repo")
@EntityScan("com.flytxt.compiler.domain")
@ComponentScan(basePackages={"com.flytxt.compiler",
		"com.flytxt.parser.marker",
		"com.flytxt.parser.processor",
		"com.flytxt.parser.store",
		"com.flytxt.parser.translator"})

public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
