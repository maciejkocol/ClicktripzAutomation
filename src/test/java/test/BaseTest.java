package test;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;

/**
 * BaseTest.java - a base class for initializing webdriver.
 * @author  Maciej Kocol
 * @version 1.0
 * @see pages.BasePage
 */
public class BaseTest {
    protected WebDriver driver;

    @BeforeTest
    @Parameters("state")

    public void setup(@Optional("") String state) throws IOException {
        if (state.contains("headless")) {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setBrowserName("chrome");
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("marionette", true);
            chromeOptions.addArguments("no-sandbox");
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
            driver = new ChromeDriver(chromeOptions);
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest(dependsOnGroups = {"search", "exit"})

    public void teardown(){
        WebDriver popup = null;
        Iterator<String> windowIterator = driver.getWindowHandles().iterator();
        while(windowIterator.hasNext()) {
            String windowHandle = windowIterator.next();
            popup = driver.switchTo().window(windowHandle);
            popup.close();
        }
        driver.quit();
    }

    @DataProvider(name = "tc")
    protected Object[][] dp(ITestContext tc) {
        return new Object[][] {
                { tc }
        };
    }
}
