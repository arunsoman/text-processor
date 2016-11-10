package com.flytxt.tp.translator.feature;

import static org.junit.Assert.assertEquals;

import com.flytxt.tp.marker.Marker;
import com.flytxt.tp.translator.TpAbsTest;
import com.flytxt.tp.translator.TpMath;
import com.flytxt.tp.translator.feature.transformer.MarkerHelper;
import com.flytxt.tp.translator.feature.transformer.MarkerTransform;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TpMathFeature extends TpAbsTest{

    TpMath math = new TpMath();

    Marker m1;

    Marker m2;

    boolean result;

    String resultStr;


    

    @Given("^marker and math class$")
    public void markerAndMathClass() throws Throwable {
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
        resultStr = math.abs( getMarker(arg1), markerFactory).toString();
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
    	result= math.greaterThan( getMarker(arg1), getMarker(arg2), markerFactory);

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
    public void afterCeilResultShouldBe(String arg1) throws Throwable{
        assertEquals(arg1, arg1);
    }

    @Then("^after floor result should be \"([^\"]*)\"$")
    public void afterFloorResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^after round result should be \"([^\"]*)\"$")
    public void afterRoundResultShouldBe(final String arg1) throws Throwable {
        assertEquals(arg1,resultStr);
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
        result = math.lessThan( getMarker(arg1),  getMarker(arg2), markerFactory);
    }

    @When("^enter number \"([^\"]*)\" less than equal to another number \"([^\"]*)\"$")
    public void enterNumberLessThanEqualToAnotherNumber(final String arg1, final String arg2) throws Throwable {
        result = math.lessEqThan( getMarker(arg1),  getMarker(arg2), markerFactory);
    }

    @When("^enter number \"([^\"]*)\" greater than equal to another number \"([^\"]*)\"$")
    public void enterNumberGreaterThanEqualToAnotherNumber(final String arg1, final String arg2) throws Throwable {
        result = math.greaterEqThan( getMarker(arg1),  getMarker(arg2), markerFactory);
    }

    @When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for substraction$")
    public void enterTwoLongNumbersForSubstraction(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.subLong( getMarker(arg1),  getMarker(arg2), markerFactory);
        resultStr = m.toString();
    }

    @When("^enter two float number \"([^\"]*)\" \"([^\"]*)\" for substraction$")
    public void enterTwoFloatNumberForSubstraction(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.subDouble( getMarker(arg1),  getMarker(arg2), markerFactory);
        resultStr = m.toString();
    }

    @When("^enter two long numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
    public void enterTwoLongNumbersForAddition(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.addLong( getMarker(arg1),  getMarker(arg2), markerFactory);
        resultStr = m.toString();
    }

    @When("^enter two float numbers \"([^\"]*)\" \"([^\"]*)\" for addition$")
    public void enterTwoFloatNumbersForAddition(final String arg1, final String arg2) throws Throwable {
        final Marker m = math.addDouble( getMarker(arg1),  getMarker(arg2), markerFactory);
        resultStr = m.toString();
    }

    @When("^enter a number \"([^\"]*)\" to find ceil$")
    public void enterANumberToFindCeil(final String arg1) throws Throwable {
    	resultStr = math.ceil( getMarker(arg1), markerFactory).toString() ;
    }

    @When("^enter a number \"([^\"]*)\" to find floor$")
    public void enterANumberToFindFloor(final String arg1) throws Throwable {
        Marker floor = math.floor( getMarker(arg1), markerFactory);
        resultStr = floor.toString();
    }

    @When("^enter a number \"([^\"]*)\"  and the scale \"([^\"]*)\"$")
    public void enterANumberAndTheScale(final String arg1, final String arg2) throws Throwable {
        Marker round = math.round( Integer.parseInt(arg2), getMarker(arg1), markerFactory);
        resultStr = round.toString();
    }

    @When("^enter two equal numbers \"([^\"]*)\"  \"([^\"]*)\"$")
    public void enterTwoEqualNumbers(final String arg1, final String arg2) throws Throwable {
        result = math.eq( getMarker(arg1),  getMarker(arg2), markerFactory);
    }

    @When("^enter a decimal number \"([^\"]*)\" to extract decimal part of the number$")
    public void enterADecimalNumberToExtractDecimalPartOfTheNumber(final String arg1) throws Throwable {
         Marker decimal = math.extractDecimalFractionPart( getMarker(arg1), markerFactory);
         byte[] data = (decimal.getData() == null)?arg1.getBytes():decimal.getData();
         resultStr = decimal.toString();
    }

    @When("^enter a decimal number \"([^\"]*)\" to extract integer part of the number$")
    public void enterADecimalNumberToExtractIntegerPartOfTheNumber(final String arg1) throws Throwable {
        resultStr = math.extractDecimalIntegerPart( getMarker(arg1), markerFactory).toString();
    }

    @When("^enter a number string \"([^\"]*)\"$")
    public void enterANumberString(final String arg1) throws Throwable {
        result = math.isNumber( getMarker(arg1), markerFactory);
    }
    
    @When("^min of \"([^\"]*)\"  \"([^\"]*)\"$")
    public void minOf(@Transform(MarkerTransform.class) final MarkerHelper arg1, @Transform(MarkerTransform.class) final MarkerHelper arg2) throws Throwable {   
    	Marker m1 = arg1.getMarker(markerFactory);
    	Marker m2=arg2.getMarker(markerFactory);
    	Marker min = math.min( m1,  m2, markerFactory);
    	if(min == m1){
    		resultStr =m1.toString();	
    	}else if (min == m2){
    		resultStr =m2.toString();	
    	}
    }

    @Then("^min number is \"([^\"]*)\"$")
    public void minNumberIs(String arg1) throws Throwable {
    	 assertEquals(arg1,resultStr);
    }

    @When("^max of \"([^\"]*)\"  \"([^\"]*)\"$")
    public void maxOf(@Transform(MarkerTransform.class) final MarkerHelper arg1, @Transform(MarkerTransform.class) final MarkerHelper arg2) throws Throwable {
    	Marker m1 = arg1.getMarker(markerFactory);
    	Marker m2=arg2.getMarker(markerFactory);
    	Marker max = math.max( m1,  m2, markerFactory);
    	if(max == m1){
    		resultStr =m1.toString();	
    	}else if (max == m2){
    		resultStr =m2.toString();	
    	}    }

    @Then("^max number is \"([^\"]*)\"$")
    public void maxNumberIs(String arg1) throws Throwable {
    	  assertEquals(arg1,resultStr);
    }
}
