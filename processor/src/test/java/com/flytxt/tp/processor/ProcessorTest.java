package com.flytxt.tp.processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;


public class ProcessorTest {

	private Processor p = new Processor();
	private List<Job> jobs;
	
	@Test @Ignore
	public void testStartFileReaders(){
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
