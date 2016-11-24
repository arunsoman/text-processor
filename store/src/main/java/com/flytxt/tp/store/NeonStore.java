package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.scheduling.annotation.Scheduled;

import com.flytxt.tp.marker.Marker;


public class NeonStore implements Store {

	

	private static FlyMemStore fms;
	
	private HdfsWriter hdfsWriter;
	
	public void init() throws FileNotFoundException, IOException, InterruptedException {

		fms=FlyMemStore.getSingletonInstance();


	}

	@Override
	public void save(byte[] data, String fileName, Marker... markers) throws IOException {
		
	}


	public void set(String fileName) {
		
	}

	@Override
	@PreDestroy
	public String done() throws IOException {
		
		return null;
	}

	// provided lower priority in hdfs write
	@Scheduled(fixedDelay = 500)
	public void timer() {
		hdfswrite();
	}

	private void hdfswrite() {
		/*
		boolean tryLock = rwl.writeLock().tryLock();
		if (tryLock) {
			try {
				writeToHdfs(fms.read());

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (tryLock)
					rwl.writeLock().unlock();
			}
		}
		*/
	}

	
	@Override
	public void preDestroy() {
		// TODO Auto-generated method stub
		
	}
}