package com.flytxt.parser.test.translator;

import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpMath;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Math {

	TpMath math = new TpMath();
	Marker m1;
	Marker m2;
	MarkerFactory mf;
	boolean result;
	 
	@Given("^marker and math class$")
	public void markerAndMathClass() throws Throwable {
		m1 = new Marker();
		m2=new Marker();
		mf= new MarkerFactory();
	}

	@When("^enter \"([^\"]*)\" less than \"([^\"]*)\"$")
	public void enterLessThan(String arg1, String arg2) throws Throwable {
		result = math.lessThan(arg1.getBytes(), m1, arg2.getBytes(), m2, mf);
	}

	@Then("^result will be true$")
	public void resultWillBeTrue() throws Throwable  {
		assertEquals(result, true);
	}
	
	@When("^enter \"([^\"]*)\" less than equal \"([^\"]*)\"$")
	public void enterLessThanEqual(String arg1, String arg2) throws Throwable {
		result = math.lessEqThan(arg1.getBytes(), m1, arg2.getBytes(), m2, mf);
	}
	
	
}
