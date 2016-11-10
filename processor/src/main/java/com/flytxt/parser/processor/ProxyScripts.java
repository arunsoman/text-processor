package com.flytxt.parser.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

    private List<LineProcessor> lps = new ArrayList<>();

    private List<com.flytxt.parser.processor.FolderEventListener.Watch> folderWatch;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JobRepo repo;

    public List<LineProcessor> getLPInstance() {
        return lps;
    }

    static class DbClassLoader extends ClassLoader {

        @SuppressWarnings("unchecked")
        Class<LineProcessor> getClass(byte[] d, String name) {
            return (Class<LineProcessor>) defineClass(name, d, 0, d.length);
        }
    }

    @PostConstruct
    public void init() throws Exception {
        DbClassLoader loader = new DbClassLoader();
        String hostName = getHostname();
        logger.debug("who am i ? :" + hostName);
        List<Job> jobs = repo.findByhostNameAndActiveTrueAndStatusTrue(hostName);

        folderWatch = new ArrayList<>(jobs.size());
        for (Job aJob : jobs) {
            lps.add(loader.getClass(aJob.getByteCode(), aJob.getName()).newInstance());
            String destination = "/tmp/" + aJob.getName() + "/" + aJob.getInputPath();
            com.flytxt.parser.processor.FolderEventListener.Watch w = new Watch(aJob.getInputPath(), aJob.getRegex(), destination);
            folderWatch.add(w);
        }
    }

    private String getHostname() {
    	 Process p;
    	 StringBuilder result = new StringBuilder();
         try {
               p = Runtime.getRuntime().exec("hostname");
               p.waitFor();
 
               BufferedReader reader = new BufferedReader(
                                 new InputStreamReader(
                                 p.getInputStream()));
 
 
               String line = "";
               while ((line = reader.readLine()) != null) {
                     result.append(line + "\n");
               }
                p.destroy();
         } catch (Exception e) {
               e.printStackTrace();
         }
         return result.toString();
     }

    public List<com.flytxt.parser.processor.FolderEventListener.Watch> getFolderWatch() {
        return folderWatch;
    }
}
