package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Semaphore;

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


	private static UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");
	
	private static FlyMemStore store;

	@SuppressWarnings("resource")
	public static void init() throws FileNotFoundException, IOException, InterruptedException {
		store = new FlyMemStore();	
	}

	@Override
	public void save(byte[] data, String fileName, Marker... markers) throws IOException {
		store.write(markers);
	}

	private static void writeToHdfs() throws IOException {
		
		try {
			ugi.doAs(new PrivilegedExceptionAction<Void>() {

				@Override
				public Void run() throws Exception {
					writer.write(store.read());
					writer.close();
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
			writeToHdfs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}