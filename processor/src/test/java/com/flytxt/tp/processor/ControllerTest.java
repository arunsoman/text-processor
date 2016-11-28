package com.flytxt.tp.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
	
	
	@Test
	public void testReload(){
		int size = 5;
		List<FlyReader> flyReaders = new ArrayList<FlyReader>(size);
		for(int i = 0; i < size; i++){
			FlyReader flyReader = Mockito.mock(FlyReader.class);
			flyReaders.add(flyReader);
		}
		Processor mockProessor = Mockito.mock(Processor.class);
		mockProessor.fileReaders = flyReaders;
		
		Controller controller = new Controller();
		controller.processor = mockProessor;
		
		controller.reload();
	}
	@Test
	public void testStop(){
		int size = 5;
		List<FlyReader> flyReaders = new ArrayList<FlyReader>(size);
		for(int i = 0; i < size; i++){
			FlyReader flyReader = Mockito.mock(FlyReader.class);
			flyReader.set(i+"folder-dummy", new test.TestScript(), "*.txt");
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
