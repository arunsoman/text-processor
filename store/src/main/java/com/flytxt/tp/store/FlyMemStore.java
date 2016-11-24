package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import javax.annotation.concurrent.ThreadSafe;

import com.flytxt.tp.marker.Marker;

@ThreadSafe
public class FlyMemStore {
	private final MappedByteBuffer out;

	// read and write indexes are stored in this buffer in ((int)readIndex,
	// (int)writeIndex) format
	private final MappedByteBuffer meta;

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
		if (instance == null) {
			instance = new FlyMemStore();
		}
		return instance;
	}

	public void write(final Marker... markers) {
		// keep on pushing data to out, when there is no more space to write
		// throw Arrayoutofbound
		final int dataLenght = Arrays.stream(markers).mapToInt(mapper -> mapper.length).sum();

		try {
			semaphore.acquire();
			if (out.remaining() < dataLenght + markers.length + newLine.length - 1) {
				throw new ArrayIndexOutOfBoundsException();
			}
			boolean needDelimiter = false;
			for (final Marker marker : markers) {
				if (needDelimiter) {
					out.put(comma);
				}
				out.put(marker.getData(), marker.index, marker.length);
				needDelimiter = true;
			}
			out.put(newLine);
			meta.putInt(4, out.position());
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}

	}

	public byte[] read() {
		byte[] data = null;
		try {
			semaphore.acquire();
			final int lastWriteIndex = meta.getInt(4);
			final int lastReadIndex = meta.getInt(1);
			out.position(lastReadIndex);
			final int datalenght = lastWriteIndex - lastReadIndex;
			if (datalenght > 0) {
				data = new byte[datalenght];
				out.get(data, lastReadIndex, lastWriteIndex - lastReadIndex);
				out.position(lastReadIndex);
			}
			meta.putInt(1, lastReadIndex);
			meta.putInt(4, lastReadIndex);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
		return data;
	}

	public void close() throws IOException {
		try {
			semaphore.acquire();
			outFile.close();
			metaFile.close();
			instance = null;
		}catch (final InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}

	}
}
