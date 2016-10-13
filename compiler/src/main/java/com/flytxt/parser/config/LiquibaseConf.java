package com.flytxt.parser.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
public class LiquibaseConf {
	@Autowired
	Environment env;
	
	@Primary
	@Profile("test")
	@Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase sl = new SpringLiquibase();
        sl.setDataSource(dataSource);
        sl.setContexts("test");
        sl.setChangeLog("classpath:db/changelog/test.dbchangelog.xml");
        sl.setShouldRun(true);
        return sl;
    }
}
