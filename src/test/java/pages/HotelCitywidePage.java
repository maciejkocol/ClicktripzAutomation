package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.ITestContext;

/**
 * HotelCitywidePage.java - a helper class for managing Clicktripz Hotel Citywide page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class HotelCitywidePage extends BasePage {

    // Fields
    @FindBy(id="city")
    private WebElement cityField;
    @FindBy(id="guests")
    private WebElement guestsDropdown;
    @FindBy(id="rooms")
    private WebElement roomsDropdown;

    public HotelCitywidePage(WebDriver driver, ITestContext tc){
        super(driver, tc);
    }

    // Set city name in field
    public void setCity(String strCity){
        wait.until(d -> cityField.isDisplayed());
        cityField.clear();
        cityField.sendKeys(strCity);
    }

    // Set number of guests
    public void setGuests(String strNumber){
        wait.until(d -> guestsDropdown.isDisplayed());
        guestsDropdown.sendKeys(strNumber);
    }

    // Set number of rooms
    public void setRooms(String strNumber){
        wait.until(d -> roomsDropdown.isDisplayed());
        roomsDropdown.sendKeys(strNumber);
    }

    // Get number of rooms
    public String getRooms(){
        wait.until(d -> roomsDropdown.isDisplayed());
        return roomsDropdown.getAttribute("value");
    }
}