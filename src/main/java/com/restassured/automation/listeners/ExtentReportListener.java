
package com.restassured.automation.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListener implements ITestListener, ISuiteListener {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static final String REPORT_PATH = "test-output/extent-reports/";
	private static boolean isInitialized = false;

	public static void logInfo(String message) {
		if (test.get() != null) {
			test.get().info(message);
		} else {
			System.out.println("INFO: " + message);
		}
	}

	public static void logPass(String message) {
		if (test.get() != null) {
			test.get().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
		} else {
			System.out.println("PASS: " + message);
		}
	}

	public static void logFail(String message) {
		if (test.get() != null) {
			test.get().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
		} else {
			System.out.println("FAIL: " + message);
		}
	}

	public static void logWarning(String message) {
		if (test.get() != null) {
			test.get().warning(MarkupHelper.createLabel(message, ExtentColor.AMBER));
		} else {
			System.out.println("WARNING: " + message);
		}
	}

	public static void logSkip(String message) {
		if (test.get() != null) {
			test.get().skip(MarkupHelper.createLabel(message, ExtentColor.ORANGE));
		} else {
			System.out.println("SKIP: " + message);
		}
	}

	public static void flushReport() {
		if (extent != null) {
			extent.flush();
		}
	}

	@Override
	public void onStart(ITestContext context) {
		initializeExtentReport();
	}

	private synchronized void initializeExtentReport() {
		if (isInitialized)
			return;

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportName = "ExtentReport-" + timeStamp + ".html";

		// Create directory if it doesn't exist
		File reportDir = new File(REPORT_PATH);
		if (!reportDir.exists()) {
			reportDir.mkdirs();
		}

		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_PATH + reportName);
		htmlReporter.config().setDocumentTitle("Rest Assured Test Report");
		htmlReporter.config().setReportName("API Automation Test Results");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// System information
		extent.setSystemInfo("Organization", "RestAssured");
		extent.setSystemInfo("Project", "API Automation Framework");
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));

		isInitialized = true;
	}

	@Override
	public void onTestStart(ITestResult result) {
		initializeExtentReport(); // Ensure report is initialized

		String testName = result.getMethod().getMethodName();
		String description = result.getMethod().getDescription();

		if (description != null && !description.isEmpty()) {
			testName = testName + " - " + description;
		}

		ExtentTest extentTest = extent.createTest(testName);
		test.set(extentTest);

		// Add test parameters if any
		Object[] parameters = result.getParameters();
		if (parameters != null && parameters.length > 0) {
			StringBuilder params = new StringBuilder("Test Parameters: ");
			for (Object param : parameters) {
				params.append(param).append(" | ");
			}
			logInfo(params.toString());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if (test.get() != null) {
			test.get().log(Status.PASS, "Test Passed");
			logExecutionTime(result);
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		if (test.get() != null) {
			test.get().log(Status.FAIL, "Test Failed");
			test.get().log(Status.FAIL, result.getThrowable());

			// Add failure details
			logFail("Failure Reason: " + result.getThrowable().getMessage());
			logExecutionTime(result);

			// You can also add screenshot or API response here if needed
			if (result.getAttribute("apiResponse") != null) {
				logInfo("API Response: " + result.getAttribute("apiResponse").toString());
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		if (test.get() != null) {
			test.get().log(Status.SKIP, "Test Skipped");
			if (result.getThrowable() != null) {
				test.get().log(Status.SKIP, result.getThrowable());
			}
			logExecutionTime(result);
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// This method is for tests that fail but are within success percentage
	}

	@Override
	public void onFinish(ITestContext context) {
		// Add overall test summary directly to extent report
		addSuiteSummary(context);
		flushReport();
	}

	private void addSuiteSummary(ITestContext context) {
		if (extent != null) {
			// Add summary as system information
			extent.setSystemInfo("Total Tests Executed", String.valueOf(context.getAllTestMethods().length));
			extent.setSystemInfo("Passed Tests", String.valueOf(context.getPassedTests().size()));
			extent.setSystemInfo("Failed Tests", String.valueOf(context.getFailedTests().size()));
			extent.setSystemInfo("Skipped Tests", String.valueOf(context.getSkippedTests().size()));

			// Create a summary test node for better visibility
			extent.createTest("Test Suite Execution Summary")
					.info("Execution Completed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
					.info("Total Tests: " + context.getAllTestMethods().length)
					.info("Passed Tests: " + context.getPassedTests().size())
					.info("Failed Tests: " + context.getFailedTests().size())
					.info("Skipped Tests: " + context.getSkippedTests().size());
		}
	}

	private void logExecutionTime(ITestResult result) {
		long duration = result.getEndMillis() - result.getStartMillis();
		logInfo("Execution Time: " + duration + "ms");
	}

	// Helper method to add API request and response details to report
	public static void addApiDetails(String requestMethod, String endpoint, String requestBody, String responseBody,
			int statusCode) {
		if (test.get() != null) {
			StringBuilder apiDetails = new StringBuilder();
			apiDetails.append("<b>API Request Details:</b><br>");
			apiDetails.append("Method: ").append(requestMethod).append("<br>");
			apiDetails.append("Endpoint: ").append(endpoint).append("<br>");

			if (requestBody != null && !requestBody.isEmpty()) {
				apiDetails.append("Request Body: ").append(formatJson(requestBody)).append("<br>");
			}

			apiDetails.append("<b>API Response Details:</b><br>");
			apiDetails.append("Status Code: ").append(statusCode).append("<br>");

			if (responseBody != null && !responseBody.isEmpty()) {
				apiDetails.append("Response Body: ").append(formatJson(responseBody));
			}

			test.get().info(apiDetails.toString());
		} else {
			System.out.println(
					"API Details - Method: " + requestMethod + ", Endpoint: " + endpoint + ", Status: " + statusCode);
		}
	}

	private static String formatJson(String json) {
		try {
			// Simple JSON formatting for better readability
			return json.replace(",", ",<br>").replace("{", "{<br>").replace("}", "<br>}").replace("[", "[<br>")
					.replace("]", "<br>]");
		} catch (Exception e) {
			return json; // Return original if formatting fails
		}
	}

	// Method to set current test instance (useful for static contexts)
	public static void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}

	// Method to get current test instance
	public static ExtentTest getTest() {
		return test.get();
	}

	// Method to check if test instance exists
	public static boolean hasTest() {
		return test.get() != null;
	}
}
