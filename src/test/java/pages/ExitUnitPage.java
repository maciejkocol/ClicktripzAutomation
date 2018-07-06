package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.ITestContext;

/**
 * ExitUnitPage.java - a helper class for managing Clicktripz Exit Unit modal page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class ExitUnitPage extends BasePage{

    //Page buttons
    @FindBy(xpath="//a[contains(@class,'button') and text()='Show me the first deal!']")
    private WebElement showMeDealButton;

    //Page tabs
    @FindAll(@FindBy(xpath="//div[@id='ct-header']/ul[starts-with(@class,'tabs')]/li[starts-with(@class,'tab')]"))
    private List<WebElement> travelTabs;
    private By filterByAdvertiser = new By.ByXPath(".//div[@class='tab-wrapper']/a[@data-tab-advertiser-name]");
    private String advertiserNameAttribute = "data-tab-advertiser-name";
    private String initialTab = "active";
    private String checkedTab = "visited";

    public ExitUnitPage(WebDriver driver, ITestContext tc){
        super(driver, tc);
    }

    // Click the show me deal button
    public void clickShowMeDealButton(){
        wait.until(d -> showMeDealButton.isDisplayed());
        showMeDealButton.click();
    }

    /**
     * This method clicks on each travel tab present and takes a screenshot when done loading.
     */
    public void traverseTravelTabs() throws Exception {
        for (WebElement tab : travelTabs) {
            if (tab.getAttribute("class").toLowerCase().contains(initialTab))
                continue;
            tab.click();
            captureSreenshot(tab.findElement(filterByAdvertiser).getAttribute(advertiserNameAttribute).replaceAll("\\p{Z}","") + "Tab");
        }
    }

    /**
     * This method counts the number of checked travel tabs.
     * @return Total number of checked tabs
     */
    public String tabsCheckedCount() {
        int count = 0;
        for (WebElement tab : travelTabs) {
            if (tab.getAttribute("class").toLowerCase().contains(checkedTab)) {
                count++;
            }
        }
        return Integer.toString(count);
    }
}
