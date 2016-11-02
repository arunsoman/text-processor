package com.flytxt.tp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import com.flytxt.tp.domain.listener.JobListener;

@Data
@Entity
@Table(name = "job_dk2")
@EntityListeners(JobListener.class)
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private Long idValue;

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
    @Column(name = "dkschema")
    private String dkSchema;

    @Lob
    @Column(name = "byte_code")
    private byte[] byteCode;

    @Lob
    @Column(name = "schema", columnDefinition="TEXT")
    private String schema;

    @PostLoad
    public void postload() {
        this.idValue = this.id;
    }
}
