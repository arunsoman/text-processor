package com.flytxt.tp.store;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import javax.annotation.concurrent.ThreadSafe;

import com.flytxt.tp.marker.Marker;

import lombok.extern.slf4j.Slf4j;

@ThreadSafe
@Slf4j
final public class FlyMemStore {

	private MappedByteBuffer out;
	private static MappedByteBuffer meta;

	private static final byte[] newLine = "\n".getBytes();
	private static final byte[] comma = ",".getBytes();
	private static RandomAccessFile outFile;
	private static RandomAccessFile metaFile;
	private static final int totalbufSize = 1024 * 1024 * 1024;
	private int writeIndexPostion = 4;
	private int readIndexPostion = 0;
	private boolean isRegistered;
	static {
		try {
			outFile = new RandomAccessFile("memStoreData.dat", "rw");
			final File file = new File("memStoreMeta.dat");
			if (file.exists()) {
				metaFile = new RandomAccessFile(file, "rw");
				meta = metaFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 2 * 1024);
				int entry = meta.getInt();
				if (entry >= 0) {
					log.debug(" meta size is " + meta.getInt());
					int bufferentry = 0;
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					while (entry > 0) {
						log.debug(" meta postion " + meta.position());
						entry--;
						if (isExprired(meta))
							next(meta);
						else {
							bufferentry++;
							copyToBuffer(buffer, meta);
						}
					}
					if (bufferentry > 0) {
						meta.putInt(0, bufferentry);
						
						meta.put(buffer.toByteArray());
						meta.putInt(4, meta.position());
					}else{
						meta.putInt(4,8);
					}
					
				}
			} else {
				metaFile = new RandomAccessFile("memStoreMeta.dat", "rw");

				meta = metaFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 2 * 1024); // meta
																								// size
																								// fixed
																								// to
																								// 2kb
				meta.position(0);
				meta.putInt(0);
				meta.putInt(8);
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private FlyMemStore() {

	}

	public boolean isRegistered() {
		return isRegistered;
	}

	private static void copyToBuffer(ByteArrayOutputStream buffer, MappedByteBuffer meta2) throws IOException {
		int postion = meta2.position();
		int fileLength = meta2.getInt();
		meta2.position(postion);
		byte[] data = new byte[fileLength + 24];
		meta2.get(data);
		buffer.write(data);
	}

	private static void next(MappedByteBuffer meta2) {
		int fileLength = meta2.getInt();
		meta2.position(fileLength + 20);
	}

	private static boolean isExprired(MappedByteBuffer meta2) {
		int position = meta2.position();
		log.debug( "position  " + position);
		int fileLength = meta2.getInt();
		log.debug( "filelength  " + fileLength);
		int startpostion = meta2.getInt(meta2.position() + fileLength);
		int endpostion = meta2.getInt();
		meta2.position(position);
		return endpostion - startpostion <= 0;
	}

	protected static FlyMemStore newInstance() throws FileNotFoundException, IOException {
		return new FlyMemStore();
	}

	public void write(final Marker... markers) {

		final int dataLenght = Arrays.stream(markers).mapToInt(mapper -> mapper.length).sum();
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
		meta.putInt(writeIndexPostion, out.position());

	}

	public byte[] read() {
	
		byte[] data = null;
		final int lastWriteIndex = meta.getInt(writeIndexPostion);
		final int lastReadIndex = meta.getInt(readIndexPostion);
		out.position(lastReadIndex);
		final int datalenght = lastWriteIndex - lastReadIndex;
		if (datalenght > 0) {
			data = new byte[datalenght];
			out.get(data, lastReadIndex, lastWriteIndex - lastReadIndex);
			out.position(lastReadIndex);
		}
		meta.putInt(readIndexPostion, lastReadIndex);
		meta.putInt(writeIndexPostion, lastReadIndex);

		return data;
	}

	public static void close() throws IOException {
		outFile.close();
		metaFile.close();
	}

	
	public void registerMe(final String folderName) throws IOException {
		if (isRegistered)
			return;
		byte[] key = folderName.getBytes();
		int metaStartPostion = findMetaStartIndex(key);
		int allocate=0;
		int startPoint=0;
		if (metaStartPostion < 0) {
			allocate = totalbufSize / 10;
			int metasize = meta.getInt(0);
			startPoint = seek( allocate);
			metaStartPostion=insertToMeta(key, allocate, startPoint);
			metasize++;
			meta.putInt(0, metasize);
		}else{
			startPoint=meta.getInt(metaStartPostion+8);
			allocate=meta.getInt(metaStartPostion+12);
		}
		log.debug(" allocate "+ allocate);
		createOutFile(allocate, startPoint);
		readIndexPostion=metaStartPostion;
		writeIndexPostion=metaStartPostion+4;
		isRegistered=true;
	}

	private int seek(int allocate) {
		int metalength = meta.getInt(0);
		boolean notfixed = true;
		int startpoint = -allocate;
		while (notfixed) {
			meta.position(8);
			startpoint += allocate;
			notfixed = false;
			while (metalength > 0) {
				metalength--;
				int keyLength = meta.getInt();
				log.debug("keyLength " +keyLength);
				int previousStartPoint = meta.getInt(keyLength+8);
				int allocatedSpace = meta.getInt();
				if (!(previousStartPoint + allocatedSpace > startpoint
						|| (previousStartPoint >= startpoint + allocate))) {
					notfixed = true;
				}
			}
		}
		return startpoint;
	}

	private int insertToMeta(byte[] key, int allocate, int startPoint) {// { count ,metalength : { keylength , key , readindex, writeindex, filestartindex, allocate} } 
		int lastUpdateIndex = meta.getInt(4);
		log.debug("lastUpdateIndex"  +lastUpdateIndex);
		meta.putInt(lastUpdateIndex, key.length);
		meta.position(lastUpdateIndex+4);
		meta.put(key);
		int metaStartPostion = meta.position();
		meta.putInt(0);
		meta.putInt(0);
		meta.putInt(startPoint);
		meta.putInt(allocate);
		meta.putInt(4, meta.position());
		return metaStartPostion;
	}

	private int findMetaStartIndex(byte[] folderName) {
		int metalength = meta.getInt(0);
		meta.position(8);
		while (metalength > 0) {
			int folderLenght = meta.getInt();
			byte[] folder = new byte[folderLenght];
			meta.get(folder);
			if (Arrays.equals(folder, folderName)) {
				return meta.position();
			}
			metalength--;
		}

		return -1;
	}

	private void createOutFile(int allocate, int startpoint) throws IOException {
		out = outFile.getChannel().map(FileChannel.MapMode.READ_WRITE, startpoint, allocate);
	}

}
