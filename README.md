## Test plan: 
refer 'Test plan.docx'

## Automation scripts: 
-	Developed using Java, Selenium Webdriver, TestNG, Maven 
-	Page factory model was used
-	A single flow/run will cover all scenarios end-to-end across all pages 
-	Data provider used which would provide different inputs for each run 

## System setup needed to run the test scripts: 
-	Java 8
-	Maven
-	Chrome browser  

## To change or add more inputs to test runs: 
-	Go to src/test/java/com.ddtest.Paint/TestFactory.java 
-	Add more rows in the roomsDataProvider return object array (currently has 2 rows, 1st with 2 rooms and 2nd with 4 rooms. Make sure to enter as many room dimensions also as # of rooms) 

## How to trigger test scripts: 
-	Go to app root folder in command prompt
-	Run mvn clean test

## How to view test report: 
-	Reports are created in target/surefire-reports/index.html
-	Logs are available at c:\log\PaintCalculator.log

## Fixes made to the application code: 
-	As per the formula in footer, surface area to paint is ((Length * 2) + (Width * 2)) * Height. But this was not coded properly. Corrected the formula in ‘calculate_feet’ method 
-	As per the formula in footer, 1 gallon of paint needed for painting 400 ft. But in the code, it was 350. Corrected it in ‘calculate_gallons_required’ method 

