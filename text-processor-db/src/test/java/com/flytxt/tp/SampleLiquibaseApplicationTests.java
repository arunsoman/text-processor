package com.flytxt.tp;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;

public class SampleLiquibaseApplicationTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testDefaultSettings() throws Exception {
		TextProcessorDB.main(new String[] { "--server.port=0" });

	}


}
