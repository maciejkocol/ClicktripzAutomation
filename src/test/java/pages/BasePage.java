package pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

/**
 * BasePage.java - a helper class for managing shared Clicktripz page objects.
 * @author  Maciej Kocol
 * @version 1.0
 */
public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;
    public ITestContext tc;

    public int sleepTime = 1000;

    private SimpleDateFormat formatter;

    private String captureDate;
    private String captureTest;
    private String capturePath = "./results";
    private String captureType = "png";
    private String dateFormat = "yyyyMMddHHmmss";
    private String formTitle = "Form - Clicktripz";

    // Tabs
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Hotel Citywide']")
    public WebElement hotelCitywideTab;
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Hotel Dateless']")
    public WebElement hotelDatelessTab;
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Hotel Specific']")
    public WebElement hotelSpecificTab;
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Flights']")
    public WebElement flightsTab;
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Vacations']")
    public WebElement vacationsTab;
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Cruises']")
    public WebElement cruisesTab;
    @FindBy(xpath="//ul[starts-with(@class,'nav')]/li/a[text()='Car Rentals']")
    public WebElement carRentalsTab;

    // Fields
    @FindBy(id="date1")
    WebElement date1;
    @FindBy(id="date2")
    WebElement date2;

    // Buttons
    @FindBy(id="search-button")
    WebElement searchButton;

    // Controls
    @FindBy(xpath="//table[@class='ui-datepicker-calendar']//a[contains(@class,'ui-state-active')]")
    WebElement datePickerActiveDay;

    public BasePage(WebDriver driver, ITestContext tc){
        this.tc = tc;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
        formatter = new SimpleDateFormat(dateFormat);
        captureDate = formatter.format(new Date());
        captureTest = tc.getCurrentXmlTest().getName().replaceAll("\\p{Z}","");
    }

    // Get page title
    public String getPageTitle(){
        return driver.getTitle();
    }

    // Select the Hotel Citywide tab
    public void selectHotelCitywideTab() {
        wait.until(d -> hotelCitywideTab.isDisplayed());
        hotelCitywideTab.click();
    }

    // Select the Hotel Dateless tab
    public void selectHotelDatelessTab() {
        wait.until(d -> hotelDatelessTab.isDisplayed());
        hotelDatelessTab.click();
    }

    // Select the Hotel Specific tab
    public void selectHotelSpecificTab() {
        wait.until(d -> hotelSpecificTab.isDisplayed());
        hotelSpecificTab.click();
    }

    // Select the Flights tab
    public void selectFlightsTab() {
        wait.until(d -> flightsTab.isDisplayed());
        flightsTab.click();
    }

    // Select the Vacations tab
    public void selectVacationsTab() {
        wait.until(d -> vacationsTab.isDisplayed());
        vacationsTab.click();
    }

    // Select the Cruises tab
    public void selectCruisesTab() {
        wait.until(d -> cruisesTab.isDisplayed());
        cruisesTab.click();
    }

    // Select the Car Rentals tab
    public void selectCarRentalsTab() {
        wait.until(d -> carRentalsTab.isDisplayed());
        carRentalsTab.click();
    }

    // Set first date
    public void setDate1(String strDate){
        wait.until(d -> date1.isDisplayed());
        date1.clear();
        date1.sendKeys(strDate);
        selectDatePickerActiveDay();
    }

    // Set second date
    public void setDate2(String strDate){
        wait.until(d -> date2.isDisplayed());
        date2.clear();
        date2.sendKeys(afterCheckIn(date1.getAttribute("value"), strDate));
        selectDatePickerActiveDay();
    }

    // Click the search button
    public void clickSearchButton() throws InterruptedException {
        wait.until(d -> searchButton.isDisplayed());
        Thread.sleep(sleepTime);
        searchButton.click();
        Thread.sleep(sleepTime);
    }

    /**
     * This method expands the current window.
     */
    public void expandWidnow() {
        driver.manage().window().setSize(new Dimension(1600, 1200));
        driver.manage().window().setPosition(new Point(0, 0));
    }

    /**
     * This method minimizes the current window.
     */
    public void minimizeWindow(){
        driver.manage().window().setPosition(new Point(0, 2000));
    }

    /**
     * This method switches to another window by page title.
     * @param pageTitle Page title of the window to switch to
     * @throws InterruptedException
     */
    public void switchToWindow(String pageTitle) throws InterruptedException {
        outerloop:
        for (int i=0; i < 10 && !driver.getTitle().contains(pageTitle); i++){
            Iterator<String> windowIterator = driver.getWindowHandles().iterator();
            while(windowIterator.hasNext()) {
                String windowHandle = windowIterator.next();
                driver.switchTo().window(windowHandle);
                String currentTitle = driver.getTitle();
                if (currentTitle.contains(pageTitle)) {
                    break outerloop;
                } else if (currentTitle.contains(formTitle)) {
                    clickSearchButton();
                }
            }
            Thread.sleep(sleepTime);
        }
    }

    /**
     * This method captures and saves a screenshot of the current window.
     * @param captureName as the name to save file with
     */
    public void captureSreenshot(String captureName) {
        try {
            String filePath = capturePath + "/" + captureDate + "_" + captureTest + "_" + captureName + "." + captureType;
            createPath(filePath);
            TakesScreenshot scrShot = ((TakesScreenshot)driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileHandler.copy(srcFile, destFile);
        } catch (Exception ex) {
            System.err.println("Unable to capture screenshot...");
            ex.printStackTrace();
        }
    }

    /**
     * This method captures and saves the url of the current page.
     * @param fileName as the name to save file with
     */
    public void captureUrl(String fileName) {
        try {
            String filePath = capturePath + "/" + captureDate + "_" + captureTest + "_" + fileName;
            createPath(filePath);
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.println(driver.getCurrentUrl());
            writer.close();
        } catch (Exception ex) {
            System.err.println("Unable to capture url...");
            ex.printStackTrace();
        }
    }

    /**
     * This method creates the path for capturing results.
     * @param filePath as the path to save under
     */
    public void createPath(String filePath) {
        try {
            File outFile = new File(filePath);
            outFile.getParentFile().mkdirs();
            if (!outFile.exists()) outFile.createNewFile();
        } catch (Exception ex) {
            System.err.println("Unable to create path...");
            ex.printStackTrace();
        }
    }

    /**
     * This method selects the active day in date picker.
     */
    public void selectDatePickerActiveDay(){
        wait.until(d -> datePickerActiveDay.isDisplayed());
        datePickerActiveDay.click();
    }

    /**
     * This method gets the next week's date by specified day of the week.
     * @param strDay Day of next week
     * @return next week's date
     */
    public String getNextWeeksDateFor(String strDay){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String nextWeeksDate = "";
        Date date = new Date();
        Date after = setDayOfWeek(addWeek(date), strDay);
        nextWeeksDate = formatter.format(after);
        return nextWeeksDate;
    }

    /**
     * This method checks if the check out date is before the check in date. If yes, then advance the check out date by one week.
     * @param strCheckInDate Check in date
     * @param strCheckOutDate Check out date
     * @return check out date advanced by one week
     */
    public String afterCheckIn(String strCheckInDate, String strCheckOutDate){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date checkInDate = null;
        Date checkOutDate = null;
        try {
            checkInDate = formatter.parse(strCheckInDate);
            checkOutDate = formatter.parse(strCheckOutDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (checkOutDate.before(checkInDate)) {
            return formatter.format(addWeek(checkOutDate));
        } else {
            return strCheckOutDate;
        }
    }

    /**
     * This method adds one week to a given date.
     * @param date Date to advance by one week
     * @return Date advanced by one week
     */
    public Date addWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * This method sets the day of the week for a given date.
     * @param date Date to set day of week for
     * @param strDay day of week
     * @return Date set to a given day of week
     */
    public Date setDayOfWeek(Date date, String strDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, getDayOfWeek(strDay));
        return cal.getTime();
    }

    /**
     * This method converts literal day of the week to an integer (SUN-SAT : 1-7)
     * @param dayOfWeek Word representation for day of the week
     * @return Numerical representation for the day of the week
     */
    public Integer getDayOfWeek(String dayOfWeek) {
        Integer num = 0;
        switch (dayOfWeek) {
            case "Monday":
                num = 2;
                break;
            case "Tuesday":
                num = 3;
                break;
            case "Wednesday":
                num = 4;
                break;
            case "Thursday":
                num = 5;
                break;
            case "Friday":
                num = 6;
                break;
            case "Saturday":
                num = 7;
                break;
            case "Sunday":
                num = 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeek);
        }
        return num;
    }
}