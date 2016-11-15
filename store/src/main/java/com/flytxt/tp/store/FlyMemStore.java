package com.flytxt.tp.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Semaphore;

import com.flytxt.tp.marker.Marker;


public class FlyMemStore {
	private  MappedByteBuffer out;

	// read and write indexes are stored in this buffer in ((int)readIndex,
	// (int)writeIndex) format
	private  MappedByteBuffer meta;

	private  Semaphore semaphore = new Semaphore(1);
	private  final int bufSize = 1 * 1024 * 1024;

	public FlyMemStore() throws FileNotFoundException, IOException {
		out = new RandomAccessFile("hadoopData.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, bufSize);
		meta = new RandomAccessFile("hadoopMeta.dat", "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 8);
	}
	public void write(Marker... markers){
		//keep on pushing data to out, when there is no more space to write throw Arrayoutofbound
	}
	
	public byte[] read(){
		throw new RuntimeException();
	}
}
