package com.flytxt.parser.processor;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.flytxt.tp.processor.FolderEventListener;
import com.flytxt.tp.processor.ProcessorConfig;
import com.flytxt.tp.processor.TestConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest("spring.profiles.active=processor")
@ActiveProfiles("test")
@SpringBootApplication(scanBasePackageClasses=ProcessorConfig.class)
public class FolderEventListenerTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
    
	@Autowired
	private FolderEventListener listner;
	
    @org.junit.Before
	public void before() throws IOException{
		folder.create();
		folder.newFolder("tmp1");
		folder.newFolder("tmp2");
	}
    
    @Test
    public void linkCreation() throws IOException{
    	listner.attachFolder("tmp1", "listner.txt", "tmp2");
    }
	


}
