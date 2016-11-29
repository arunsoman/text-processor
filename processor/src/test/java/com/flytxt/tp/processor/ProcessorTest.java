package com.flytxt.tp.processor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.ByteStreams;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProcessorTest {
	@Autowired
	private ApplicationContext ctx;
	
	private Processor p = new Processor();
	private List<Job> jobs;
	
	private static final String DATA_FILE_PATH ="src"+File.separator+"test"+File.separator+"resources"+File.separator+"test-data";
	
	@Test 
	public void testStartFileReaders(){
		createJobs(1);
		p.setCtx(ctx);
		try {
			Method startFileReaders =p.getClass().getDeclaredMethod("startFileReaders", List.class);
			startFileReaders.setAccessible(true);
			startFileReaders.invoke(p, jobs);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	/**
	 * Create the Job object 
	 * @param cnt
	 */
	private void createJobs(int cnt){
		jobs = new ArrayList<Job>(cnt);
		for(int i = 0; i < cnt; i++){
			Job aJob = new Job();
			aJob.setName("com.flytxt.tp.processor.LineProcessorImpl");
			aJob.setByteCode(getLineProcessor());
			jobs.add(aJob);
			createTestData();
		}
	}
	
	
	/**
	 * Create the File Data . Which is deleted after the process .So again it copy to the source folder 
	 */
	private void createTestData() {
		File directory =  new File(LineProcessorImpl.SOURCE_PTH);
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		String sourcePath = LineProcessorImpl.SOURCE_PTH +File.separator+"test-data";
		File file = new File(sourcePath);
		if(!file.exists()){
			try {
				Files.copy(Paths.get(DATA_FILE_PATH), Paths.get(sourcePath), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				org.junit.Assert.fail("Unable to copy test data");
			}
		}
	}
	
	
	/**
	 * Load the Byte Code 
	 * @return
	 */
	private byte[] getLineProcessor() {	 
		String resource = LineProcessorImpl.class.getName().replace('.', '/')+".class";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        try {
			return ByteStreams.toByteArray(inputStream);
		} catch (IOException e1) {			
			e1.printStackTrace();
			return null;
		}		
	}
}
