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

	private static MappedByteBuffer out;

	// read and write indexes are stored in this buffer in ((int)readIndex,
	// (int)writeIndex) format
	private static MappedByteBuffer meta;

	private static Semaphore semaphore = new Semaphore(1);

	private static OutputStreamWriter writer;

	private static final int bufSize = 1 * 1024 * 1024;

	//private static byte[] data = new byte[bufSize];
	private static byte[] data;

	private static UserGroupInformation ugi = UserGroupInformation.createRemoteUser("root");

	@SuppressWarnings("resource")
	public static void init() throws FileNotFoundException, IOException, InterruptedException {
		if (out != null)
			return;
		semaphore.acquire();
		if (out != null) {
			semaphore.release();
			return;
		}
		out = new RandomAccessFile("hadoopData.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, bufSize);
		meta = new RandomAccessFile("hadoopMeta.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 8);

		Path path = new Path("/tmp/output");
		Configuration config = new Configuration();
		// Hadoop configurations go here
		config.addResource(new Path("/tmp/hdfs-site.xml"));
		config.addResource(new Path("/tmp/core-site.xml"));
		RollingFileNamingStrategy fileNamingStrategy = new RollingFileNamingStrategy().createInstance();

		writer = new OutputStreamWriter(config, path, null);
		writer.setFileNamingStrategy(fileNamingStrategy);
		semaphore.release();
	}

	@Override
	public void save(byte[] data, String fileName, Marker... markers) throws IOException {
		try {
			// if (out.position() + data.length > bufSize) // checking for
			// bufSize
			
			log.info(""+out.remaining()+" "+data.length);
			if (out.remaining() <= data.length+1)
				writeToHdfs();
			semaphore.acquire();
			// boundary
			boolean needDelimiter = false;
			for (Marker marker : markers) {
				if (needDelimiter)
					out.putChar(',');
				out.put(marker.getData(), marker.index, marker.length);
				needDelimiter = true;

			}
			if (needDelimiter)
				out.put("\n".getBytes());
			meta.putInt(4, out.position()); // update writeIndex in meta
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void writeToHdfs() throws IOException {
		int lastReadIndex = meta.getInt(0), lastWriteIndex = meta.getInt(4);
		if (lastReadIndex == lastWriteIndex)
			return;
		try {
			ugi.doAs(new PrivilegedExceptionAction<Void>() {

				@Override
				public Void run() throws Exception {
					semaphore.acquire();
					
					out.position(lastReadIndex);
					data= new byte[lastWriteIndex - lastReadIndex];
					out.get(data, lastReadIndex, lastWriteIndex - lastReadIndex);
					writer.write(data);
					writer.close();
					out.position(lastReadIndex);
					meta.putInt(0, lastReadIndex); // update readIndex in meta
					semaphore.release();
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