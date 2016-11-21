package com.flytxt.tp.processor.filefilter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
public class RegexFilterTest {
	File [] files = new File[10];
	RegexFilter rf = new RegexFilter();
	
	@Before
	public void init(){
		for(int i =0; i < 10; i++){
		File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.getName()).thenReturn(i+".txt");
		files[i] = mockedFile;
		}
	}
	
	@Test
	public void testPositive(){
		String regex = ".*\\.txt";
		rf.setRegex(regex);
		File[] okFiles = rf.canProcess(files);
		//TODO
	}
	
	@Test
	public void testNegative(){
		String regex = ".*\\.csv";
		rf.setRegex(regex);
		File[] okFiles = rf.canProcess(files);
		//TODO
	}
	
}
