package com.flytxt.compiler.domain;

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
@Table(name = "job_dk2")
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private Long idValue;

    private String name;

    @Column(name = "active")
    private boolean active;

    @Column(name = "hostname")
    private String hostName;

    @Column(name = "inputpath")
    private String inputPath;

    @Column(name = "outputpath")
    private String outputPath;

    private String regex;

    @Lob
    @Column(name = "dk_pscript")
    private String dkPscript;

    @PostLoad
    public void postload() {
        this.idValue = this.id;
    }

}
