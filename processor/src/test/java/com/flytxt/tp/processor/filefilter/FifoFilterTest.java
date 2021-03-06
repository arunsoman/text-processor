package com.flytxt.tp.processor.filefilter;

import java.io.File;

import org.junit.Assert;
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
		files[5] =null;
	}
	@Test
	public void testPositive(){
		File[] okFiles = fifo.canProcess(files);
		File preFile = null;
		for(File aFile: okFiles){
			if(null!=aFile){
				Assert.assertNotNull(aFile.getName());			
				if(preFile!=null){
					Assert.assertTrue(aFile.lastModified() >= preFile.lastModified());
				}			
				preFile =aFile;	
			}
		}
	}
}
