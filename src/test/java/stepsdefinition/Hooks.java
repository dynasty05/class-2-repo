package stepsdefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ribake on 15/02/2018.
 */
public class Hooks {
    private static WebDriver driver;

    static {
        // initialise browser executable
        System.setProperty("web.driver.chromedriver", "~/Installations/chromedriver");
    }

    public static WebDriver getDriver(){
        return driver;
    }


    @Before
    public void before(){
        // instantiate a new WebDriver for new Scenario
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
    }


    @After
    public void after(Scenario scenario){
        if(scenario.isFailed()){
            byte[] image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(image, "img/png");
        }

        // invalidate the WebDriver instance for Scenario just concluded
        driver.quit();
    }
}
