package com.flytxt.tp.processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProcessorTest {
	@Autowired
	private ApplicationContext ctx;
	
	private Processor p = new Processor();
	private List<Job> jobs;
	
	@Test @Ignore
	public void testStartFileReaders(){
		createJobs(1);
		p.setCtx(ctx);
		try {
			Method startFileReaders =p.getClass().getMethod("startFileReaders", List.class);
			startFileReaders.setAccessible(true);
			startFileReaders.invoke(p, jobs);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void createJobs(int cnt){
		jobs = new ArrayList<Job>(cnt);
		for(int i = 0; i < cnt; i++){
			Job aJob = new Job();
			
			jobs.add(aJob);
		}
	}
}
