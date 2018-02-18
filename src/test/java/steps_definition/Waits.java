package steps_definition;

import com.google.common.base.Function;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
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

        // The wait object initialised with the timeout and driver. Similar to XCTWaiter in XCTest.
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // waiter repeatedly waits for the expected condition to become true for 5 seconds
        // failing which it throws a TimeoutException. Expected Condition is like Expectations in XCTest.
        WebElement periodicElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("periodicElement")));
        String elementHTML = periodicElement.getAttribute("outerHTML");
        System.out.println("Explicit wait found element with HTML: " + elementHTML);
    }

    @Then("^I set explicit wait block on on alert present$")
    public void i_set_explicit_wait_block_on_on_alert_present() {
        // Initialise wait object
        WebDriverWait wait = new WebDriverWait(driver, 5);

        // invoke the action that causes an asynchronous call.
        driver.findElement(By.id("timingAlert")).click();

        // Wait until an expected condition. Note: an Alert is not a WebElement.
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("Found alert");

        // dismiss the alert
        alert.accept();
    }

    @Then("^I wait until I find five periodicElements$")
    public void i_wait_until_I_find_five_periodicElements() {
        // Instantiate the fluent wait that is tied to a driver type
        FluentWait<WebDriver> flexibleWait = new FluentWait<WebDriver>(driver);
        // set the timeout and polling interval on the wait object.
        // Note: like webdriver wait, fluent wait needs a driver object and a timeout.
        flexibleWait.withTimeout(30, TimeUnit.SECONDS);
        flexibleWait.pollingEvery(2, TimeUnit.SECONDS);

        // set the exceptions to be ignored during the wait
        flexibleWait.ignoring(NoSuchElementException.class);
        flexibleWait.ignoring(StaleElementReferenceException.class);

        // specify the expected condition or function. Wait stops when function returns a non-null
        // object or timeout is reached. The object returned [must] be the object being waited for.
        // The Function spells out the expectation.
        flexibleWait.until(new Function<WebDriver, List<WebElement>>(){
            // logic of fulfilled expectations goes here
            public List<WebElement> apply(WebDriver d) {
                // the exact UI element(s) we want to wait for.
                List<WebElement> elements = driver.findElements(By.id("periodicElement"));

                // expectation is fulfilled if there are 5 elements in the list
                if(elements.size() == 5){
                    System.out.println("Target length is reached");
                    return elements;

                } else {
                    System.out.println("Not reached target length. Continue waiting ...");
                    return null;
                }
            }
        });



    }

}
