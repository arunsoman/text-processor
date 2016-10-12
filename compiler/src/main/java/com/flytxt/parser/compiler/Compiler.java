package com.flytxt.parser.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan({ "com.flytxt.parser", "com.flytxt.parser.domain" })
@EnableJpaRepositories({ "com.flytxt.parser", "com.flytxt.parser.domain" })
public class Compiler {

	public static void main(String args[]) {
		SpringApplication.run(Compiler.class, args);
	}
}
