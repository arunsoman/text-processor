package com.flytxt.parser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name= "job_dk2")
public class Job {
	
	@Id
	@GeneratedValue
	private Long id;
	@Transient
	private Long idValue;
	private String name;
	private Long schedulingInterval;
	private String startAndEndTime;
	private Long isActive;
	private String startDate;
	private String endDate;
	@Column(name = "hostname")
	private String hostName;
	@Column(name = "inputpath")
	private String inputPath;
	private String regex;
	private String platform;
	private String lastRun;
	private String jobConstraint;
	@Column(name = "processedfileaction")
	private Long processedFileAction;
	@Lob
	private String dkSchema;
	@Lob
	private String dkPscript;

	@PostLoad
	public void postload() {
		this.idValue = this.id;
	}
	
	

}
