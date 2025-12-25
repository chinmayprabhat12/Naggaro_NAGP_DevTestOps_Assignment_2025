package com.restassured.automation.tests;

import com.restassured.automation.base.BaseTest;
import com.restassured.automation.listeners.ExtentReportListener;
import com.restassured.automation.models.Order;
import com.restassured.automation.models.Pet;
import com.restassured.automation.pages.PetAPI;
import com.restassured.automation.pages.StoreAPI;
import com.restassured.automation.utils.LoggerUtil;
import com.restassured.automation.utils.TestDataProvider;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class StoreTests extends BaseTest {

    @Test(dataProvider = "orderData", dataProviderClass = TestDataProvider.class)
    public void testCreateOrder(String orderId, String petId, String quantity, String status, String complete) {
        LoggerUtil.info("Creating order: " + orderId);
        
        try {
            // Convert String parameters to appropriate types
            Long orderIdLong = Long.parseLong(orderId);
            Long petIdLong = Long.parseLong(petId);
            Integer quantityInt = Integer.parseInt(quantity);
            Boolean completeBool = Boolean.parseBoolean(complete);
            
            // First create a pet
            Pet pet = new Pet(petIdLong, "Dogs", "TestPet", 
                             Arrays.asList("test.jpg"), 
                             Arrays.asList("test"), 
                             "available");
            PetAPI.createPet(pet);
            
            Order order = new Order(orderIdLong, petIdLong, quantityInt, status, completeBool);
            Response response = StoreAPI.createOrder(order);
            
            Assert.assertEquals(response.getStatusCode(), 200);
            LoggerUtil.info("Order created successfully: " + orderId);
            ExtentReportListener.logPass("Order created: " + orderId);
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Order creation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(dependsOnMethods = "testCreateOrder", dataProvider = "orderData", dataProviderClass = TestDataProvider.class)
    public void testGetOrder(String orderId, String petId, String quantity, String status, String complete) {
        LoggerUtil.info("Getting order: " + orderId);
        
        try {
            Long orderIdLong = Long.parseLong(orderId);
            Response response = StoreAPI.getOrder(orderIdLong);
            
            Assert.assertEquals(response.getStatusCode(), 200);
            
            // Convert expected values for comparison
            Long expectedPetId = Long.parseLong(petId);
            Integer expectedQuantity = Integer.parseInt(quantity);
            Boolean expectedComplete = Boolean.parseBoolean(complete);
            
            Assert.assertEquals(response.jsonPath().getLong("id"), orderIdLong.longValue());
            Assert.assertEquals(response.jsonPath().getLong("petId"), expectedPetId.longValue());
            Assert.assertEquals(response.jsonPath().getInt("quantity"), expectedQuantity.intValue());
            Assert.assertEquals(response.jsonPath().getString("status"), status);
      //      Assert.assertEquals(response.jsonPath().getBoolean("complete"), expectedComplete);
            Assert.assertEquals(response.jsonPath().getBoolean("complete"), expectedComplete.booleanValue());    
            LoggerUtil.info("Order retrieved successfully: " + orderId);
            ExtentReportListener.logPass("Order retrieved: " + orderId);
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Order retrieval failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(dependsOnMethods = "testGetOrder", dataProvider = "orderData", dataProviderClass = TestDataProvider.class)
    public void testDeleteOrder(String orderId, String petId, String quantity, String status, String complete) {
        LoggerUtil.info("Deleting order: " + orderId);
        
        try {
            Long orderIdLong = Long.parseLong(orderId);
            
            Response response = StoreAPI.deleteOrder(orderIdLong);
            Assert.assertEquals(response.getStatusCode(), 200);
            
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Order deletion failed: " + e.getMessage());
            throw e;
        }
    }
}