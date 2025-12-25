package com.restassured.automation.tests;

import com.restassured.automation.base.BaseTest;
import com.restassured.automation.listeners.ExtentReportListener;
import com.restassured.automation.models.Pet;
import com.restassured.automation.pages.PetAPI;
import com.restassured.automation.utils.LoggerUtil;
import com.restassured.automation.utils.TestDataProvider;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

public class PetTests extends BaseTest {

    @Test(dataProvider = "petData", dataProviderClass = TestDataProvider.class)
    public void testCreatePet(String id, String category, String name, String photoUrls, String tags, String status) {
        LoggerUtil.info("Creating pet: " + name);
        
        try {
            // Convert String parameters to appropriate types
            Long petId = Long.parseLong(id);
            
            // Create pet with proper object structure
            Pet pet = new Pet();
            pet.setId(petId);
            pet.setName(name);
            pet.setStatus(status);
            
            // Set category as object
            Pet.Category categoryObj = new Pet.Category(1L, category);
            pet.setCategory(categoryObj);
            
            // Set photo URLs as list
            pet.setPhotoUrls(Arrays.asList(photoUrls.split(",")));
            
            // Set tags as list of Tag objects
            Pet.Tag tagObj = new Pet.Tag(1L, tags);
            pet.setTags(Arrays.asList(tagObj));
            
            Response response = PetAPI.createPet(pet);
            
            Assert.assertEquals(response.getStatusCode(), 200, "Pet creation failed. Response: " + response.getBody().asString());
            LoggerUtil.info("Pet created successfully: " + name);
            ExtentReportListener.logPass("Pet created: " + name);
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Pet creation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(dependsOnMethods = "testCreatePet", dataProvider = "petData", dataProviderClass = TestDataProvider.class)
    public void testGetPet(String id, String category, String name, String photoUrls, String tags, String status) {
        LoggerUtil.info("Getting pet: " + name);
        
        try {
            Long petId = Long.parseLong(id);
            Response response = PetAPI.getPet(petId);
            
            Assert.assertEquals(response.getStatusCode(), 200);
            
            ExtentReportListener.logPass("Pet retrieved: " + name);
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Pet retrieval failed: : " + e.getMessage());
            throw e;
        }
    }

    @Test(dependsOnMethods = "testGetPet", dataProvider = "petData", dataProviderClass = TestDataProvider.class)
    public void testUpdatePet(String id, String category, String name, String photoUrls, String tags, String status) {
        LoggerUtil.info("Updating pet: " + name);
        
        try {
            Long petId = Long.parseLong(id);
            
            // Create updated pet with proper object structure
            Pet updatedPet = new Pet();
            updatedPet.setId(petId);
            updatedPet.setName(name + "_Updated");
            updatedPet.setStatus("sold");
            
            // Set category as object
            Pet.Category categoryObj = new Pet.Category(1L, category);
            updatedPet.setCategory(categoryObj);
            
            // Set photo URLs as list
            updatedPet.setPhotoUrls(Arrays.asList(photoUrls.split(",")));
            
            // Set tags as list of Tag objects
            Pet.Tag tagObj = new Pet.Tag(1L, tags + "_updated");
            updatedPet.setTags(Arrays.asList(tagObj));
            
            Response response = PetAPI.updatePet(updatedPet);
            
            Assert.assertEquals(response.getStatusCode(), 200);
            LoggerUtil.info("Pet updated successfully: " + name);
            ExtentReportListener.logPass("Pet updated: " + name);
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Pet update failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(dependsOnMethods = "testUpdatePet", dataProvider = "petData", dataProviderClass = TestDataProvider.class)
    public void testDeletePet(String id, String category, String name, String photoUrls, String tags, String status) {
        LoggerUtil.info("Deleting pet: " + name);
        
        try {
            Long petId = Long.parseLong(id);
            Response response = PetAPI.deletePet(petId);
            
            Assert.assertEquals(response.getStatusCode(), 200);
            
            ExtentReportListener.logPass("Pet deleted: " + name);
            
        } catch (Exception e) {
            LoggerUtil.error("Test failed: " + e.getMessage());
            ExtentReportListener.logFail("Pet deletion failed: " + e.getMessage());
            throw e;
        }
    }
}