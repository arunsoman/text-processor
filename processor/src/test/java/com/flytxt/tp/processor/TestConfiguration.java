package com.flytxt.tp.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.flytxt.tp.processor.filefilter.FilterChainBuilder;
import com.flytxt.tp.processor.filefilter.FilterParameters;
import com.flytxt.tp.processor.filefilter.FlyFileFilter;

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
	
	@Bean
	@Lazy
	public Processor processor() {
		return new Processor();
	}

	@Bean
	public FolderEventListener folderEventListener() {
		return new FolderEventListener();
	}

	@Bean
	@Lazy
	@Scope("prototype")
	public FlyReader flyReader() {
		return new FlyReader();
	}

	@Bean
	public Controller controller() {
		return new Controller();

	}
	@Bean
	public FilterChainBuilder filterChainBuilder() {
		return new FilterChainBuilder();
	}
	
	@Bean
	public FilterParameters filterParameters() {
		return new FilterParameters();
	}
	
	@Bean
	@Lazy
	public FlyFileFilter flyFileFilter() {
		return new FlyFileFilter();
	}


}
