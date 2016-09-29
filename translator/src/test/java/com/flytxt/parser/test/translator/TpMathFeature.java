package com.flytxt.parser.test.translator;

import com.flytxt.parser.marker.Marker;
import static org.junit.Assert.assertEquals;
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
	String resultStr;
	
	private Marker getMarker(String str) {
		Marker mocker = new Marker();
		mocker.index = 0;
		mocker.length = str.length();
		return mocker;
	}

	@Given("^marker and math class$")
	public void markerAndMathClass() throws Throwable {
		m1 = new Marker();
		m2 = new Marker();
		mf = new MarkerFactory();
	}

	@Then("^for less than result should be \"([^\"]*)\"$")
	public void forLessThanResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@Then("^for less than eq result should be \"([^\"]*)\"$")
	public void forLessThanEqResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@When("^enter number to apply abs \"([^\"]*)\"$")
	public void enterNumberToApplyAbs(String arg1) throws Throwable {
	    resultStr = math.abs(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@Then("^after applying abs result should be \"([^\"]*)\"$")
	public void afterApplyingAbsResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^for greater than equal to result should be \"([^\"]*)\"$")
	public void forGreaterThanEqualToResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@When("^enter number \"([^\"]*)\" greater than  another number \"([^\"]*)\"$")
	public void enterNumberGreaterThanAnotherNumber(String arg1, String arg2) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@Then("^for greater than result should be \"([^\"]*)\"$")
	public void forGreaterThanResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@Then("^after sub long result should be \"([^\"]*)\"$")
	public void afterSubLongResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@Then("^after sub float result should be \"([^\"]*)\"$")
	public void afterSubFloatResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@Then("^after adding long result should be \"([^\"]*)\"$")
	public void afterAddingLongResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^after adding float result should be \"([^\"]*)\"$")
	public void afterAddingFloatResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^after ceil result should be \"([^\"]*)\"$")
	public void afterCeilResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^after floor result should be \"([^\"]*)\"$")
	public void afterFloorResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^after round result should be \"([^\"]*)\"$")
	public void afterRoundResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^after checking two number to be equal result should be \"([^\"]*)\"$")
	public void afterCheckingTwoNumberToBeEqualResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}

	@Then("^after extracting decimal result should be \"([^\"]*)\"$")
	public void afterExtractingDecimalResultShouldBe(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@Then("^after extracting integer result should be \"([^\"]*)\"$")
	public void afterExtractingIntegerResultShouldBe(String arg1) throws Throwable {
	    assertEquals(arg1, resultStr);
	}

	@Then("^after checking a number to be strings result should be \"([^\"]*)\"$")
	public void afterCheckingANumberToBeStringsResultShouldBe(String arg1) throws Throwable {
		assertEquals(result, arg1.equals("Y"));
	}
	
	@When("^enter number \"([^\"]*)\" less than another number \"([^\"]*)\"$")
	public void enterNumberLessThanAnotherNumber(String arg1, String arg2) throws Throwable {
		result = math.lessThan(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
	}

	@When("^enter number \"([^\"]*)\" less than equal to another number \"([^\"]*)\"$")
	public void enterNumberLessThanEqualToAnotherNumber(String arg1, String arg2) throws Throwable {
		result = math.lessEqThan(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
	}

	@When("^enter number \"([^\"]*)\" greater than equal to another number \"([^\"]*)\"$")
	public void enterNumberGreaterThanEqualToAnotherNumber(String arg1, String arg2) throws Throwable {
		result = math.greaterEqThan(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
	}

	@When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for substraction$")
	public void enterTwoLongNumbersForSubstraction(String arg1, String arg2) throws Throwable {
		Marker m = math.subLong(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
		resultStr = m.toString(m.getData());
	}

	@When("^enter two float number \"([^\"]*)\" \"([^\"]*)\" for substraction$")
	public void enterTwoFloatNumberForSubstraction(String arg1, String arg2) throws Throwable {
		Marker m = math.subDouble(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
		resultStr = m.toString(m.getData());
	}

	@When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
	public void enterTwoLongNumbersForAddition(String arg1, String arg2) throws Throwable {
		Marker m = math.addLong(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
		resultStr = m.toString(m.getData());
	}

	@When("^enter two float numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
	public void enterTwoFloatNumbersForAddition(String arg1, String arg2) throws Throwable {
		Marker m = math.addDouble(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
		resultStr = m.toString(m.getData());
	}

	@When("^enter a number \"([^\"]*)\" to find ceil$")
	public void enterANumberToFindCeil(String arg1) throws Throwable {
		resultStr = math.ceil(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@When("^enter a number \"([^\"]*)\" to find floor$")
	public void enterANumberToFindFloor(String arg1) throws Throwable {
		resultStr = math.floor(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@When("^enter a number \"([^\"]*)\"  and the scale \"([^\"]*)\"$")
	public void enterANumberAndTheScale(String arg1, String arg2) throws Throwable {
		resultStr = math.round(arg1.getBytes(), Integer.parseInt(arg2), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@When("^enter two equal numbers \"([^\"]*)\"  \"([^\"]*)\"$")
	public void enterTwoEqualNumbers(String arg1, String arg2) throws Throwable {
	    result = math.eq(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
	}

	@When("^enter a decimal number \"([^\"]*)\" to extract decimal part of the number$")
	public void enterADecimalNumberToExtractDecimalPartOfTheNumber(String arg1) throws Throwable {
		resultStr = math.extractDecimalFractionPart(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@When("^enter a decimal number \"([^\"]*)\" to extract integer part of the number$")
	public void enterADecimalNumberToExtractIntegerPartOfTheNumber(String arg1) throws Throwable {
	    resultStr = math.extractDecimalIntegerPart(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@When("^enter a number string \"([^\"]*)\"$")
	public void enterANumberString(String arg1) throws Throwable {
	    result = math.isNumber(arg1.getBytes(), getMarker(arg1), mf);
	}
}
