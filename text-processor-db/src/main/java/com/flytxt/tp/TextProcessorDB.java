package com.flytxt.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public interface TextProcessorDB {
	
	public static void main(final String[] args) {
        SpringApplication.run(TextProcessorDB.class, args);
    }

}
