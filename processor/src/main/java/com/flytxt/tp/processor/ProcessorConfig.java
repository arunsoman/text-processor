package com.flytxt.tp.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.flytxt.tp.processor.FolderEventListener.Watch;
import com.flytxt.tp.processor.filefilter.FilterChainBuilder;
import com.flytxt.tp.processor.filefilter.FilterParameters;
import com.flytxt.tp.processor.filefilter.FlyFileFilter;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Profile("processor")
@Configuration
@EnableJpaRepositories
@EntityScan(basePackageClasses=Job.class)
@EnableConfigurationProperties
@ComponentScan
@Slf4j
public class ProcessorConfig {

	@Getter
	private List<Job> jobs;

	@Getter
	private List<com.flytxt.tp.processor.FolderEventListener.Watch> folderWatch;

	@Autowired
	private JobRepo repo;
	private final Logger appLog = LoggerFactory.getLogger("applicationLog");



	static class DbClassLoader extends ClassLoader {
		private DbClassLoader() {
			super(Thread.currentThread().getContextClassLoader());
		}

		@SuppressWarnings("unchecked")
		public Class<LineProcessor> getClass(final byte[] d, final String name) {
			return (Class<LineProcessor>) defineClass(name, d, 0, d.length);
		}
	}

	public LineProcessor getLp(final byte[] byteCode, final String name) throws InstantiationException, IllegalAccessException{
		final DbClassLoader loader = new DbClassLoader();
		return loader.getClass(byteCode, name).newInstance();
	}
	@PostConstruct
	public void init() throws Exception {
		final String hostName = getHostname();
		log.debug("who am i ? :" + hostName);
		if (hostName == null || hostName.length() == 0) {
			appLog.error("getHostName returned null, This reader will not function");
			throw new RuntimeException("getHostName returned null, This reader will not function");
		}

		jobs = repo.findByhostNameAndActiveTrueAndStatusTrue(hostName);
		folderWatch = new ArrayList<>(jobs.size());
		for (final Job aJob : jobs) {
			final String destination = "/tmp/" + aJob.getName() +  aJob.getInputPath();
			//String destination =aJob.getOutputPath();
			log.info("destination {}", destination);
			final File theDir = new File(destination);
			if (!theDir.exists()) {
				theDir.mkdirs();
			}
			final com.flytxt.tp.processor.FolderEventListener.Watch w = new Watch(aJob.getInputPath(), aJob.getRegex(),
					destination);
			folderWatch.add(w);
		}
	}

	private String getHostname() {
		Process p;
		final StringBuilder result = new StringBuilder();
		try {
			p = Runtime.getRuntime().exec("hostname");
			p.waitFor();

			final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			p.destroy();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return result.toString().trim();
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
