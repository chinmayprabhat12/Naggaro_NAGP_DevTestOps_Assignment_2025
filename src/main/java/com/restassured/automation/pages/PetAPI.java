package com.restassured.automation.pages;

import static io.restassured.RestAssured.given;

import com.restassured.automation.models.Pet;
import com.restassured.automation.utils.JsonUtil;
import com.restassured.automation.utils.LoggerUtil;

import io.restassured.response.Response;

public class PetAPI {

	public static Response createPet(Pet pet) {
		String requestBody = JsonUtil.toJsonString(pet);

		LoggerUtil.logApiRequest("POST", "/pet", requestBody);

		Response response = given().header("Content-Type", "application/json").body(requestBody).when().post("/pet")
				.then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}

	public static Response getPet(Long petId) {
		LoggerUtil.logApiRequest("GET", "/pet/" + petId, null);

		Response response = given().header("Content-Type", "application/json").pathParam("petId", petId).when()
				.get("/pet/{petId}").then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}

	public static Response updatePet(Pet pet) {
		String requestBody = JsonUtil.toJsonString(pet);

		LoggerUtil.logApiRequest("PUT", "/pet", requestBody);

		Response response = given().header("Content-Type", "application/json").body(requestBody).when().put("/pet")
				.then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}

	public static Response deletePet(Long petId) {
		LoggerUtil.logApiRequest("DELETE", "/pet/" + petId, null);

		Response response = given().header("Content-Type", "application/json").header("api_key", "special-key")
				.pathParam("petId", petId).when().delete("/pet/{petId}").then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}
}