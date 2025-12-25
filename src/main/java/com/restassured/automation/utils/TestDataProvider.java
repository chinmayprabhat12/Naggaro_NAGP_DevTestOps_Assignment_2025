package com.restassured.automation.utils;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    
    @DataProvider(name = "userData")
    public static Object[][] getUserData() {
        return ExcelUtil.getTestData("Users");
    }

    @DataProvider(name = "petData")
    public static Object[][] getPetData() {
        return ExcelUtil.getTestData("Pets");
    }

    @DataProvider(name = "orderData")
    public static Object[][] getOrderData() {
        return ExcelUtil.getTestData("Orders");
    }
}