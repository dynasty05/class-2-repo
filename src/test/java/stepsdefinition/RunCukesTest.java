package stepsdefinition;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ribake on 21/02/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
                 plugin = {"pretty", "json:target/cucumber-report.json", "html:target/cucumber-html-reports"})
public class RunCukesTest {
}
