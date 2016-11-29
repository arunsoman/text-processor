package com.flytxt.tp.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

//@Configuration
public class TestConfiguration {
	@Bean
	Processor getProcessor() throws Exception {
		final Processor processor = new Processor();
		return processor;
	}

	@Bean
	public ProcessorConfig getPConfig (){
		return new ProcessorConfig();
	}

	@Bean
	Controller getController() throws Exception {
		final Controller controller = new Controller();
		return controller;
	}


}
