package com.flytxt.tp.processor.filefilter;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FifoFilterTest {
	File [] files = new File[10];
	FifoFilter fifo = new FifoFilter();
	
	@Before
	public void init(){
		for(int i =0; i < 10; i++){
		File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.getName()).thenReturn(i+".txt");
		if(i%2==0)
			Mockito.when(mockedFile.lastModified()).thenReturn(System.currentTimeMillis()-100000);
		else
			Mockito.when(mockedFile.lastModified()).thenReturn(System.currentTimeMillis());
		files[i] = mockedFile;
		}
	}
	@Test
	public void testPositive(){
		File[] okFiles = fifo.canProcess(files);
		for(File aFile: okFiles){
		System.out.println(aFile.getName());
		}
	}
}
