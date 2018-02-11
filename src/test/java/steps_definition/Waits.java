package steps_definition;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    @When("^I set implicit timeout$")
    public void i_set_implicit_timeout() throws Throwable {
        // Implicit wait is the global timeout for every element that driver waits on
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        timeouts.implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Then("^I print the text for target element$")
    public void i_print_the_text_for_target_element() throws Throwable {
        WebElement targetElement = driver.findElement(By.id("periodicElement"));
        System.out.println(targetElement.getText());
    }

    @When("^I set script timeout$")
    public void i_set_script_timeout() throws Throwable {
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        // Script timeout is the timeout webdriver gives for javascript calls in the browser to return.
        // Below code shows timeout for a synchronous - simultaneous call.
        // For an asynchronous call example, see executAsyncScript section at http://seleniumhq.github.io/selenium/docs/api/java/index.html
        timeouts.setScriptTimeout(5, TimeUnit.SECONDS);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        Object result = javascriptExecutor.executeScript("return window.onload='myFunction()';");
        System.out.println("Javascript execution result "+ (String)result);

    }

    @Then("^I set explicit wait block on target element until it becomes clickable$")
    public void i_set_explicit_wait_block_on_target_element_until_it_becomes_clickable() throws Throwable {
        // Explicit timeout allows us to cause driver to wait on for an expected
        // condition on a element by element basis. Best practice as implicit waits
        // apply to every element regardless of the wait necessary on it.
        // Here goes:

        // The wait object initialised with the timeout and driver
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // waiter repeatedly waits for the expected condition to become true for 5 seconds
        // failing which it throws a TimeoutException.
        WebElement periodicElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("periodicElement")));
        String elementHTML = periodicElement.getAttribute("outerHTML");
        System.out.println("Explicit wait found element with HTML: " + elementHTML);
    }

}
