package step_definitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ribake on 24/01/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
                 plugin = {"pretty", "html:target/cucumber-html-reports", "json:target/cucumber.json" })
public class RunCukesTest {
}
