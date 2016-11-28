package com.flytxt.tp.processor;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
	@Autowired
	private ApplicationContext ctx;
	
	@Bean
	Controller getController() throws Exception {
		Controller controller = new Controller();
		return controller;
	}

	@Bean
	Processor getProcessor() throws Exception {
		Processor p = new Processor();
		//p.setCtx(ctx);
		int size = 5;
		List<FlyReader> flyReaders = new ArrayList<FlyReader>(size);
		for (int i = 0; i < size; i++) {
			FlyReader flyReader = Mockito.mock(FlyReader.class);
			flyReaders.add(flyReader);
		}
		p.fileReaders = flyReaders;
		p.startFileReaders();
		return p;
	}

	@Bean
	ProcessorConfig getProcessorConfig(){
		return new ProcessorConfig();
	}
}
