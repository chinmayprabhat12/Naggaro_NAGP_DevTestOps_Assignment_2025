# Rest Assured API Automation Framework
A comprehensive RestAssured-based API automation framework for testing API endpoints.

```
**Note : 1.Out of all the Test cases, one test cases is intentionally failed by providing Invalid test data to validate the assertion.
2. Sometimes the API doesn't respond properly, which can cause the test scripts to fail even if they’re working perfectly. This usually happens because the site is just a demo and might be slow or unstable at times. The issue is with the website, not the automation scripts.**
```

## Features

- **RestAssured Integration**: Robust API testing with RestAssured
- **TestNG Framework**: Organized test execution with TestNG
- **Page Object Model**: Clean separation of concerns with POM pattern
- **ExtentReports**: Detailed HTML reports with test results
- **Log4j2 Logging**: Comprehensive logging with console and file outputs
- **Data-Driven Testing**: Support for Excel and CSV data providers
- **Parameterized Tests**: Dynamic test data injection
- **Comprehensive Assertions**: Detailed API response validations

## Project Structure

```
RestAssured-API-Automation/
├── src/
│   ├── main/java/com/restassured/automation/
│   │   ├── base/
│   │   │   └── BaseTest.java
│   │   ├── listeners/
│   │   │   ├── ExtentReportListener.java
│   │   ├── models/
│   │   │   ├── Order.java
│   │   │   ├── Pet.java
│   │   │   ├── User.java
│   │   ├── pages/
│   │   │   ├── PetAPI.java
│   │   │   ├── StoreAPI.java
│   │   │   └── UserAPI.java
│   │   └── utils/
│   │       ├── ConfigReader.java
│   │       ├── ExcelUtil.java
│   │       ├── JsonUtil.java
│   │       ├── LoggerUtil.java
│   │       ├── TestDataProvider.java
│   └── test/java/com/restassured/automation/tests/
│       ├── PetTests.java
│       ├── StoreTests.java
│       └── UserTests.java
├── src/test/resources/
│   ├── config.properties
│   ├── log4j2.xml
│   ├── testng.xml
│   └── testdata/
│       ├── testdata.xlsx
├── logs/
├── test-output/
├── pom.xml
├── run-tests.bat
└── README.md
```

### **Prerequisites**
- Java 8 or higher
- Maven 3.6 or higher
- Valid FavQs API Key in `config.properties`

### Installation

1. **Clone/Download the project**
2. **Configure API Key**:
   - Open `src/test/resources/config.properties`
   - Replace `API_KEY` with your actual API key

3. **Install Dependencies**:
   
 ```
bash
mvn clean install
```

### **Running Tests Execution**

#Using TestNg
1. Right-click on the project → **Run As** → **TestNg Test**

#Using Maven
1. Right-click on the project → **Run As** → **Maven clean**
2. After build success → Right-click again → **Run As** → **Maven test**

#Using batch file
Navigate to the project directory and run below bat file

```
run-tests.bat
```
#Using Cmd line
1. Open Command Prompt
2. Navigate to the project directory and run below command

```
mvn test 
```

# Enhanced Reporting and Logs
After test execution below reports are generated

1. HTML Test Report is generated inside the `test-output/extent-reports` folder.
2. Surefire-reports is generated inside the `\target\surefire-reports\FavQs API Automation Suite\` folder.
4. Application Log files (automation.log) are stored in `logs/` folder
5. Junitreport is generated inside the `test-output/junitreports` folder.

