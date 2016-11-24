package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

import javax.annotation.concurrent.ThreadSafe;

import com.flytxt.tp.marker.Marker;

@ThreadSafe
public class FlyMemStore {
	private MappedByteBuffer out;

	// read and write indexes are stored in this buffer in ((int)readIndex,
	// (int)writeIndex) format
	private MappedByteBuffer meta;

	private static final byte[] newLine = "\n".getBytes();
	private static final byte[] comma = ",".getBytes();
	private final Semaphore semaphore = new Semaphore(1);
	private final int bufSize = 1 * 1024 * 1024;
	private final RandomAccessFile outFile = new RandomAccessFile("hadoopData.dat", "rw");
	private final RandomAccessFile metaFile = new RandomAccessFile("hadoopMeta.dat", "rw");
	private static FlyMemStore instance;

	private FlyMemStore() throws IOException {
		out = outFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, bufSize);
		meta = metaFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 8);
	}

	public static synchronized FlyMemStore getSingletonInstance() throws FileNotFoundException, IOException {
		if (instance == null)
			instance = new FlyMemStore();
		return instance;
	}

	public void write(Marker... markers) {
		// keep on pushing data to out, when there is no more space to write
		// throw Arrayoutofbound
		int dataLenght = Arrays.stream(markers).mapToInt(mapper -> mapper.length).sum();

		try {
			semaphore.acquire();
			if (out.remaining() < dataLenght + markers.length + newLine.length - 1)
				throw new ArrayIndexOutOfBoundsException();
			boolean needDelimiter = false;
			for (int i = 0; i < markers.length; i++) {
				if (needDelimiter) {
					out.put(comma);
				}
				out.put(markers[i].getData(), markers[i].index, markers[i].length);
				needDelimiter = true;
			}
			out.put(newLine);
			meta.putInt(4, out.position());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}

	}

	public byte[] read() {
		byte[] data = null;
		try {
			semaphore.acquire();
			int lastWriteIndex = meta.getInt(4);
			int lastReadIndex = meta.getInt(1);
			out.position(lastReadIndex);
			int datalenght = lastWriteIndex - lastReadIndex;
			if(datalenght>0){
			data = new byte[datalenght];
			out.get(data, lastReadIndex, lastWriteIndex - lastReadIndex);
			out.position(lastReadIndex);
			}
			meta.putInt(1, lastReadIndex);
			meta.putInt(4, lastReadIndex);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
		return data;
	}

	public void close() throws IOException {
		if (outFile != null)
			outFile.close();
		if (metaFile != null)
			metaFile.close();
		instance = null;
	}
}
