package com.ddtest.Paint;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

/**
 * Test Factory initializes tests in PaintCalculationTest and calls each test
 * one after the other with same sets of data to form a chain of tests
 * 
 * @author devi
 *
 */
public class TestFactory {

	/**
	 * @Factory method initializes tests and calls all the @Test methods
	 *          sequentially for each set of data provided by dp Data Provider
	 *          
	 * @param numRooms
	 * @param roomsList
	 * @return
	 */
	@Factory(dataProvider = "dp")
	public Object[] createInstances(String numRooms, List<Room> roomsList) {
		return new Object[] { new PaintCalculationTest(numRooms, roomsList) };
	}

	/**
	 * Data Provider method which supplies test data to the tests.
	 * Provides number of rooms and room information (length, width and height)
	 * @return
	 */
	@DataProvider(name = "dp")
	public Object[][] roomsDataProvider() {

		return new Object[][] { { "2", Arrays.asList(new Room("10", "10", "10"), new Room("20", "20", "10")) },
				{ "4", Arrays.asList(new Room("14", "12", "10"), new Room("20", "20", "15"), new Room("50", "5", "10"),
						new Room("15", "16", "10")) } };
	}
}
