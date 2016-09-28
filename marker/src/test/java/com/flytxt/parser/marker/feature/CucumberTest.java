package com.flytxt.parser.marker.feature;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions( glue = "com.flytxt.parser.marker.feature",
	snippets=SnippetType.CAMELCASE,
	strict=false)
public class CucumberTest{
}