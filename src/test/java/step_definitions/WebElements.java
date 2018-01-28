package step_definitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ribake on 24/01/2018.
 */
public class WebElements {
    WebDriver driver = Hooks.driver;

    @When("^I open practiceselenium\\.com website$")
    public void i_open_practiceselenium_com_website() throws Throwable {
        driver.get("http://www.practiceselenium.com/practice-form.html");
        assertEquals("practice-form", driver.getTitle());
    }

    @Then("^I find first and last name and print the html$")
    public void i_find_first_and_last_name_and_print_the_html() throws Throwable {
        WebElement firstName = driver.findElement(By.name("firstname"));
        WebElement lastName = driver.findElement(By.name("lastname"));

        System.out.println("Firstname html "+ firstName.getAttribute("outerHTML"));
        System.out.println("Lastname html "+ lastName.getAttribute("outerHTML"));

    }

    @Then("^I find menu and print the html$")
    public void i_find_menu_and_print_the_html() throws Throwable {
        WebElement menuElement = driver.findElement(By.linkText("Menu"));
        System.out.println("Menu html: " + menuElement.getAttribute("outerHTML"));
    }

    @Then("^I find button and print the html$")
    public void i_find_button_and_print_the_html() throws Throwable {
        WebElement okButton = driver.findElement(By.id("submit"));
        System.out.println("Button html"+ okButton.getAttribute("outerHTML"));
    }

    @Then("^I find radio button male and print the html$")
    public void i_find_radio_button_male_and_print_the_html() throws Throwable {
        WebElement maleRadio = driver.findElement(By.id("sex-0"));
        WebElement femaleRadio = driver.findElement(By.id("sex-1"));

        System.out.println("Male Radio "+maleRadio.getAttribute("outerHTML"));
        System.out.println("FeMale Radio "+femaleRadio.getAttribute("outerHTML"));
    }

    @Then("^I find check box and print the html$")
    public void i_find_check_box_and_print_the_html() throws Throwable {
        WebElement blackTea = driver.findElement(By.id("tea1"));
        WebElement redTea = driver.findElement(By.id("tea2"));
        WebElement oolongTea = driver.findElement(By.id("tea3"));

        System.out.println("FeMale Radio "+blackTea.getAttribute("outerHTML"));
        System.out.println("FeMale Radio "+redTea.getAttribute("outerHTML"));
        System.out.println("FeMale Radio "+oolongTea.getAttribute("outerHTML"));

    }

    @Then("^I find select list and print html$")
    public void i_find_select_list_and_print_html() throws Throwable {
        Select continenetSelect = new Select(driver.findElement(By.id("continents")));
        List<WebElement> options = continenetSelect.getOptions();

        System.out.println("Printing all options....");

        for(WebElement w:options){
            System.out.println(w.getText());
        }

    }

    @Then("^I find another select list and print html$")
    public void i_find_another_select_list_and_print_html() throws Throwable {
        Select anotherSelectList = new Select(driver.findElement((By.id("selenium_commands"))));

        List<WebElement> options = anotherSelectList.getOptions();
        System.out.println("Printing all options....");
        for(WebElement e:options)
        {
            System.out.println(e.getText());
        }
    }

    @Then("^I find div and print html$")
    public void i_find_div_and_print_html() throws Throwable {
        WebElement divElement = driver.findElement(By.className("wsb-htmlsnippet-element"));
        System.out.println("Div html "+ divElement.getAttribute("outerHTML"));
    }

}
