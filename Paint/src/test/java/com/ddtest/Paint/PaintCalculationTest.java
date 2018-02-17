package com.ddtest.Paint;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ddtest.Paint.Room;
/**
 * This class holds all the tests which will be executed one after the other
 * 
 * @author devi
 *
 */
public class PaintCalculationTest {
	WebDriver driver;

	HomePage home;
	Dimensions dimensions;
	ResultsPage results;

	List<Room> roomsList;

	String numRooms;

	private static final Logger logger = Logger.getLogger(PaintCalculationTest.class);

	/**
	 * Constructor
	 * 
	 * @param numRooms
	 * @param roomsList
	 */
	public PaintCalculationTest(String numRooms, List<Room> roomsList) {
		super();
		this.numRooms = numRooms;
		this.roomsList = roomsList;
	}

	/**
	 * BeforeClass method will run once per set of test data to setup preconditions
	 * before triggering the tests. Preconditions include initializing web Driver
	 * and initializing page objects
	 */
	@BeforeClass
	public void setupDriver() {
		logger.info("[PaintCalculationTest] test initialized with data for " + numRooms + " rooms");

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");

		driver = new ChromeDriver();

		home = new HomePage(driver);
		dimensions = new Dimensions(driver);
		results = new ResultsPage(driver);

		logger.info("[setupDriver] initialized the driver");

		driver.get("http://127.0.0.1:5000/"); // Endpoint for the paint calculator application

		logger.info("[setupDriver] opened application");

	}

	/**
	 * AfterClass method takes care of cleaning up by closing the driver once
	 * execution is complete for each set of test data
	 */
	@AfterClass
	public void closeDriver() {
		driver.quit();

		logger.info("[closeDriver] closed the driver");
	}

	/**
	 * This test verifies that user cannot proceed to Dimensions page without
	 * setting number of rooms in home page
	 */
	@Test
	public void validationMessagesTest() {

		home.clearRooms();
		
		logger.info("[validationMessagesTest] current page is: " + driver.getTitle());

		String messageNoInput = home.getMessage();
		logger.info("[validationMessagesTest] validation message in home page when there is no data entered: "
				+ messageNoInput);

		home.clickSubmitBtn();
		
		Assert.assertEquals(messageNoInput, "Please fill out this field.");

		Assert.assertTrue(driver.getTitle().equals("Calculating Paint Amount"));
		logger.info("[validationMessagesTest] Remains in Home page when submitted without any input");
		

	}
	
	/**
	 * This test verifies the form validation message for negative number entered
	 */
	@Test
	public void validationMessageNegativeNumberTest() {
		
		home.clearRooms();
		
		home.setNumRooms("-1");
		String message = home.getMessage();
		logger.info("[validationMessagesTest] validation message in home page when negative number is entered: "
				+ message);

		Assert.assertEquals(message, "Value must be greater than or equal to 1.");

	}

	/**
	 * This test verifies the form validation message for 0 entered as input 
	 */
	@Test
	public void validationMessageZeroTest() {
		
		home.clearRooms();
		
		home.setNumRooms("0");
		String message = home.getMessage();
		logger.info("[validationMessagesTest] validation message in home page when 0 is entered: "
				+ message);

		Assert.assertEquals(message, "Value must be greater than or equal to 1.");

	}

	/**
	 * This test verifies the form validation message for decimal number entered
	 */
	@Test
	public void validationMessageDecimalNumberTest() {
		
		home.clearRooms();
		
		home.setNumRooms("1.2");
		String message = home.getMessage();
		logger.info("[validationMessagesTest] validation message in home page when decimal number is entered: "
				+ message);

		Assert.assertEquals(message, "Please enter a valid value. The two nearest valid values are 1 and 2.");

	}

	/**
	 * This test verifies navigation from Home page to Dimensions page when valid
	 * data is provided
	 */
	@Test(dependsOnMethods = { "validationMessagesTest" })
	public void homeToDimensionsNavigationTest() {

		logger.info("[homeToDimensionsNavigationTest] current page is: " + driver.getTitle());

		home.submitNumRooms(numRooms);

		logger.info("[homeToDimensionsNavigationTest] current page is: " + driver.getTitle());
		Assert.assertTrue(driver.getTitle().equals("Dimension Calculation"));
	}

	/**
	 * This test verifies that the number of rooms displayed in Dimensions page
	 * match with the number of rooms supplied in Home page
	 */
	@Test(dependsOnMethods = { "homeToDimensionsNavigationTest" })
	public void verifyDimensionsCountTest() {

		Assert.assertTrue(dimensions.getNumOfRoomsDisplayed() == Integer.valueOf(numRooms),
				"# of rooms displayed in dimensions page does not match with # of rooms entered in home page");
	}

