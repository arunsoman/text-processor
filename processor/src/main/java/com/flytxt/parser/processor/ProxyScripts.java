package com.flytxt.parser.processor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JobRepo repo;

    public List<LineProcessor> getLPInstance() {
        return lps;
    }
    static class DbClassLoader extends ClassLoader{
		Class <?>getClass(byte[] d){
			return defineClass(d, 0, d.length);
		}
	}
    @PostConstruct
    public void init() throws Exception {
    	DbClassLoader loader = new DbClassLoader();
    	List<Job> jobs = repo.findByhostNameAndActiveTrueAndStatusTrue(getHostname());
    	for(Job aJob: jobs){
    		lps.add((LineProcessor)loader.getClass(aJob.getByteCode()).newInstance());
    	}
    }
    private String getHostname()
    {
      try (BufferedInputStream in = new BufferedInputStream(Runtime.getRuntime().exec("hostname").getInputStream()))
      {
        byte[] b = new byte[256];
        in.read(b, 0, b.length); //guaranteed to read all data before returning
        return new String(b);
      }
      catch (IOException e)
      {
        String message = "Error reading hostname";
        throw new RuntimeException(message, e);
      }
    }
    class FolderWatch {
    }

    @SuppressWarnings("unchecked")
    public List<com.flytxt.parser.processor.FolderEventListener.Watch> getFolderWatch() {
        
        return null;
    }
}