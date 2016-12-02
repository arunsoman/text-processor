package com.flytxt.tp.processor.filefilter;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
public class RegexFilterTest {
	File [] files = new File[10];
	RegexFilter rf = new RegexFilter();
	
	@Before
	public void init(){
		for(int i =0; i < 10; i++){
			File mockedFile = null;
			if(i%2==0){
				mockedFile =  Mockito.mock(File.class);
				Mockito.when(mockedFile.getName()).thenReturn(i+".txt");
			}else{
				mockedFile =  Mockito.mock(File.class);
				Mockito.when(mockedFile.getName()).thenReturn(i+".csv");
			}
			files[i] = mockedFile;
		}
	}
	
	@Test
	public void testPositive(){
		String regex = ".*\\.txt";
		rf.setRegex(regex);
		File[] okFiles = rf.canProcess(files);
		for(File file : okFiles){	
			if(null!=file)
			Assert.assertTrue(file.getName().contains(".txt"));
		}
		
		
	}
	
	@Test
	public void testNegative(){
		String regex = ".*\\.csv";
		rf.setRegex(regex);
		File[] okFiles = rf.canProcess(files);
		for(File file : okFiles){	
			if(null!=file)
			Assert.assertTrue(file.getName().contains(".csv"));
		}
	}
	
}
