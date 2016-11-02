package com.flytxt.parser.processor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.flytxt.parser.marker.LineProcessor;
import com.flytxt.parser.processor.FolderEventListener.Watch;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "compiler")
@Data
public class ProxyScripts {

	public String getScript;

	public String getJar;

	public String remoteHost;

	public String hostName;

	private List<LineProcessor> lps;

	private List<com.flytxt.parser.processor.FolderEventListener.Watch> folderWatch;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JobRepo repo;

	public List<LineProcessor> getLPInstance() {
		return lps;
	}

	static class DbClassLoader extends ClassLoader {
		Class<LineProcessor> getClass(byte[] d, String name) {
			return (Class<LineProcessor>) defineClass(name, d, 0, d.length);
		}
	}

	@PostConstruct
    public void init() throws Exception {
    	DbClassLoader loader = new DbClassLoader();
    	String hostName = getHostname();
    	logger.debug("who am i ? :"+ hostName);
    	List<Job> jobs = repo.findByhostNameAndActiveTrueAndStatusTrue(hostName);
    	folderWatch = new ArrayList<>(jobs.size());
    	for(Job aJob: jobs){
    		lps.add((LineProcessor)loader.getClass(aJob.getByteCode(), aJob.getName()).newInstance());
    		com.flytxt.parser.processor.FolderEventListener.Watch w = new Watch(aJob.getInputPath(), aJob.getRegex(), aJob.getOutputPath());
    		folderWatch.add(w);
    	}
    }

	private String getHostname() {
		try (BufferedInputStream in = new BufferedInputStream(Runtime.getRuntime().exec("hostname").getInputStream())) {
			byte[] b = new byte[256];
			in.read(b, 0, b.length); 
			return new String(b);
		} catch (IOException e) {
			String message = "Error reading hostname";
			throw new RuntimeException(message, e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<com.flytxt.parser.processor.FolderEventListener.Watch> getFolderWatch() {
		return folderWatch;
	}
}