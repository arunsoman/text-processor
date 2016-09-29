package com.flytxt.parser.test.translator;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpMath;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TpMathFeature {

	TpMath math = new TpMath();
	Marker m1;
	Marker m2;
	MarkerFactory mf;
	boolean result;

	@Given("^marker and math class$")
	public void markerAndMathClass() throws Throwable {
		m1 = new Marker();
		m2 = new Marker();
		mf = new MarkerFactory();
	}

	/*
	 * @When("^enter \"([^\"]*)\" less than \"([^\"]*)\"$") public void
	 * enterLessThan(String arg1, String arg2) throws Throwable { result =
	 * math.lessThan(arg1.getBytes(), m1, arg2.getBytes(), m2, mf); }
	 * 
	 * @Then("^result will be true$") public void resultWillBeTrue() throws
	 * Throwable { assertEquals(result, true); }
	 * 
	 * @When("^enter \"([^\"]*)\" less than equal \"([^\"]*)\"$") public void
	 * enterLessThanEqual(String arg1, String arg2) throws Throwable { result =
	 * math.lessEqThan(arg1.getBytes(), m1, arg2.getBytes(), m2, mf); }
	 */

	@When("^enter number \"([^\"]*)\" less than another number \"([^\"]*)\"$")
	public void enterNumberLessThanAnotherNumber(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter number \"([^\"]*)\" less than equal to another number \"([^\"]*)\"$")
	public void enterNumberLessThanEqualToAnotherNumber(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a negative number \"([^\"]*)\"$")
	public void enterANegativeNumber(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter number \"([^\"]*)\" greater than equal to another number \"([^\"]*)\"$")
	public void enterNumberGreaterThanEqualToAnotherNumber(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for substraction$")
	public void enterTwoLongNumbersForSubstraction(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two float number \"([^\"]*)\" \"([^\"]*)\" for substraction$")
	public void enterTwoFloatNumberForSubstraction(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
	public void enterTwoLongNumbersForAddition(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two float numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
	public void enterTwoFloatNumbersForAddition(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two numbers \"([^\"]*)\"  \"([^\"]*)\" for addition$")
	public void enterTwoNumbersForAddition(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two numbers \"([^\"]*)\" \"([^\"]*)\" for substraction$")
	public void enterTwoNumbersForSubstraction(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a number \"([^\"]*)\" to find ceil$")
	public void enterANumberToFindCeil(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a number \"([^\"]*)\" to find floor$")
	public void enterANumberToFindFloor(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a number \"([^\"]*)\"  and the scale \"([^\"]*)\"$")
	public void enterANumberAndTheScale(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter two equal numbers \"([^\"]*)\"  \"([^\"]*)\"$")
	public void enterTwoEqualNumbers(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a decimal number \"([^\"]*)\" to extract decimal part of the number$")
	public void enterADecimalNumberToExtractDecimalPartOfTheNumber(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a decimal number \"([^\"]*)\" to extract integer part of the number$")
	public void enterADecimalNumberToExtractIntegerPartOfTheNumber(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^enter a number string \"([^\"]*)\"$")
	public void enterANumberString(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^result should be \"([^\"]*)\"$")
	public void resultShouldBe(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}
}
