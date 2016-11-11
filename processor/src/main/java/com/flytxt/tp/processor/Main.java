package com.flytxt.tp.processor;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {

    public static void main(final String[] args) throws Exception {
       
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
    	 SpringApplication build = builder.profiles("processor").bannerMode(Mode.OFF).build();
    	 builder.application().run( args);
    }
}
