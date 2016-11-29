package com.flytxt.tp;

import java.io.File;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import liquibase.integration.spring.SpringLiquibase;


@SpringBootApplication
public class TextProcessorDB {

	@Value("${liquibaseRollback.file:#{null}}")
	private File rollbackFile;

	public static void main(final String[] args) {
		SpringApplication.run(TextProcessorDB.class, args);
	}

	@Bean
	public BeanPostProcessor postProcessor(){

		return new BeanPostProcessor(){

			@Override
			public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
				if (bean instanceof SpringLiquibase) {
					((SpringLiquibase) bean).setRollbackFile(rollbackFile);
				}
				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
				return bean;
			}

		};

	}

}
