package com.flytxt.tp.domain.listener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flytxt.tp.Job;
import com.flytxt.tp.dto.WorkflowDTO;

@Component
@RepositoryEventHandler(Job.class)
public class JobListener {

    private WorkflowDTO dto;
    private Queue <String>notifications;
    
    public JobListener() {
        try {
            dto = new WorkflowDTO();
            notifications = new LinkedList<String>(); 
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void executeMeBeforeSave(final Job job) {
        if (job.isActive())
            try {
                byte[] compileToBytes = dto.serialize(dto.convert(job));
                job.setByteCode(compileToBytes);
                job.setStatus(true);
                notifications.add(job.getHostName());
            } catch (Exception e) {
                job.setStatus(false);
            }
    }
    
    @Scheduled(fixedDelay=5*60*1000)
    public void pushNotifications(){
    	String host;
    	while((host = notifications.poll()) != null){
    		RestTemplate restTemplate = new RestTemplate();
    		String result = restTemplate.getForObject("http://"+host+"9001/reload", String.class);
    	}
    }
}
