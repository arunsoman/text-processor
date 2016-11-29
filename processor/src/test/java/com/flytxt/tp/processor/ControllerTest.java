package com.flytxt.tp.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ProcessorConfig.class)
@ActiveProfiles(profiles={"test","processor"})
public class ControllerTest {

	@Autowired
	private Controller controller;


	@Test
	public void testReload() throws Exception{
		controller.reload();
	}

	@Test
	public void testStop(){
		controller.stop();
	}
}
