package stepsdefinition;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ribake on 01/03/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
                 plugin = {"pretty", "html:reports/cucumber-html-reports.html", "json:reports/cucumber-json-reports.json"},
                 tags = {"~@parallel"})
public class RunCukesTest {
}
