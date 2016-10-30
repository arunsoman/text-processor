package com.flytxt.parser.marker.feature;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions( features = "classpath:features",glue = "com.flytxt.parser.marker.feature",
	snippets=SnippetType.CAMELCASE,
	strict=false)
@ComponentScan
public class StoryRunner{
}