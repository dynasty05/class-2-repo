package stepsdefinition;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ribake on 03/03/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:featurefiles"},
                 plugin = {"pretty",
                           "html:target/cucumber-html-report.html",
                           "json:target/cucumber-json-report.json"})
public class RunCukesTest {
}
