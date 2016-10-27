package com.flytxt.parser.translator.feature;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.translator.TpString;
import com.flytxt.parser.translator.feature.transformer.MarkerHelper;
import com.flytxt.parser.translator.feature.transformer.MarkerTransform;
import com.flytxt.parser.translator.feature.transformer.StringHelper;
import com.flytxt.parser.translator.feature.transformer.StringTransform;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TpStringFeature {

	@Autowired
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
        resultStr = "" + tpString.length( mh.getMarker(mf));
    }

    @Then("^check string result \"([^\"]*)\"$")
    public void returnLength(String arg1) throws Throwable {
        assertEquals(arg1, resultStr);
    }

    @Then("^trimmed string \"([^\"]*)\"$")
    public void trimmedString(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" starts with prefix \"([^\"]*)\"$")
    public void startsWithPrefix(final String arg1, final String arg2) throws Throwable {
        result = tpString.startsWith( getMarker(arg1),  getMarker(arg2));
    }

    @Then("^check boolean result \"([^\"]*)\"$")
    public void returnTrueIfStringStartsWithPrefix(final String arg1) throws Throwable {
        assertEquals(arg1.equals("Y"), result);
    }

    @When("^convert to upperCase\"([^\"]*)\"$")
    public void convertToUpperCase(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        Marker upperCase = tpString.toUpperCase(mh.getMarker(mf), mf);
        resultStr = upperCase.toString();
    }

    @When("^convert to lowerCase \"([^\"]*)\"$")
    public void convertToLowerCase(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        Marker upperCase = tpString.toLowerCase(mh.getMarker(mf), mf);
        resultStr = upperCase.toString();
    }

    @When("^conver to titleCase\"([^\"]*)\"$")
    public void converToTitleCase(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        Marker upperCase = tpString.toTitleCase(mh.getMarker(mf), mf);
        resultStr = upperCase.toString();
    }

    @When("^\"([^\"]*)\" with leading whitespace is provided$")
    public void withLeadingWhitespaceIsProvided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = tpString.lTrim( mh.getMarker(mf), mf).toString();
    }

    @Then("^returns string without leading whitespaces \"([^\"]*)\"$")
    public void returnsStringWithoutLeadingWhitespaces(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" with trailing whitespaces provided$")
    public void withTrailingWhitespacesProvided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = tpString.rTrim( mh.getMarker(mf), mf).toString();
    }

    @Then("^return string without trailing whilespaces \"([^\"]*)\"$")
    public void returnStringWithoutTrailingWhilespaces(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" with whitespace in beginning or end is provided$")
    public void withWhitespaceInBeginningOrEndIsProvided(@Transform(MarkerTransform.class) final MarkerHelper mh) throws Throwable {
        resultStr = tpString.trim( mh.getMarker(mf), mf).toString();
    }

    @Then("^return string with no whitespaces at start or end \"([^\"]*)\"$")
    public void returnStringWithNoWhitespacesAtStartOrEnd(@Transform(StringTransform.class) final StringHelper arg1) throws Throwable {
        assertEquals(arg1.getStr(), resultStr);
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\"$")
    public void contains(@Transform(MarkerTransform.class) final MarkerHelper mh, @Transform(MarkerTransform.class) final MarkerHelper mh2) throws Throwable {
        result = tpString.contains(mh.getMarker(mf), mh2.getMarker(mf));
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\" ignore case$")
    public void containsIgnoreCase(@Transform(MarkerTransform.class) final MarkerHelper mh1, @Transform(MarkerTransform.class) final MarkerHelper mh2) throws Throwable {
        result = tpString.containsIgnoreCase( mh1.getMarker(mf), mh2.getMarker(mf));
    }

    @When("^\"([^\"]*)\" endsWith \"([^\"]*)\"$")
    public void endswith(@Transform(MarkerTransform.class) final MarkerHelper mh, @Transform(MarkerTransform.class) final MarkerHelper mh2) throws Throwable {
        result = tpString.endsWith( mh.getMarker(mf),  mh2.getMarker(mf));
    }

    @When("^\"([^\"]*)\" endsWith \"([^\"]*)\" ignore case$")
    public void endswithIgnoreCase(@Transform(MarkerTransform.class) final MarkerHelper mh, @Transform(MarkerTransform.class) final MarkerHelper mh2) throws Throwable {
        result = tpString.endsWithIgnore( mh.getMarker(mf),  mh2.getMarker(mf));
    }

    @When("^\"([^\"]*)\" is given and \"([^\"]*)\" chars to be extracted from head$")
    public void isGivenAndCharsToBeExtractedFromHead(@Transform(MarkerTransform.class) final MarkerHelper mh, String n) throws Throwable {
        try {
            Marker resultM = tpString.extractLeading( mh.getMarker(mf), Integer.parseInt(n), mf);
            resultStr = resultM.toString();
        } catch (RuntimeException e) {
            resultStr = "";
        }
    }

    @When("^\"([^\"]*)\" is given and \"([^\"]*)\" chars to be extracted from trail$")
    public void isGivenAndCharsToBeExtractedFromTrail(@Transform(MarkerTransform.class) final MarkerHelper mh, String n) throws Throwable {
        try {
            Marker extractTrailing = tpString.extractTrailing( mh.getMarker(mf), Integer.parseInt(n), mf);
            resultStr = extractTrailing.toString();

        } catch (RuntimeException e) {
            resultStr = "";
        }
    }

    @When("^two markers has to be merged \"([^\"]*)\" and \"([^\"]*)\"$")
    public void twoMarkersHasToBeMergedAnd(@Transform(MarkerTransform.class) final MarkerHelper mh, @Transform(MarkerTransform.class) final MarkerHelper mh2) throws Throwable {
        Marker merge = tpString.merge( mh.getMarker(mf),  mh2.getMarker(mf), mf);
        resultStr = merge.toString();
    }
}
