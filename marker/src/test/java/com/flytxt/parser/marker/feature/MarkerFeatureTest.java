package com.flytxt.parser.marker.feature;

import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.marker.TokenFactory;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MarkerFeatureTest {

	private MarkerFactory mf = new MarkerFactory();
	private TokenFactory tf = new TokenFactory();
	Marker splitAndGetMarker;
	byte[] data;
	int size;

	private Marker getMarker(String str) {
		Marker mocker = new Marker();
		mocker.index = 0;
		mocker.length = str.length();
		return mocker;
	}

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
		splitAndGetMarker = m1.splitAndGetMarker(inputB, token, Integer.parseInt(arg3), mf);
		data = inputB;
	}

	@Then("^result should be \"([^\"]*)\"$")
	public void resultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, splitAndGetMarker.toString(data));
	}

	@When("^In the input \"([^\"]*)\" delimited \"([^\"]*)\"$")
	public void inTheInputDelimited(String arg1, String arg2) throws Throwable {
		byte[] inputB = arg1.getBytes();
		byte[] token = arg2.getBytes();
		mf.setMaxListSize(arg1.split(arg2).length);
		size = getMarker(arg1).splitAndGetMarkers(inputB, token, mf).size();
	}

	@Then("^lenght should be \"([^\"]*)\"$")
	public void lenghtShouldBe(String arg1) throws Throwable {
		assertEquals(Integer.parseInt(arg1), size);
	}

	@When("^In the input \"([^\"]*)\" delimited \"([^\"]*)\" get \"([^\"]*)\" element$")
	public void inTheInputDelimitedGetElement(String arg1, String arg2, String arg3) throws Throwable {
		Marker m1 = getMarker(arg1);
		splitAndGetMarker = m1.splitAndGetMarker(arg1.getBytes(), arg2.getBytes(), Integer.parseInt(arg3), mf);
		data = arg1.getBytes();
	}
}
