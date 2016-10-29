package com.flytxt.tp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@ComponentScan
public class LiquibaseConf {

    @Profile("dev")
    @Bean(name = "liquibase")
    public SpringLiquibase liquibaseForDev(DataSource dataSource) {
        SpringLiquibase sl = new SpringLiquibase();
        sl.setDataSource(dataSource);
        sl.setContexts("dev");
        sl.setChangeLog("classpath:db/changelog/dev.dbchangelog.xml");
        sl.setShouldRun(true);
        return sl;
    }

    @Primary
    @Profile("test")
    @Bean(name = "liquibase")
    public SpringLiquibase liquibaseForTest(DataSource dataSource) {
        SpringLiquibase sl = new SpringLiquibase();
        sl.setDataSource(dataSource);
        sl.setContexts("test");
        sl.setChangeLog("classpath:db/changelog/test.dbchangelog.xml");
        sl.setShouldRun(true);
        return sl;
    }

}
