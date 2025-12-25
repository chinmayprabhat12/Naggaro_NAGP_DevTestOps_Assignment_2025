package com.restassured.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.restassured.automation.base.BaseTest;
import com.restassured.automation.listeners.ExtentReportListener;
import com.restassured.automation.models.User;
import com.restassured.automation.pages.UserAPI;
import com.restassured.automation.utils.TestDataProvider;

import io.restassured.response.Response;

public class UserTests extends BaseTest {

    @Test(dataProvider = "userData", dataProviderClass = TestDataProvider.class, 
          description = "Test to create user with valid data")
    public void testCreateUser(String username, String firstName, String lastName, 
            String email, String password, String phone, String userStatus) {
        
        // Convert Double phone to String and Double userStatus to int
        int userStatusInt = Integer.parseInt(userStatus);
        
        User user = new User(username, firstName, lastName, email, password, phone, userStatusInt);
        
        Response response = UserAPI.createUser(user);
        
        Assert.assertEquals(response.getStatusCode(), 200, "User creation failed");
        ExtentReportListener.logPass("User created successfully: " + username);
    }

    @Test(dependsOnMethods = "testCreateUser", dataProvider = "userData", 
          dataProviderClass = TestDataProvider.class, description = "Test to get user details")
    public void testGetUser(String username, String firstName, String lastName, 
                           String email, String password, String phone, String userStatus) {
        
        Response response = UserAPI.getUser(username);
        
        Assert.assertEquals(response.getStatusCode(), 200, "User retrieval failed");
//        Assert.assertEquals(response.jsonPath().getString("username"), username);
        ExtentReportListener.logPass("User retrieved successfully: " + username);
    }

    @Test(dependsOnMethods = "testGetUser", dataProvider = "userData", 
          dataProviderClass = TestDataProvider.class, description = "Test to update user")
    public void testUpdateUser(String username, String firstName, String lastName, 
            String email, String password, String phone, String userStatus) {
        
        // Convert Double phone to String and Double userStatus to int
      
        int userStatusInt = Integer.parseInt(userStatus);
        
        // Create updated user data
        User updatedUser = new User(username, firstName + "_updated", lastName + "_updated", 
                                  "updated_" + email, password, "updated_" + phone, userStatusInt);
        
        Response response = UserAPI.updateUser(username, updatedUser);
        
        Assert.assertEquals(response.getStatusCode(), 200, "User update failed");
    }

    @Test(dependsOnMethods = "testUpdateUser", dataProvider = "userData", 
          dataProviderClass = TestDataProvider.class, description = "Test to delete user")
    public void testDeleteUser(String username, String firstName, String lastName, 
            String email, String password, String phone, String userStatus) {
        
        Response response = UserAPI.deleteUser(username);
        
        Assert.assertEquals(response.getStatusCode(), 200, "User deletion failed");
        
        ExtentReportListener.logPass("User deleted successfully: " + username);
    }

    @Test(dataProvider = "userData", dataProviderClass = TestDataProvider.class,
          description = "Test user login functionality")
    public void testUserLogin(String username, String firstName, String lastName, 
            String email, String password, String phone, String userStatus) {
        
        // Convert Double phone to String and Double userStatus to int
    	 int userStatusInt = Integer.parseInt(userStatus);
        
        // First create the user
        User user = new User(username, firstName, lastName, email, password, phone, userStatusInt);
        UserAPI.createUser(user);
        
        // Test login
        Response response = UserAPI.loginUser(username, password);
        
        Assert.assertEquals(response.getStatusCode(), 200, "User login failed");
        Assert.assertTrue(response.jsonPath().getString("message").contains("logged in"));
        ExtentReportListener.logPass("User login successful: " + username);
        
        // Clean up
        UserAPI.deleteUser(username);
    }
    
}