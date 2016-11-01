package com.flytxt.parser.processor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "job_dk2")
public class Job {

	private String name;
	
    @Column(name = "active")
    private boolean active;

    @Column(name = "status")
    private boolean status;

    @Column(name = "hostname")
    private String hostName;

    @Column(name = "inputpath")
    private String inputPath;

    @Column(name = "outputpath")
    private String outputPath;

    private String regex;

    @Lob
    @Column(name = "byte_code")
    private byte[] byteCode;
}
