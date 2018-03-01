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
 * Created by ribake on 01/03/2018.
 */
public class Hooks {
    public static WebDriver driver;
    static {
        System.setProperty("webdriver.chrome.driver",
                            "/Users/user/Installations/chromedriver");
    }

    @Before
    public void setUpTestcase(){
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();

    }

    @After
    public void tearDownTestcase(Scenario scenario){
        if(scenario.isFailed()){
            try {
                scenario.write("Scenario failed. Capturing sc");
                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");

            } catch (WebDriverException w){
                System.err.println(w.getMessage());
            }
        }
        driver.close();
    }
}
