
package com.restassured.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;

/**
 * Logger utility class to provide centralized logging functionality.
 */
public class LoggerUtil {

	protected static final Logger logger = LogManager.getLogger(LoggerUtil.class);

	/**
	 * Get logger instance for the specified class
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

	/**
	 * Get logger instance with specified name
	 */
	public static Logger getLogger(String name) {
		return LogManager.getLogger(name);
	}

	/**
	 * Log test step information
	 */
	public static void logTestStep(String stepDescription) {
		logger.info("TEST STEP: " + stepDescription);
	}

	/**
	 * Log API request details
	 */
	public static void logApiRequest(String method, String endpoint, String requestBody) {
		logger.info("=== API REQUEST DETAILS ===");
		logger.info("Method: " + method);
		logger.info("URL: " + RestAssured.baseURI + endpoint);
		logger.info("Headers: Content-Type=application/json");
		if (requestBody != null && !requestBody.isEmpty()) {
			logger.info("Request Body: " + requestBody);
		}
		logger.info("===========================");
	}

	/**
	 * Log API response details
	 */
	public static void logApiResponse(int statusCode, String responseBody) {
		logger.info("=== API RESPONSE DETAILS ===");
		logger.info("Status Code: " + statusCode);
		logger.info("Response Body: " + responseBody);
		logger.info("============================");
	}

	/**
	 * Simple info logging
	 */
	public static void info(String message) {
		logger.info(message);
	}

	/**
	 * Simple error logging
	 */
	public static void error(String message) {
		logger.error(message);
	}

	/**
	 * Simple debug logging
	 */
	public static void debug(String message) {
		logger.debug(message);
	}

	/**
	 * Simple warning logging
	 */
	public static void warn(String message) {
		logger.warn(message);
	}
}