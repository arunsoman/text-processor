package com.flytxt.parser.translator.feature;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "com.flytxt.parser.test.translator",
snippets=SnippetType.CAMELCASE,plugin = {"pretty"},strict=false)
public class StoryRunnerTest {
	

}
