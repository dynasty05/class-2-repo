package step_definitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;


/**
 * Created by ribake on 28/01/2018.
 */
public class HtmlLocators {
    private WebDriver driver = Hooks.driver;

    @When("^I login to practiceselenium website$")
    public void i_login_to_practiceselenium_website() throws Throwable {
        driver.get("http://www.practiceselenium.com/");
        assertEquals("Welcome", driver.getTitle());
    }

    @Then("^I access elements and use watir commands$")
    public void i_access_elements_and_use_watir_commands() throws Throwable {
        WebElement welcomeLink = driver.findElement(By.linkText("Welcome"));

        System.out.println("Welcome text "+welcomeLink.getText());
        System.out.println("Tag Name "+welcomeLink.getAttribute("tagName"));
        System.out.println("Href "+welcomeLink.getAttribute("href"));
        System.out.println("Outer HTML "+welcomeLink.getAttribute("outerHTML"));
        System.out.println("Inner HTML " + welcomeLink.getAttribute("innerHTML"));
    }

}
