package stepsdefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Set;

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

        driver.close();
    }
}
