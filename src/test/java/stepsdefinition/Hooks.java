package stepsdefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

/**
 * Created by ribake on 03/03/2018.
 */
public class Hooks {
    public static WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", "/Users/user/Installations/chromedriver");
    }

    @Before("@web")
    public void before()throws MalformedURLException {
        System.out.println("Called openBrowser");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
    }

    @After("@web")
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + driver.getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }

        }
        driver.quit();
    }

}
