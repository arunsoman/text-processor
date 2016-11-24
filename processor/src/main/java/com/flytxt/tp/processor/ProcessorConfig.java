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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import com.flytxt.tp.processor.FolderEventListener.Watch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Profile("processor")
@Configuration
@EnableConfigurationProperties
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
		public Class<LineProcessor> getClass(byte[] d, String name) {
			return (Class<LineProcessor>) defineClass(name, d, 0, d.length);
		}
	}

	public LineProcessor getLp(byte[] byteCode, String name) throws InstantiationException, IllegalAccessException{
		DbClassLoader loader = new DbClassLoader();
		return loader.getClass(byteCode, name).newInstance();
	}
	@PostConstruct
	public void init() throws Exception {
		String hostName = getHostname();
		log.debug("who am i ? :" + hostName);
		if (hostName == null || hostName.length() == 0) {
			appLog.error("getHostName returned null, This reader will not function");
			throw new RuntimeException("getHostName returned null, This reader will not function");
		}

		jobs = repo.findByhostNameAndActiveTrueAndStatusTrue(hostName);
		folderWatch = new ArrayList<>(jobs.size());
		for (Job aJob : jobs) {
			String destination = "/tmp/" + aJob.getName() +  aJob.getInputPath();
			//String destination =aJob.getOutputPath();
			log.info("destination {}", destination);
			File theDir = new File(destination);
			if (!theDir.exists())
				theDir.mkdirs();
			com.flytxt.tp.processor.FolderEventListener.Watch w = new Watch(aJob.getInputPath(), aJob.getRegex(),
					destination);
			folderWatch.add(w);
		}
	}

	private String getHostname() {
		Process p;
		StringBuilder result = new StringBuilder();
		try {
			p = Runtime.getRuntime().exec("hostname");
			p.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			p.destroy();
		} catch (Exception e) {
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
	public FlyReader flyReader() {
		return new FlyReader();
	}

	@Bean
	public Controller controller() {
		return new Controller();

	}
}
