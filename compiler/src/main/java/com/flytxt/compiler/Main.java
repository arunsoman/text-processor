package com.flytxt.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

import com.flytxt.parser.marker.MarkerDefaultConfig;
import com.flytxt.parser.processor.ProcessorDefaultConfig;
import com.flytxt.parser.store.StoreDefaultConfig;
import com.flytxt.parser.translator.TranslatorDefaultConfig;

@SpringBootApplication
@ContextConfiguration(classes = {
		MarkerDefaultConfig.class,
		StoreDefaultConfig.class,
		TranslatorDefaultConfig.class,
		ProcessorDefaultConfig.class
})

public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
