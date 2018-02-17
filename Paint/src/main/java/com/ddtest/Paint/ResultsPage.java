package com.ddtest.Paint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object for Results page
 * 
 * @author devi
 *
 */
public class ResultsPage {
	private static final Logger logger = Logger.getLogger(ResultsPage.class);

	WebDriver driver;

	@FindBy(xpath = "//tr/td[1]")
	List<WebElement> roomNums;

	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> feetPerRoom;

	@FindBy(xpath = "//tr/td[3]")
	List<WebElement> gallonsPerRoom;

	@FindBy(xpath = "//div[@class='container']/h5")
	WebElement totalGallons;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement homeBtn;

	/**
	 * Constructor initializes driver and web elements
	 * 
	 * @param driver
	 */
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("[ResultsPage] initialized ResultsPage object");
	}

	/**
	 * Method to find number of rooms displayed in Results table
	 * 
	 * @return
	 */
	public List<String> getRoomNums() {
		List<String> roomNumsValues = new ArrayList<String>();
		logger.info("[getRoomNums] getting list of room #s displayed in results page");

		Iterator<WebElement> roomIterator = this.roomNums.iterator();
		while (roomIterator.hasNext()) {
			roomNumsValues.add(roomIterator.next().getText());
		}
		logger.info("[getRoomNums] # of rooms displayed in results page: " + roomNumsValues.size());
		return roomNumsValues;
	}

	/**
	 * Method to find surface area displayed for all rooms
	 * 
	 * @return
	 */
	public List<String> getFeetPerRoom() {
		List<String> feetPerRoomValues = new ArrayList<String>();
		logger.info("[getFeetPerRoom] getting 'feet' displayed for each individual room in results page");

		Iterator<WebElement> feetIterator = this.feetPerRoom.iterator();
		while (feetIterator.hasNext()) {
			feetPerRoomValues.add(feetIterator.next().getText());
		}
		logger.info("[getFeetPerRoom] 'feet' displayed in results page for " + feetPerRoomValues.size() + " rooms");
		return feetPerRoomValues;
	}

	/**
	 * Method to find gallons displayed for all rooms
	 * 
	 * @return
	 */
	public List<String> getGallonsPerRoom() {
		List<String> gallonsPerRoomValues = new ArrayList<String>();
		logger.info("[getGallonsPerRoom] getting 'gallons' displayed for each individual room in results page");

		Iterator<WebElement> gallonsIterator = this.gallonsPerRoom.iterator();
		while (gallonsIterator.hasNext()) {
			gallonsPerRoomValues.add(gallonsIterator.next().getText());
		}
		logger.info("[getGallonsPerRoom] 'gallons' displayed in results page for " + gallonsPerRoomValues.size()
				+ " rooms");
		return gallonsPerRoomValues;
	}

	/**
	 * Method to find total gallons displayed below the table
	 * 
	 * @return
	 */
	public String getTotalGallons() {
		logger.info("[getTotalGallons] getting 'total gallons' displayed in results page");
		String totalGallons = this.totalGallons.getText().replaceAll("(\\D+)", "");
		logger.info("[getTotalGallons] 'total gallons' displayed in results page is " + totalGallons);
		return totalGallons;
	}

	/**
	 * Method to click on Home button
	 */
	public void clickHomeBtn() {
		this.homeBtn.click();
		logger.info("[clickHomeBtn in Results] clicked home in results page");
	}

}
