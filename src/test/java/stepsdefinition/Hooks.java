package stepsdefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ribake on 21/02/2018.
 */
public class Hooks {
    public static WebDriver driver;
    static {
        /* Moved setting this property to static init block so property can be set once and for
           when class is loaded */
        System.setProperty("webdriver.chrome.driver", "/Users/user/Installations/chromedriver");
    }

    // @Before here comes from cucumber-java and not cucumber-junit
    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void openBrowser(){
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        System.out.println("Called Open browser and deleted all cookies");
    }

    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario){
        if(scenario.isFailed()){

            try {
                scenario.write("Scenario failed. Capturing sc");
                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");

            } catch (WebDriverException w){
                System.err.println(w.getMessage());
            }
        }

        driver.quit();
    }
}
