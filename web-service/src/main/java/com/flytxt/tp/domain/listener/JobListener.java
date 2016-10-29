package com.flytxt.tp.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.flytxt.compiler.RealtimeCompiler;
import com.flytxt.tp.domain.Job;

public class JobListener {
	@PrePersist
	@PreUpdate
	public void executeMeBeforeSave(final Job job) {
		if(job.isActive()){
			try {
				byte[] compileToBytes = RealtimeCompiler.compileToBytes(job.getName(), job.getSchema());
				job.setByteCode(compileToBytes);
				job.setStatus(true);
			} catch (Exception e) {
				job.setStatus(false);
			}
		}
	}
}
