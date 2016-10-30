package com.flytxt.parser.translator.feature;

import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpDate;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TpDateFeature {
	TpDate tpDate;
	MarkerFactory mf;
	Marker marker1;
	Marker marker2;
	boolean result;
	String resultStr;
	
	@Given("^marker and tpdate class$")
	public void markerAndTpdateClass() throws Throwable {
		 mf = new MarkerFactory();
		 tpDate = new TpDate();
	}

	private Marker getMarker(String str) {
		Marker mocker = new Marker();
		mocker.index = 0;
		mocker.length = str.length();
		return mocker;
	}

	@When("^\"([^\"]*)\" after \"([^\"]*)\"$")
	public void after(String arg1, String arg2) throws Throwable {
		byte[] d1 = arg1.getBytes();
		byte[] d2 = arg2.getBytes();
		result = tpDate.after( getMarker(arg1), getMarker(arg2));
	}

	@Then("^date one after date two returns \"([^\"]*)\"$")
	public void dateOneAfterDateTwoReturns(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	

	@When("^\"([^\"]*)\" before \"([^\"]*)\"$")
	public void before(String arg1, String arg2) throws Throwable {
		byte[] d1 = arg1.getBytes();
		byte[] d2 = arg2.getBytes();
		result = tpDate.before( getMarker(arg1),  getMarker(arg2));
	}
	
	@Then("^date one before date two returns \"([^\"]*)\"$")
	public void dateOneBeforeDateTwoReturns(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}


	@When("^\"([^\"]*)\" minus \"([^\"]*)\"$")
	public void minus(String arg1, String arg2) throws Throwable {
		resultStr = tpDate.differenceInMillis(getMarker(arg1),  getMarker(arg2), mf).toString();
	}

	@Then("^diff is \"([^\"]*)\"$")
	public void diffIs(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}
}
