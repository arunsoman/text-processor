package com.flytxt.parser.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.flytxt.tp.processor.Job;
import com.flytxt.tp.processor.JobRepo;
import com.flytxt.tp.processor.ProxyScripts;

public class ProxyScriptTest {

	private ProxyScripts ps = new ProxyScripts();
	private JobRepo repo;
	@Before
	public void init() {
		repo = Mockito.mock(JobRepo.class);
		Mockito.when(repo.findByhostNameAndActiveTrueAndStatusTrue(null)).thenReturn(mockJobs());
		ps.setRepo(repo);
	}
	private List<Job> mockJobs(){
		List<Job> jobs = new ArrayList<>();
		return jobs;
	}
	
	@Test
	public void testInit(){
		try {
			ps.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}