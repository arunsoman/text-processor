package com.flytxt.parser.processor;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.flytxt.tp.processor.FolderEventListener;
import com.flytxt.tp.processor.ProcessorConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ProcessorConfig.class)
@ActiveProfiles({"test","processor"})
public class FolderEventListenerTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@org.junit.Before
	public void before() throws IOException{
		folder.create();
		folder.newFolder("tmp1");
		folder.newFolder("tmp2");
	}

	@Test
	public void linkCreation() throws IOException{
		//listner.attachFolder("tmp1", "listner.txt", "tmp2");
	}



}
