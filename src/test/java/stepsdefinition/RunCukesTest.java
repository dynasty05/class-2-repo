package stepsdefinition;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ribake on 15/02/2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
                  glue = {"classpath:stepsdefinition"},
                  plugin = {"pretty", "html:target/cucumber-html-reports", "json:target/cucumber-report.json"})
public class RunCukesTest {
}
