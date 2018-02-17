package com.ddtest.Paint;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object for Home page
 * 
 * @author devi
 *
 */
public class HomePage {
	private static final Logger logger = Logger.getLogger(HomePage.class);

	WebDriver driver;

	@FindBy(name = "rooms")
	WebElement numRooms;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement submitBtn;

	/**
	 * Constructor initializes driver and web elements
	 * 
	 * @param driver
	 */
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("[HomePage] initialized HomePage object");
	}

	/**
	 * Method to set the number of rooms in home page
	 * 
	 * @param numRooms
	 */
	public void setNumRooms(String numRooms) {
		this.numRooms.sendKeys(numRooms);
		logger.info("[setNumRooms in HomePage] entered # of rooms as " + numRooms);
	}

	/**
	 * Method to click on submit button
	 */
	public void clickSubmitBtn() {
		this.submitBtn.click();
		logger.info("[clickSubmitBtn in HomePage] clicked submit in home page");
	}

	/**
	 * Method to submit the number of rooms
	 * 
	 * @param numRooms
	 */
	public void submitNumRooms(String numRooms) {
		this.setNumRooms(numRooms);
		this.clickSubmitBtn();
	}

	/**
	 * method to get the validation message on the page 
	 * @return 
	 */
	public String getMessage() {
		return numRooms.getAttribute("validationMessage");
	}
	
	/**
	 * method to clear the current value in number of rooms
	 */
	public void clearRooms() {
		numRooms.clear();
	}
	
}