	/**
	 * This test verifies that user cannot proceed to Results page without entering
	 * proper data in Dimensions page
	 */
	@Test(dependsOnMethods = { "verifyDimensionsCountTest" })
	public void dimensionsValidationMessagesTest() {
		
		dimensions.clearDimensions("length-0");
		String messageNoInput = dimensions.getValidationMessage("length-0");
		
		logger.info(
				"[dimensionsValidationMessagesTest] validation message in dimensions page when there is no data entered: "
						+ messageNoInput);

		dimensions.clickSubmitBtn();
		Assert.assertTrue(driver.getTitle().equals("Dimension Calculation"));
		logger.info("[dimensionsValidationMessagesTest] Remains in dimensions page when submitted without any input");

	}

	/**
	 * This test verifies the form validation message for negative number entered
	 */
	@Test(dependsOnMethods = { "dimensionsValidationMessagesTest" })
	public void negativeDimensionsValidationMessagesTest() {
		
		dimensions.clearDimensions("width-0");
		dimensions.inputSpecificDimensions("width-0", "-1");
		String message = dimensions.getValidationMessage("width-0");
		
		logger.info(
				"[dimensionsValidationMessagesTest] validation message in dimensions page when negative number is entered: "
						+ message);

		Assert.assertEquals(message, "Value must be greater than or equal to 1.");

	}

	/**
	 * This test verifies the form validation message for decimal number entered
	 */
	@Test(dependsOnMethods = { "negativeDimensionsValidationMessagesTest" })
	public void decimalDimensionsValidationMessagesTest() {
		
		dimensions.clearDimensions("height-0");
		
		dimensions.inputSpecificDimensions("height-0", "3.4");
		
		String message = dimensions.getValidationMessage("height-0");
		
		logger.info(
				"[dimensionsValidationMessagesTest] validation message in dimensions page when decimal value is entered: "
						+ message);

		Assert.assertEquals(message, "Please enter a valid value. The two nearest valid values are 3 and 4.");

	}
	
	/**
	 * This test verifies the form validation message for 0 entered as input 
	 */
	@Test(dependsOnMethods = { "decimalDimensionsValidationMessagesTest" })
	public void zeroDimensionsValidationMessagesTest() {
		
		dimensions.clearDimensions("length-0");
		
		dimensions.inputSpecificDimensions("length-0", "0");
		
		String message = dimensions.getValidationMessage("length-0");
		
		logger.info(
				"[dimensionsValidationMessagesTest] validation message in dimensions page when 0 is entered: "
						+ message);

		Assert.assertEquals(message, "Value must be greater than or equal to 1.");

	}
	
	/**
	 * This test verifies navigation to Results page from Dimensions page on
	 * entering proper room dimensions
	 */
	@Test(dependsOnMethods = { "zeroDimensionsValidationMessagesTest" })
	public void inputDimensionsTest() {
		
		dimensions.clearDimensions("length-0");
		dimensions.clearDimensions("width-0");
		dimensions.clearDimensions("height-0");
		
		dimensions.submitDimensionsOfAllRooms(Integer.valueOf(numRooms), roomsList);

		logger.info("[verifyDimensionsCountTest] current page is: " + driver.getTitle());
		Assert.assertTrue(driver.getTitle().equals("Results!"));
	}

	/**
	 * This test verifies that all rooms are accounted for in Results page as
	 * entered in the home page, and that no room is missed or no extra rooms are
	 * added
	 */
	@Test(dependsOnMethods = { "inputDimensionsTest" })
	public void verifyResultsRoomcountTest() {
		// results = new ResultsPage(driver);
		Assert.assertTrue(results.getRoomNums().size() == Integer.valueOf(numRooms),
				"# of rooms displayed in dimensions page does not match with # of rooms entered in home page");
		Assert.assertTrue(results.getFeetPerRoom().size() == Integer.valueOf(numRooms),
				"# of rooms displayed in dimensions page does not match with # of rooms entered in home page");
		Assert.assertTrue(results.getGallonsPerRoom().size() == Integer.valueOf(numRooms),
				"# of rooms displayed in dimensions page does not match with # of rooms entered in home page");
		logger.info(
				"[verifyResultsRoomcountTest] # of rooms displayed in results page matches with # entered in home page");
	}

