package com.flytxt.tp.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

@Profile("processor")
@Configuration
@EnableConfigurationProperties
public class ProcessorConfig {

	@Bean
	public ProxyScripts proxyScripts() {
		return new ProxyScripts();
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
	public FlyReader flyReader() {
		return new FlyReader();
	}

	@Bean
	public Controller controller() {
		return new Controller();

	}
}
