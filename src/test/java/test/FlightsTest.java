package test;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.FlightsPage;

/**
 * FlightsTest.java - a simple class for testing the Clicktripz Flights search.
 * @author  Maciej Kocol
 * @version 1.0
 * @see pages.FlightsPage
 */
public class FlightsTest extends ExitUnitTest{
    FlightsPage flightsPage;

    @BeforeMethod

    public void _setup(){
        driver.get("https://www.clicktripz.com/test_flights.php");
    }

    /**
     * This test case will navigate to https://www.clicktripz.com/test_flights.php
     * Verify Flights page
     * @throws Exception
     */
    @Test(priority = 0, groups = "search", dataProvider = "tc")
    public void test_Flights(ITestContext tc) throws Exception{

        // Create Hotel Citywide Page object
        flightsPage = new FlightsPage(driver, tc);

        // Verify page title
        Assert.assertTrue(flightsPage.getPageTitle().contains("Flights Test Form - Clicktripz"));

        // Select Flights tab
        flightsPage.selectFlightsTab();

        // Set from airport
        flightsPage.setFromAirport("ORD");

        // Set to airport
        flightsPage.setToAirport("BKK");

        // Set departure date
        flightsPage.setDate1(flightsPage.getNextWeeksDateFor("Friday"));

        // Set arrival date
        flightsPage.setDate2(flightsPage.getNextWeeksDateFor("Sunday"));

        // Set number of travelers
        flightsPage.setTravelers("2");

        // Click the search button
        flightsPage.clickSearchButton();

        // Switch to main window
        flightsPage.switchToWindow("Flights Test Form - Clicktripz");

        // Minimize main window
        flightsPage.minimizeWindow();

    }
}
