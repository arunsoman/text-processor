package features;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

public class TpStringFeatureTest {


    @Given("^marker and tpstring class $")
    public void marker_and_tpstring_class() throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" provided$")
    public void something_provided(String string1, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" starts with prefix \"([^\"]*)\"$")
    public void something_starts_with_prefix_something(String string1, String string2, String strArg1, String strArg2) throws Throwable {
        throw new PendingException();
    }

    @When("^convert to upperCase(.+)$")
    public void convert_to_uppercase(String string) throws Throwable {
        throw new PendingException();
    }

    @When("^convert to lowerCase (.+)$")
    public void convert_to_lowercase(String string) throws Throwable {
        throw new PendingException();
    }

    @When("^conver to titleCase\"([^\"]*)\"$")
    public void conver_to_titlecasesomething(String string, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" with leading whitespace is provided$")
    public void something_with_leading_whitespace_is_provided(String string, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" with trailing whitespaces provided$")
    public void something_with_trailing_whitespaces_provided(String string, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" with whitespace in beginning or end is provided$")
    public void something_with_whitespace_in_beginning_or_end_is_provided(String string, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\"   $")
    public void something_contains_something(String string, String substring, String strArg1, String strArg2) throws Throwable {
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" contains \"([^\"]*)\" ignore case  $")
    public void something_contains_something_ignore_case(String string, String substring, String strArg1, String strArg2) throws Throwable {
        throw new PendingException();
    }

    @Then("^return length \"([^\"]*)\"$")
    public void return_length_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^return true if string starts with prefix\"([^\"]*)\"$")
    public void return_true_if_string_starts_with_prefixsomething(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^returns upperCase \"([^\"]*)\"$")
    public void returns_uppercase_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^returns lowerCase \"([^\"]*)\"$")
    public void returns_lowercase_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^returns titleCase \"([^\"]*)\"$")
    public void returns_titlecase_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^returns string without leading whitespaces \"([^\"]*)\"$")
    public void returns_string_without_leading_whitespaces_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^return string without trailing whilespaces \"([^\"]*)\"$")
    public void return_string_without_trailing_whilespaces_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^return string with no whitespaces at start or end \"([^\"]*)\"$")
    public void return_string_with_no_whitespaces_at_start_or_end_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^return true if string contais subString\"([^\"]*)\"$")
    public void return_true_if_string_contais_substringsomething(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^return true if string contais subString ignoreCase \"([^\"]*)\"$")
    public void return_true_if_string_contais_substring_ignorecase_something(String result, String strArg1) throws Throwable {
        throw new PendingException();
    }

}