package com.flytxt.tp.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ControllerTest {
	
	@Autowired
	private Controller controller;


	@Test
	public void testReload() throws Exception{
		controller.reload();
	}
	@Test
	@Ignore
	public void testStop(){
		int size = 5;
		List<FlyReader> flyReaders = new ArrayList<FlyReader>(size);
		for(int i = 0; i < size; i++){
			FlyReader flyReader = Mockito.mock(FlyReader.class);
			flyReader.set(i+"folder-dummy", new test.TestScript(), null);
			flyReaders.add(flyReader);
		}
		Processor mockProessor = Mockito.mock(Processor.class);
		mockProessor.fileReaders = flyReaders;
		try {
			mockProessor.startFileReaders();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller controller = new Controller();
		controller.processor = mockProessor;
		
		controller.stop();
		for(FlyReader aReader : flyReaders){
			System.out.println(aReader.getStatus());
		}
	}
}
