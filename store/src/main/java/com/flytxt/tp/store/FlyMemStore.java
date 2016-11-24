package com.flytxt.tp.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
	private static MappedByteBuffer hmeta;

	private static final byte[] newLine = "\n".getBytes();
	private static final byte[] comma = ",".getBytes();
	private static RandomAccessFile outFile;
	private static RandomAccessFile metaFile;
	private static RandomAccessFile historyMetaFile;
	private static final int totalbufSize = 1024 * 3;
	private byte[] historyData;
	private int metaStartPostion;
	private  int writeIndexPostion=12;
	private  int readIndexPostion=8;
	static {
		try {
			outFile = new RandomAccessFile("hadoopData.dat", "rw");
			final File file = new File("hadoopMeta.dat");
			if(file.exists()) {
				final String filename="hadoopMeta.dat.hist"+System.currentTimeMillis();
				file.renameTo(new File(filename));
				historyMetaFile= new RandomAccessFile("hadoopMeta.dat", "rw");
				hmeta=historyMetaFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 2*1024);
			}
			metaFile = new RandomAccessFile("hadoopMeta.dat", "rw");

			meta = metaFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 2*1024); // meta size fixed to 2kb
			meta.position(0);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private FlyMemStore() {

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
		if(historyData !=null){
			final byte[] clone = historyData.clone();
			historyData=null;
			return clone;
		}
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

	public void close() throws IOException {
		outFile.close();
		metaFile.close();


	}

	public void reAllocate(final String folderName) {

	}

	public void registerMe(final String folderName, final int start, final int length) throws IOException {
		final int allocate = totalbufSize / length;
		final int startPoint = start * allocate;
		historyData=insertOrUpdateMeta(folderName, allocate, startPoint);
		out = outFile.getChannel().map(FileChannel.MapMode.READ_WRITE, startPoint, allocate);
		writeIndexPostion+=metaStartPostion;
		readIndexPostion+=metaStartPostion;
	}

	private byte[] insertOrUpdateMeta(final String folderName,  int allocate,  final int startPoint) {
		final byte[] folder = folderName.getBytes();
		int lastReadIndex=0;
		int lastWriteIndex=0;
		final byte[] dummyName=  new byte[folder.length];
		final int lenght=folder.length+4*5;
		byte[] previousData =null;
		if(hmeta !=null) {
			hmeta.position(0);
			while(hmeta.getInt()>0 && hmeta.remaining()>=lenght){
				final int filenameLength = hmeta.getInt();
				hmeta.get(dummyName, hmeta.position(), filenameLength);
				if(Arrays.equals(dummyName, folder)){

					final int previousStartPoint=hmeta.getInt();
					final int previousAllocation=hmeta.getInt();
					log.debug(" previousStartPoint "+previousStartPoint+" previousAllocation "+previousAllocation);
					lastReadIndex=hmeta.getInt();
					lastWriteIndex=hmeta.getInt();
					final int bufferDataLength=lastWriteIndex-lastReadIndex;
					if(bufferDataLength !=0 && bufferDataLength >allocate){
						allocate=bufferDataLength;
						try {
							final int previousDataLenght=lastWriteIndex-lastReadIndex;
							previousData = new byte[previousDataLenght];
							outFile.getChannel().map(FileChannel.MapMode.READ_WRITE, previousStartPoint+lastReadIndex, previousDataLenght).get(previousData);
						} catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}
				return previousData;
			}
		}


		meta.putInt(lenght);
		meta.putInt(folder.length);
		meta.put(folder);
		metaStartPostion=meta.position();
		meta.putInt(startPoint);
		meta.putInt(allocate);
		meta.putInt(0);
		meta.putInt(0);
		return previousData;
	}
}
