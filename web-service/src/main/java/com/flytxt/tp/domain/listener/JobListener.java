package com.flytxt.tp.domain.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.flytxt.tp.Job;
import com.flytxt.tp.dto.WorkflowDTO;

@Component
@RepositoryEventHandler(Job.class)
public class JobListener {

    private WorkflowDTO dto;

    public JobListener() {
        try {
            dto = new WorkflowDTO();
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
            } catch (Exception e) {
                job.setStatus(false);
            }
    }
}
