package com.flytxt.tp.processor;

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

    @Column(name = "compile_status", columnDefinition="tinyint(4) default 0")
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
    @Column(name = "blockly_conf", columnDefinition = "TEXT")
    private String schema;

    @PostLoad
    public void postload() {
        this.idValue = this.id;
    }
    
    public LineProcessor getLp() throws InstantiationException, IllegalAccessException{
		DbClassLoader loader = new DbClassLoader();
		return loader.getClass(byteCode, name).newInstance();
	}
    
    private static class DbClassLoader extends ClassLoader {
		private DbClassLoader() {
			super(Thread.currentThread().getContextClassLoader());
		}

		@SuppressWarnings("unchecked")
		public Class<LineProcessor> getClass(byte[] d, String name) {
			return (Class<LineProcessor>) defineClass(name, d, 0, d.length);
		}
	}

}
