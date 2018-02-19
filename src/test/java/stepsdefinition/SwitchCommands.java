package stepsdefinition;

import com.thoughtworks.selenium.Wait;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.nio.channels.Pipe;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by ribake on 15/02/2018.
 */
public class SwitchCommands {
    private WebDriver driver = Hooks.getDriver();

    @When("^I open seleniumframework practiceform$")
    public void i_open_seleniumframework_practiceform() throws Throwable {
        driver.get("http://www.seleniumframework.com/Practiceform/");
    }

    @When("^I open a new window$")
    public void i_open_a_new_window() throws Throwable {
        String handle = driver.getWindowHandle();
        // Returns something like - CDwindow-(5A817E1E42658526F4EADBB827C5CEA)
        System.out.println("Parent Window Handle: " + handle);

        // click on a button that opens another browser window
        System.out.println("Opening another window");
        driver.findElement(By.id("button1")).click();

        Set<String> handles = driver.getWindowHandles();
        System.out.println("Number of window handles: " + handles.size());
        for(String s: handles){
            System.out.println("Current Window handle: " + s);
        }
    }

    @Then("^I switch to the new window$")
    public void i_switch_to_the_new_window() throws Throwable {
        for(String s: driver.getWindowHandles()){
            driver.switchTo().window(s);
            System.out.println("Current window " + s + " has title "+ driver.getTitle());
        }
        // close the latest window
        driver.close();
    }

    @When("^I open a new tab$")
    public void i_open_a_new_tab() throws Throwable {
        String currentHandle = driver.getWindowHandle();
        System.out.println("Current Window Handle " + currentHandle);

        /* Add a wait to fix instability issue with buttons not loading before
          code reaches this point */
        // Option 1: Wait for button using the global implicit wait
        // driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Option 2: Wait for button using an explicit wait on the button
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement newTabButton = wait.until(ExpectedConditions.visibilityOf(driver.findElements(
                                                                                By.tagName("button")).get(2)));
        newTabButton.click();

//        List<WebElement> allButtons = driver.findElements(By.tagName("button"));
//        /* System.out.println("All buttons in DOM: " + allButtons.size());
//        for (int i = 0; i < allButtons.size(); i++){
//            System.out.println("Button " + (i + 1) + allButtons.get(i).getText());
//        } */
//        // this opens an additional tab - which is also seen as a window
//
//        allButtons.get(2).click();

        // get handles of all open windows
        Set<String> handles = driver.getWindowHandles();
        if(handles.size() > 1) System.out.println("New tab opened");

        for (String h : handles){
            System.out.println("Open window " + h);
        }

    }

    @Then("^I switch to the new tab$")
    public void i_switch_to_the_new_tab() throws Throwable {
        // Switch WebDriver to the newly open tab
        String currentHandle = driver.getWindowHandle();
        for (String h : driver.getWindowHandles()){
            if (!h.equals(currentHandle)){
                driver.switchTo().window(h);
            }
        }
        // assert on the page title of the new tab
        Assert.assertEquals(driver.getTitle(), "Selenium Framework | Selenium, Cucumber, Ruby, Java et al.");

    }
}
