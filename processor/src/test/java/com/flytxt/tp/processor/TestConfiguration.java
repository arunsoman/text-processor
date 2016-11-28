package com.flytxt.tp.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
	@Autowired
	private ApplicationContext ctx;

	@Bean
	Processor getProcessor() throws Exception {
		Processor processor = new Processor();
		return processor;
	}
	
	@Bean
	public ProcessorConfig getPConfig (){
		return new ProcessorConfig();
	}

	@Bean
	Controller getController() throws Exception {
		Controller controller = new Controller();
		return controller;
	}


}
