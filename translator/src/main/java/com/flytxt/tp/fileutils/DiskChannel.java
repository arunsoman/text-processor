package com.flytxt.tp.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DiskChannel implements Channel {
	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private File file;

	@Override
	public BufferedReader open(String fileName) throws FileNotFoundException {
		file = new File(fileName);
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		return bufferedReader;
	}

	@Override
	public void close() throws Exception {
		bufferedReader.close();
		fileReader.close();
		
	}

}
