package com.flytxt.tp.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ClasspathChannel implements Channel{
	private File file;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	@Override
	public void close() throws Exception {
		bufferedReader.close();
		fileReader.close();
	}

	@Override
	public BufferedReader open(String fileName) throws FileNotFoundException {
		ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource(fileName).getFile());
        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        return bufferedReader;
	}

}
