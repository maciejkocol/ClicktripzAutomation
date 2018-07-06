package test;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import pages.ExitUnitPage;

/**
 * ExitUnitTest.java - a simple class for testing the Clicktripz Exit Unit modal.
 * @author  Maciej Kocol
 * @version 1.0
 * @see pages.ExitUnitPage
 */
public class ExitUnitTest extends BaseTest {
    ExitUnitPage exitUnitPage;

    /**
     * This test case will switch to a popunder. Depends on search.
     * Verify Exit Unit modal
     * @throws Exception
     */
    @Test(dependsOnGroups = "search", groups = "exit", dataProvider = "tc")
    public void test_Exit_Unit(ITestContext tc) throws Exception{

        // Create Exit Unit Page object
        exitUnitPage = new ExitUnitPage(driver, tc);

        // Switch to Exit Unit window
        exitUnitPage.switchToWindow("Compare Travel Sites");

        // Verify page title
        Assert.assertTrue(exitUnitPage.getPageTitle().contains("Compare Travel Sites"));

        // Capture screenshot when Exit Unit window initially loads
        exitUnitPage.captureSreenshot("ExitUnitLoads");

        // Click the show me the first deal button
        exitUnitPage.clickShowMeDealButton();

        // Switch to window
        exitUnitPage.switchToWindow("Compare Travel Sites");

        // Expand window
        exitUnitPage.expandWidnow();

        // Capture screenshot after welcome modal is closed
        exitUnitPage.captureSreenshot("WelcomeModalClosed");

        // Go through all tabs and capture screenshots
        exitUnitPage.traverseTravelTabs();

        // Capture URL
        exitUnitPage.captureUrl("ExitUnitUrl");

        // Verify number of tabs checked
        Assert.assertEquals(exitUnitPage.tabsCheckedCount(), "7");
    }
}
