package com.clicktripz.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.ITestContext;

/**
 * FlightsPage.java - a helper class for managing Clicktripz Flights page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class FlightsPage extends BasePage {

    // Fields
    @FindBy(id="city1")
    private WebElement fromField;
    @FindBy(id="city2")
    private WebElement toField;
    @FindBy(id="travelers")
    private WebElement travelersDropdown;

    // Controls
    @FindBy(xpath="//table[@class='ui-datepicker-calendar']//a[contains(@class,'ui-state-active')]")
    private WebElement datePickerActiveDay;

    // Buttons
    @FindBy(id="search-button")
    private WebElement searchButton;

    public FlightsPage(WebDriver driver, ITestContext tc){
        super(driver, tc);
    }

    // Get page title
    public String getPageTitle(){
        return driver.getTitle();
    }

    // Set from airport field
    public void setFromAirport(String strAirport){
        wait.until(d -> fromField.isDisplayed());
        fromField.clear();
        fromField.sendKeys(strAirport);
    }

    // Set to airport field
    public void setToAirport(String strAirport){
        wait.until(d -> toField.isDisplayed());
        toField.clear();
        toField.sendKeys(strAirport);
    }

    // Set number of travelers in dropdown
    public void setTravelers(String strCity){
        wait.until(d -> travelersDropdown.isDisplayed());
        travelersDropdown.sendKeys(strCity);
    }
}