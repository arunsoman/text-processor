package com.flytxt.tp.processor.filefilter;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LastModifiedWindowFilterTest{
	LastModifiedWindowFilter lm = new LastModifiedWindowFilter();
	File [] files = new File[10];
	long currentTime = 0;
	
	@Before
	public void init(){
		currentTime = System.currentTimeMillis();
		for(int i =0; i < 10; i++){
		File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.getName()).thenReturn(i+".txt");
		if(i%2==0)
			Mockito.when(mockedFile.lastModified()).thenReturn(currentTime -50000);
		else
			Mockito.when(mockedFile.lastModified()).thenReturn(System.currentTimeMillis());
		files[i] = mockedFile;
		}
	}
	
	@Test
	public void testPositive(){
		lm.windowUnit = 50000;
		File[] okFiles = lm.canProcess(files);
		for(File aFile: okFiles){
			if(aFile != null){				
				Assert.assertNotNull(aFile.getName());					
				Assert.assertTrue(aFile.lastModified() >= (currentTime-50000));
			}		
		}	
	}

}
