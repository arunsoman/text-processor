package com.flytxt.compiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.flytxt.compiler.config.RepoConf;
import com.flytxt.compiler.repo.JobRepo;
import com.flytxt.parser.marker.MarkerDefaultConfig;
import com.flytxt.parser.processor.ProcessorDefaultConfig;
import com.flytxt.parser.store.StoreDefaultConfig;
import com.flytxt.parser.translator.TranslatorDefaultConfig;

@SpringBootApplication

@ContextConfiguration(classes = {
		MarkerDefaultConfig.class,
		StoreDefaultConfig.class,
		TranslatorDefaultConfig.class,
		ProcessorDefaultConfig.class,
		RepoConf.class,
		JobRepo.class
})

@SpringBootConfiguration
@EnableJpaRepositories("com.flytxt.compiler.repo")
@EntityScan("com.flytxt.compiler.domain")
@ComponentScan("com.flytxt.compiler")

public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
