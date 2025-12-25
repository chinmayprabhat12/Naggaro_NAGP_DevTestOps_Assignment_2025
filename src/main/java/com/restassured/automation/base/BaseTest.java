
package com.restassured.automation.base;

import com.restassured.automation.listeners.ExtentReportListener;
import com.restassured.automation.utils.ConfigReader;
import com.restassured.automation.utils.LoggerUtil;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({ ExtentReportListener.class })
public class BaseTest {

	@BeforeSuite
	public void setup() {
		LoggerUtil.info("Setting up test environment...");
		RestAssured.baseURI = ConfigReader.getBaseUrl();
		LoggerUtil.info("Base URL set to: " + ConfigReader.getBaseUrl());
	}
}