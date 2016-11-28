package com.flytxt.tp.processor.filefilter;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FileIteratorTest {
	FileIterator<Path> fileIterator =null;
	@Before
	public void prepareTestData(){
		File [] filteredFiles = new File[11];
		filteredFiles[1] = null;	
		for(int i=1;i<10;i++){
			if(i%2!=0){
				filteredFiles[i] = null;				
			}else{
				File mockedFile = Mockito.mock(File.class);
				filteredFiles[i] =mockedFile;
				Mockito.when(mockedFile.toPath()).thenReturn(Mockito.mock(Path.class));
			}
		}
		fileIterator = new FileIterator<>(filteredFiles);
		
	}

	@Test
	public void test_Iterable() {
		for(Path path : fileIterator){
			Assert.assertNotNull(path);
		}
		
	}
	
	@Test
	public void test_Iterator() {
		for(Iterator<Path> pathIterator = fileIterator;pathIterator.hasNext();){			
			Assert.assertNotNull(pathIterator.next());
		}
		
	}

}
