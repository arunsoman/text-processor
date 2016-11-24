package com.flytxt.tp;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;
import static org.assertj.core.api.Assertions.assertThat;

public class SampleLiquibaseApplicationTests {
	
	@Rule
	public OutputCapture outputCapture = new OutputCapture();
	
	@Test
	public void testDefaultSettings() throws Exception {
		TextProcessorDB.main(new String[] { "--server.port=0" });
		
		String output = this.outputCapture.toString();
		assertThat(output).contains("Successfully acquired change log lock")
		.contains("Creating database history "
				+ "table with name: PUBLIC.DATABASECHANGELOG")
		.contains("Table person created")
		.contains("ChangeSet classpath:/db/"
				+ "changelog/db.changelog-master.yaml::1::"
				+ "marceloverdijk ran successfully")
		.contains("New row inserted into person")
		.contains("ChangeSet classpath:/db/changelog/"
				+ "db.changelog-master.yaml::2::"
				+ "marceloverdijk ran successfully")
		.contains("Successfully released change log lock");
	}


}
