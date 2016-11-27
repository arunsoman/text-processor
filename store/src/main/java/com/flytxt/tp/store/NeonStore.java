package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;

import com.flytxt.tp.marker.Marker;


public class NeonStore implements Store {

	private static MemStrorePool fms;

	private FlyMemStore memStore ;

	private HdfsWriter writer ;

	public void init(final String folderName) throws FileNotFoundException, IOException, InterruptedException {

		memStore=MemStrorePool.getSingletonInstance().getMemStore(folderName);
		//memStore = fms.getMemStore(folderName);
		writer	= new HdfsWriter(folderName);
	}

	@Override
	public void save(final byte[] data, final String fileName, final Marker... markers) throws IOException {
		memStore.write(markers);
	}

	@Override
	public void set(final String fileName) {

	}

	// provided lower priority in hdfs write
	@Scheduled(fixedDelay = 500)
	public void timer() throws IOException {
		hdfswrite();
	}

	private void hdfswrite() throws IOException {
		final byte[] read = memStore.read();
		if(read !=null) {
			writer.writeToHdfs(read);
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
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String done() throws IOException {
		return null;
	}

}