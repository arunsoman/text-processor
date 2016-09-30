package com.flytxt.parser.test.translator;

import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.test.translator.transformer.MarkerHelper;
import com.flytxt.parser.test.translator.transformer.MarkerTransform;
import com.flytxt.parser.test.translator.transformer.StringHelper;
import com.flytxt.parser.test.translator.transformer.StringTransform;
import com.flytxt.parser.translator.TpString;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TpStringFeature {

    MarkerFactory mf;

    Marker marker1;

    Marker marker2;

    boolean result;

    String resultStr;

    TpString tpString;

    private Marker getMarker(final String str) {
        final Marker mocker = new Marker();
        mocker.index = 0;
        mocker.length = str.length();
        return mocker;
    }

    @Given("^marker and tpstring class$")
    public void markerAndTpstringClass() throws Throwable {
        mf = new MarkerFactory();
        tpString = new TpString();
    }

    @When("^\"([^\"]*)\" provided$")
    public void provided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = "" + tpString.length(mh.getBytes(), mh.getMarker(), mh.getMf());
    }

    @Then("^check string result \"([^\"]*)\"$")
    public void returnLength(final String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @When("^\"([^\"]*)\" starts with prefix \"([^\"]*)\"$")
    public void startsWithPrefix(final String arg1, final String arg2) throws Throwable {
        result = tpString.startsWtih(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2));
    }

    @Then("^check boolean result \"([^\"]*)\"$")
    public void returnTrueIfStringStartsWithPrefix(final String arg1) throws Throwable {
        assertEquals(arg1.equals("Y"), result);
    }

    @When("^convert to upperCase\"([^\"]*)\"$")
    public void convertToUpperCase(final String arg1) throws Throwable {
        resultStr = tpString.toUpperCase(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^convert to lowerCase \"([^\"]*)\"$")
    public void convertToLowerCase(final String arg1) throws Throwable {
        resultStr = tpString.toLowerCase(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^conver to titleCase\"([^\"]*)\"$")
    public void converToTitleCase(final String arg1) throws Throwable {
        resultStr = tpString.toTitleCase(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
    }

    @When("^\"([^\"]*)\" with leading whitespace is provided$")
    public void withLeadingWhitespaceIsProvided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = tpString.lTrim(mh.getBytes(), mh.getMarker(), mh.getMf()).toString(mh.getBytes());
    }

    @Then("^returns string without leading whitespaces \"([^\"]*)\"$")
    public void returnsStringWithoutLeadingWhitespaces(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" with trailing whitespaces provided$")
    public void withTrailingWhitespacesProvided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = tpString.rTrim(mh.getBytes(), mh.getMarker(), mh.getMf()).toString(mh.getBytes());
    }

    @Then("^return string without trailing whilespaces \"([^\"]*)\"$")
    public void returnStringWithoutTrailingWhilespaces(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" with whitespace in beginning or end is provided$")
    public void withWhitespaceInBeginningOrEndIsProvided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = tpString.trim(mh.getBytes(), mh.getMarker(), mh.getMf()).toString(mh.getBytes());
    }

    @Then("^return string with no whitespaces at start or end \"([^\"]*)\"$")
    public void returnStringWithNoWhitespacesAtStartOrEnd(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\"$")
    public void contains(final String arg1, final String arg2) throws Throwable {
        result = tpString.contains(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\" ignore case$")
    public void containsIgnoreCase(final String arg1, final String arg2) throws Throwable {
        result = tpString.containsIgnoreCase(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2), mf);
    }

    @When("^convert to upperCase(.+)$")
    public void convert_to_uppercase(final String string) throws Throwable {
        throw new PendingException();
    }

    @When("^convert to lowerCase (.+)$")
    public void convert_to_lowercase(final String string) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\"   $")
    public void something_contains_something(final String string, final String substring, final String strArg1, final String strArg2) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\" ignore case  $")
    public void something_contains_something_ignore_case(final String string, final String substring, final String strArg1, final String strArg2) throws Throwable {
        throw new PendingException();
    }

}
