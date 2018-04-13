package stepsdefinition;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ribake on 25/03/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:featurefiles"},
                  plugin = {"pretty", "html:target/cucumber-html-report.html", "json:target/cucumber-json-report.json"})
// No need to add "glue" option if RunCukesTest is in the same directory as the steps file.
public class RunCukesTest {
}
