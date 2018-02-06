package steps_definition;

import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Created by ribake on 06/02/2018.
 */
public class Waits {
    WebDriver driver = Hooks.driver;

    @When("^I open seleniumframework website$")
    public void i_open_seleniumframework_website() throws Throwable {
        driver.get("http://www.seleniumframework.com/Practiceform/");

    }

    @When("^I set pageload timeout$")
    public void i_set_pageload_timeout() throws Throwable {
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        // Note: default pageload timeout is infinite
        timeouts.pageLoadTimeout(10, TimeUnit.SECONDS);

        // use the timeout configured
        WebElement target = driver.findElement(By.id("periodicElement"));
        System.out.println("Target element: " + target.getText());
    }

}