	/**
	 * This test verifies that surface area is calculated for each room as (Length*2
	 * + Width*2) * Height
	 */
	@Test(dependsOnMethods = { "inputDimensionsTest" })
	public void verifyFeetCalculated() {
		// results = new ResultsPage(driver);
		List<String> feetPerRoomValues = results.getFeetPerRoom();

		for (int i = 0; i < feetPerRoomValues.size(); i++) {
			int feetDisplayed = Integer.valueOf((feetPerRoomValues.get(i)));
			logger.info("[verifyFeetCalculated] Feet displayed in results page for room#" + (i + 1) + " is "
					+ feetDisplayed);

			int roomFeet = feetCalculation(roomsList.get(i));
			logger.info("[verifyFeetCalculated] Expected Feet for room#" + (i + 1) + " is " + roomFeet);

			Assert.assertEquals(feetDisplayed, roomFeet, "Feet calculated for room #" + (i + 1) + " is incorrect. "
					+ "Expected: " + roomFeet + ", Dispalyed: " + feetDisplayed);
		}

	}

	/**
	 * This test verifies that gallons required per room is calculated as expected.
	 * 1 Gallon required per 400ft of surface area. Gallons Required is rounded up
	 * to the nearest integer
	 */
	@Test(dependsOnMethods = { "inputDimensionsTest" })
	public void verifyGallonsCalculated() {
		// results = new ResultsPage(driver);
		List<String> gallonsPerRoomValues = results.getGallonsPerRoom();

		for (int i = 0; i < gallonsPerRoomValues.size(); i++) {
			int gallonsDisplayed = Integer.valueOf((gallonsPerRoomValues.get(i)));
			logger.info("[verifyGallonsCalculated] Gallons displayed in results page for room#" + (i + 1) + " is "
					+ gallonsDisplayed);

			int roomGallons = gallonCalculation(roomsList.get(i));
			logger.info("[verifyGallonsCalculated] Expected Gallons for room#" + (i + 1) + " is " + roomGallons);

			Assert.assertEquals(gallonsDisplayed, roomGallons, "Gallons calculated for room #" + (i + 1)
					+ " is incorrect. " + "Expected: " + roomGallons + ", Dispalyed: " + roomGallons);
		}

	}

	/**
	 * This test verifies that the total gallons is eaual to the sum of gallons
	 * required for each room
	 */
	@Test(dependsOnMethods = { "inputDimensionsTest" })
	public void verifyTotalGallonsCalculated() {
		// results = new ResultsPage(driver);
		int totalGallonsDisplayed = Integer.valueOf(results.getTotalGallons());

		int totalGallons = totalGallonsCalculation(roomsList);
		logger.info("[verifyTotalGallonsCalculated] Expected Total Gallons is " + totalGallons);

		Assert.assertEquals(totalGallonsDisplayed, totalGallons, "Total Gallons calculated is incorrect. "
				+ "Expected: " + totalGallons + ", Dispalyed: " + totalGallonsDisplayed);
	}

	/**
	 * This test verifies navigation back to Home page from Results page
	 */
	@Test(dependsOnMethods = { "verifyGallonsCalculated", "verifyFeetCalculated", "verifyResultsRoomcountTest" })
	public void backToHomeNavigationTest() {
		// results = new ResultsPage(driver);

		logger.info("[backToHomeNavigationTest] current page is: " + driver.getTitle());
		results.clickHomeBtn();

		logger.info("[backToHomeNavigationTest] current page is: " + driver.getTitle());
		Assert.assertTrue(driver.getTitle().equals("Calculating Paint Amount"));
	}

	/**
	 * Utility method to calculate surface area of a room given the dimensions
	 * 
	 * @param room
	 * @return
	 */
	public int feetCalculation(Room room) {
		int roomLength = Integer.valueOf(room.getLength());
		int roomWidth = Integer.valueOf(room.getWidth());
		int roomHeight = Integer.valueOf(room.getHeight());

		int roomFeet = ((2 * roomLength) + (2 * roomWidth)) * roomHeight;
		// int roomFeet = roomLength*roomWidth*roomHeight; //*****************incorrect
		// formula

		return roomFeet;
	}

	/**
	 * Utility method to calculate gallons required for each room
	 * 
	 * @param room
	 * @return
	 */
	public int gallonCalculation(Room room) {
		int roomFeet = feetCalculation(room);

		int roomGallons = (int) Math.ceil(roomFeet / 400d);

		// int roomGallons = roomFeet/350; //***************incorrect calculation
		return roomGallons;
	}

	/**
	 * Utility method to find total gallons required for all rooms combined
	 * 
	 * @param roomsList
	 * @return
	 */
	public int totalGallonsCalculation(List<Room> roomsList) {
		int totalGallons = 0;
		for (Room r : roomsList) {
			totalGallons += gallonCalculation(r);
		}

		return totalGallons;
	}

	

}
