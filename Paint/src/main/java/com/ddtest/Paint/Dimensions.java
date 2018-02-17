package com.ddtest.Paint;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object for Dimensions page
 * 
 * @author devi
 *
 */
public class Dimensions {
	private static final Logger logger = Logger.getLogger(Dimensions.class);

	WebDriver driver;

	@FindBy(name = "dimensions_table")
	WebElement dimensionsTable;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement submitBtn;

	@FindBy(tagName = "footer")
	WebElement footer;

	/**
	 * Constructor initializes driver and web elements
	 * 
	 * @param driver
	 */
	public Dimensions(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("[Dimensions] initialized Dimensions object");
	}

	/**
	 * Method to find number of rooms displayed in Dimensions table
	 * 
	 * @return
	 */
	public int getNumOfRoomsDisplayed() {
		int numRows = this.dimensionsTable.findElements(By.tagName("tr")).size();
		logger.info("[getNumOfRoomsDisplayed] # of rooms displayed in dimensions page is " + (numRows - 1));
		return (numRows - 1);
	}

	/**
	 * Method to find length, width and height elements for each room corresponding
	 * to room numbers
	 * 
	 * @param n
	 * @return
	 */
	public List<WebElement> getNthRoomElements(String n) {
		logger.info("[getNthRoomElements] finding elements of room # " + n + " in dimensions page");
		logger.info("[getNthRoomElements] getting all the table rows in dimensions page");
		List<WebElement> tableRows = this.dimensionsTable.findElements(By.tagName("tr"));

		List<WebElement> nthRoomElements = new ArrayList<WebElement>();

		logger.info("[getNthRoomElements] looping through each row in dimensions page");
		for (WebElement currRow : tableRows) {
			if (currRow.findElements(By.tagName("td")).size() > 0) {
				String rowNum = (currRow.findElement(By.xpath("td[1]")).getText());
				logger.info("[getNthRoomElements] currently checking row# " + rowNum);
				if (currRow.findElement(By.xpath("td[1]")).getText().equals(n)) {
					nthRoomElements.add(currRow.findElement(By.xpath("td[2]")));
					nthRoomElements.add(currRow.findElement(By.xpath("td[3]")));
					nthRoomElements.add(currRow.findElement(By.xpath("td[4]")));
					logger.info("[getNthRoomElements] Obtained the web elements for room# " + rowNum);
					break;
				}
			}
		}

		return nthRoomElements;
	}

	/**
	 * Method to set data for length, width and height for each room
	 * 
	 * @param nthRoomElements
	 * @param room
	 */
	public void inputNthRoom(List<WebElement> nthRoomElements, Room room) {

		logger.info("[inputNthRoom] inputting the dimensions of room");
		Actions action = new Actions(driver);
		action.moveToElement(nthRoomElements.get(0)).click().sendKeys(room.getLength()).build().perform();
		action.moveToElement(nthRoomElements.get(1)).click().sendKeys(room.getWidth()).build().perform();
		action.moveToElement(nthRoomElements.get(2)).click().sendKeys(room.getHeight()).build().perform();
		logger.info("[inputNthRoom] values entered are: " + room.getLength() + ", " + room.getWidth() + ", "
				+ room.getHeight());

	}

	/**
	 * Method to click on submit button
	 */
	public void clickSubmitBtn() {
		this.submitBtn.click();
		logger.info("[clickSubmitBtn in Dimensions] clicked submit in dimensions page");
	}

	/**
	 * Method to enter values in length, width and height for all rooms and click on
	 * submit button
	 * 
	 * @param n
	 * @param roomsList
	 */
	public void submitDimensionsOfAllRooms(int n, List<Room> roomsList) {
		List<WebElement> nthRoomElements;
		for (int i = 0; i < n; i++) {
			nthRoomElements = this.getNthRoomElements(String.valueOf(i + 1));
			logger.info("[fillDimensionsOfAllRooms] input dimensions data for room#" + (i + 1));
			this.inputNthRoom(nthRoomElements, roomsList.get(i));
			nthRoomElements.clear();
		}
		this.clickSubmitBtn();
	}

	/**
	 * method to get the validation message for a given input field
	 * @param field
	 * @return message 
	 */
	public String getValidationMessage(String field) {
		return driver.findElement(By.name(field)).getAttribute("validationMessage");
	}
	
	/**
	 * method to clear the current value in given field 
	 * @param field
	 */
	public void clearDimensions(String field) {
		driver.findElement(By.name(field)).clear();
	}
	
	/**
	 * method to enter the given value in specific field 
	 * @param field
	 * @param value
	 */
	public void inputSpecificDimensions(String field, String value) {
		driver.findElement(By.name(field)).sendKeys(value);
	}
	
	
}
