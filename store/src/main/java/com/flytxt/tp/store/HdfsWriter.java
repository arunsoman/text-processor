package com.flytxt.tp.store;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.data.hadoop.store.codec.DefaultCodecInfo;
import org.springframework.data.hadoop.store.output.OutputStreamWriter;
import org.springframework.data.hadoop.store.strategy.naming.RollingFileNamingStrategy;

public class HdfsWriter {
	//TODO fix this
	private UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");
	

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static OutputStreamWriter writer;
	
	public HdfsWriter(String location) {
		Path path = new Path(location);
		Configuration config = new Configuration();
		// Hadoop configurations go here
		config.addResource(new Path("/conf/hdfs-site.xml"));
		config.addResource(new Path("/conf/core-site.xml"));
		RollingFileNamingStrategy fileNamingStrategy = new RollingFileNamingStrategy().createInstance();
		fileNamingStrategy.init(path);

		writer = new OutputStreamWriter(config, path, 
				new DefaultCodecInfo("org.apache.hadoop.io.compress.BZip2Codec", true, "bzip"));
		writer.setFileNamingStrategy(fileNamingStrategy);
	}

	void writeToHdfs(byte[] data) throws IOException {
		try {
			ugi.doAs(new PrivilegedExceptionAction<Void>() {

				@Override
				public Void run() throws Exception {
					rwl.writeLock().lock();
					try {
						writer.write(data);
					} finally {
						writer.close();
						rwl.writeLock().lock();
					}

					return null;
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
