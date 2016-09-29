package com.flytxt.parser.test.translator;

import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpMath;

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

    private Marker getMarker(final String str) {
        final Marker mocker = new Marker();
        mocker.index = 0;
        mocker.length = str.getBytes().length;
        return mocker;
    }

    @Given("^marker and math class$")
    public void markerAndMathClass() throws Throwable {
        m1 = new Marker();
        m2 = new Marker();
        mf = new MarkerFactory();
    }

    @Then("^for less than result should be \"([^\"]*)\"$")
    public void forLessThanResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1.equals("Y"), result);
    }

    @Then("^for less than eq result should be \"([^\"]*)\"$")
    public void forLessThanEqResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @When("^enter number to apply abs \"([^\"]*)\"$")
    public void enterNumberToApplyAbs(final String arg1) throws Throwable {
        resultStr = math.abs(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @Then("^after applying abs result should be \"([^\"]*)\"$")
    public void afterApplyingAbsResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^for greater than equal to result should be \"([^\"]*)\"$")
    public void forGreaterThanEqualToResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @When("^enter number \"([^\"]*)\" greater than  another number \"([^\"]*)\"$")
    public void enterNumberGreaterThanAnotherNumber(final String arg1, final String arg2) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @Then("^for greater than result should be \"([^\"]*)\"$")
    public void forGreaterThanResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @Then("^after sub long result should be \"([^\"]*)\"$")
    public void afterSubLongResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @Then("^after sub float result should be \"([^\"]*)\"$")
    public void afterSubFloatResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @Then("^after adding long result should be \"([^\"]*)\"$")
    public void afterAddingLongResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after adding float result should be \"([^\"]*)\"$")
    public void afterAddingFloatResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after ceil result should be \"([^\"]*)\"$")
    public void afterCeilResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after floor result should be \"([^\"]*)\"$")
    public void afterFloorResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after round result should be \"([^\"]*)\"$")
    public void afterRoundResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after checking two number to be equal result should be \"([^\"]*)\"$")
    public void afterCheckingTwoNumberToBeEqualResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1.equals("Y"), result);
    }

    @Then("^after extracting decimal result should be \"([^\"]*)\"$")
    public void afterExtractingDecimalResultShouldBe(final String arg1) throws Throwable {
    	assertEquals(arg1, resultStr);
    }

    @Then("^after extracting integer result should be \"([^\"]*)\"$")
    public void afterExtractingIntegerResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after checking a number to be strings result should be \"([^\"]*)\"$")
    public void afterCheckingANumberToBeStringsResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1.equals("Y"), result);
    }

    @When("^enter number \"([^\"]*)\" less than another number \"([^\"]*)\"$")
    public void enterNumberLessThanAnotherNumber(final String arg1, final String arg2) throws Throwable {
        result = math.lessThan(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
    }

    @When("^enter number \"([^\"]*)\" less than equal to another number \"([^\"]*)\"$")
    public void enterNumberLessThanEqualToAnotherNumber(final String arg1, final String arg2) throws Throwable {
        result = math.lessEqThan(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
    }

    @When("^enter number \"([^\"]*)\" greater than equal to another number \"([^\"]*)\"$")
    public void enterNumberGreaterThanEqualToAnotherNumber(final String arg1, final String arg2) throws Throwable {
        result = math.greaterEqThan(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
    }

    @When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for substraction$")
    public void enterTwoLongNumbersForSubstraction(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.subLong(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
        resultStr = m.toString(m.getData());
    }

    @When("^enter two float number \"([^\"]*)\" \"([^\"]*)\" for substraction$")
    public void enterTwoFloatNumberForSubstraction(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.subDouble(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
        resultStr = m.toString(m.getData());
    }

    @When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
    public void enterTwoLongNumbersForAddition(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.addLong(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
        resultStr = m.toString(m.getData());
    }

    @When("^enter two float numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
    public void enterTwoFloatNumbersForAddition(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.addDouble(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
        resultStr = m.toString(m.getData());
    }

    @When("^enter a number \"([^\"]*)\" to find ceil$")
    public void enterANumberToFindCeil(final String arg1) throws Throwable {
        resultStr = math.ceil(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^enter a number \"([^\"]*)\" to find floor$")
    public void enterANumberToFindFloor(final String arg1) throws Throwable {
        resultStr = math.floor(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^enter a number \"([^\"]*)\"  and the scale \"([^\"]*)\"$")
    public void enterANumberAndTheScale(final String arg1, final String arg2) throws Throwable {
        resultStr = math.round(arg1.getBytes(), Integer.parseInt(arg2), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^enter two equal numbers \"([^\"]*)\"  \"([^\"]*)\"$")
    public void enterTwoEqualNumbers(final String arg1, final String arg2) throws Throwable {
        result = math.eq(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
    }

    @When("^enter a decimal number \"([^\"]*)\" to extract decimal part of the number$")
    public void enterADecimalNumberToExtractDecimalPartOfTheNumber(final String arg1) throws Throwable {
        resultStr = math.extractDecimalFractionPart(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^enter a decimal number \"([^\"]*)\" to extract integer part of the number$")
    public void enterADecimalNumberToExtractIntegerPartOfTheNumber(final String arg1) throws Throwable {
        resultStr = math.extractDecimalIntegerPart(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^enter a number string \"([^\"]*)\"$")
    public void enterANumberString(final String arg1) throws Throwable {
        result = math.isNumber(arg1.getBytes(), getMarker(arg1), mf);
    }
}
