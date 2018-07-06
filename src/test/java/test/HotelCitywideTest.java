package test;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HotelCitywidePage;

/**
 * HotelCitywideTest.java - a simple class for testing the Clicktripz Hotel Citywide search.
 * @author  Maciej Kocol
 * @version 1.0
 * @see pages.HotelCitywidePage
 */
public class HotelCitywideTest extends ExitUnitTest{
    HotelCitywidePage hotelCitywidePage;

    @BeforeMethod

    public void _setup() {
        driver.get("https://www.clicktripz.com/test.php");
    }

    /**
     * This test case will navigate to https://www.clicktripz.com/test.php
     * Verify Hotel Citywide page
     * @throws Exception
     */
    @Test(priority = 0, groups = "search", dataProvider = "tc")
    public void test_Hotel_Citywide(ITestContext tc) throws Exception{

        // Create Hotel Citywide Page object
        hotelCitywidePage = new HotelCitywidePage(driver, tc);

        // Verify page title
        Assert.assertTrue(hotelCitywidePage.getPageTitle().contains("Hotel Citywide Test Form - Clicktripz"));

        // Select Hotel Citywide tab
        hotelCitywidePage.selectHotelCitywideTab();

        // Set city name
        hotelCitywidePage.setCity("Chicago, IL");

        // Set check-in date
        hotelCitywidePage.setDate1(hotelCitywidePage.getNextWeeksDateFor("Friday"));

        // Set check-out date
        hotelCitywidePage.setDate2(hotelCitywidePage.getNextWeeksDateFor("Sunday"));

        // Set number of guests
        hotelCitywidePage.setGuests("2");

        // Verify preselected number of rooms
        Assert.assertEquals(hotelCitywidePage.getRooms(), "1");

        // Click the search button
        hotelCitywidePage.clickSearchButton();

        // Switch to main window
        hotelCitywidePage.switchToWindow("Hotel Citywide Test Form - Clicktripz");

        // Minimize main window
        hotelCitywidePage.minimizeWindow();

    }
}
