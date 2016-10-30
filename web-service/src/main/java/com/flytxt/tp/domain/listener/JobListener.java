package com.flytxt.tp.domain.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.flytxt.tp.domain.Job;
import com.flytxt.tp.dto.WorkflowDTO;

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
	@PrePersist
	@PreUpdate
	public void executeMeBeforeSave(final Job job) {
		if(job.isActive()){
			try {
				byte[] compileToBytes = dto.serialize( dto.convert(job.getSchema()));
				job.setByteCode(compileToBytes);
				job.setStatus(true);
			} catch (Exception e) {
				job.setStatus(false);
			}
		}
	}
}
