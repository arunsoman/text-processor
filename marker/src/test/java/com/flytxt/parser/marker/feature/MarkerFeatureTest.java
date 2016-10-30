package com.flytxt.parser.marker.feature;

import static org.junit.Assert.assertEquals;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.flytxt.parser.marker.CurrentObject;
import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerDefaultConfig;
import com.flytxt.parser.marker.MarkerFactory;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.Setter;


@ContextConfiguration(classes = MarkerDefaultConfig.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class MarkerFeatureTest {

	@Autowired
	@Getter @Setter 
	private MarkerFactory markerFactory;
	private CurrentObject currentObject;
	
	@Before
    public void init(){
    	currentObject = markerFactory.getCurrentObject();
    	currentObject.setFileName("TestFile");
    	currentObject.setFolderName("TestFolder");
    }
	private Marker getMarker(String str) {
		byte[] lineMarker = str.getBytes();
    	currentObject.setLineMarker(lineMarker);
    	currentObject.setCurrentLine(0, lineMarker.length);
    	Marker line = markerFactory.createMarker(null,0, lineMarker.length);
    	return line;
	}

	Marker splitAndGetMarker;
	byte[] data;
	int size;
	
	@Given("^markerfactory$")
	public void markerfactory() throws Throwable {
	}

	@Given("^tokenfactory$")
	public void tokenfactory() throws Throwable {
	}

	@When("^In the input \"([^\"]*)\" delimited \"([^\"]*)\",\"([^\"]*)\"$")
	public void inTheInputDelimited(String arg1, String arg2, String arg3) throws Throwable {
		Marker m1 = getMarker(arg1);
		byte[] inputB = arg1.getBytes();
		byte[] token = arg2.getBytes();
		splitAndGetMarker = m1.splitAndGetMarker( token, Integer.parseInt(arg3), markerFactory);
		data = inputB;
	}

	@Then("^result should be \"([^\"]*)\"$")
	public void resultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, splitAndGetMarker.toString());
	}

	@When("^In the input \"([^\"]*)\" delimited \"([^\"]*)\"$")
	public void inTheInputDelimited(String arg1, String arg2) throws Throwable {
		byte[] inputB = arg1.getBytes();
		byte[] token = arg2.getBytes();
		markerFactory.setMaxListSize(arg1.split(arg2).length);
		size = getMarker(arg1).splitAndGetMarkerList( token, markerFactory).size();
	}

	@Then("^lenght should be \"([^\"]*)\"$")
	public void lenghtShouldBe(String arg1) throws Throwable {
		assertEquals(Integer.parseInt(arg1), size);
	}

	@When("^In the input \"([^\"]*)\" delimited \"([^\"]*)\" get \"([^\"]*)\" element$")
	public void inTheInputDelimitedGetElement(String arg1, String arg2, String arg3) throws Throwable {
		Marker m1 = getMarker(arg1);
		splitAndGetMarker = m1.splitAndGetMarker( arg2.getBytes(), Integer.parseInt(arg3), markerFactory);
		data = arg1.getBytes();
	}
}
