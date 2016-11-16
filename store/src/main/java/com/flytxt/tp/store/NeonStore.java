package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.mortbay.log.Log;
import org.springframework.data.hadoop.store.output.OutputStreamWriter;
import org.springframework.data.hadoop.store.strategy.naming.RollingFileNamingStrategy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.flytxt.tp.marker.Marker;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NeonStore implements Store {

	private static OutputStreamWriter writer;

	private static FlyMemStore fms ;
	private static UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	@SuppressWarnings("resource")
	public static  void init() throws FileNotFoundException, IOException, InterruptedException {
		
		synchronized(NeonStore.class){
		if (fms == null)
			fms = new FlyMemStore();
		}

		Path path = new Path("/tmp/output");
		Configuration config = new Configuration();
		// Hadoop configurations go here
		config.addResource(new Path("/tmp/hdfs-site.xml"));
		config.addResource(new Path("/tmp/core-site.xml"));
		RollingFileNamingStrategy fileNamingStrategy = new RollingFileNamingStrategy().createInstance();

		writer = new OutputStreamWriter(config, path, null);
		writer.setFileNamingStrategy(fileNamingStrategy);
		
	}

	@Override
	public void save(byte[] data, String fileName, Marker... markers) throws IOException {
		try {
			fms.write(markers);
		} catch (ArrayIndexOutOfBoundsException e) {
			rwl.writeLock().lock();
			writeToHdfs(fms.read());
			rwl.writeLock().unlock();
			save( data,  fileName,  markers) ;
		}
	}

	private static void writeToHdfs(byte[] data) throws IOException {
		try {
			ugi.doAs(new PrivilegedExceptionAction<Void>() {

				@Override
				public Void run() throws Exception {
					rwl.writeLock().lock();
					writer.write(data);
					writer.close();
					rwl.writeLock().lock();
					return null;
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void set(String fileName) {
		// TODO Auto-generated method stub
	}

	@Override
	public String done() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Scheduled(fixedDelay = 500)
	public void timer() {
		try {
			rwl.writeLock().lock();
			writeToHdfs(fms.read());
			rwl.writeLock().unlock();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}