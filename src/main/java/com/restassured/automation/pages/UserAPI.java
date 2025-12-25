
package com.restassured.automation.pages;

import static io.restassured.RestAssured.given;

import com.restassured.automation.models.User;
import com.restassured.automation.utils.JsonUtil;
import com.restassured.automation.utils.LoggerUtil;

import io.restassured.response.Response;

public class UserAPI {

	public static Response createUser(User user) {
		String requestBody = JsonUtil.toJsonString(user);

		// Log API request
		LoggerUtil.logApiRequest("POST", "/user", requestBody);

		Response response = given().header("Content-Type", "application/json").body(requestBody).when().post("/user")
				.then().extract().response();

		// Log API response
		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());

		return response;
	}

	public static Response getUser(String username) {
		// Log API request
		LoggerUtil.logApiRequest("GET", "/user/" + username, null);

		Response response = given().header("Content-Type", "application/json").pathParam("username", username).when()
				.get("/user/{username}").then().extract().response();

		// Log API response
		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());

		return response;
	}

	public static Response updateUser(String username, User user) {
		String requestBody = JsonUtil.toJsonString(user);

		// Log API request
		LoggerUtil.logApiRequest("PUT", "/user/" + username, requestBody);

		Response response = given().header("Content-Type", "application/json").pathParam("username", username)
				.body(requestBody).when().put("/user/{username}").then().extract().response();

		// Log API response
		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());

		return response;
	}

	public static Response deleteUser(String username) {
		// Log API request
		LoggerUtil.logApiRequest("DELETE", "/user/" + username, null);

		Response response = given().header("Content-Type", "application/json").pathParam("username", username).when()
				.delete("/user/{username}").then().extract().response();

		// Log API response
		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());

		return response;
	}

	public static Response loginUser(String username, String password) {
		String url = "/user/login?username=" + username + "&password=" + password;

		// Log API request
		LoggerUtil.logApiRequest("GET", url, null);

		Response response = given().header("Content-Type", "application/json").queryParam("username", username)
				.queryParam("password", password).when().get("/user/login").then().extract().response();

		// Log API response
		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());

		return response;
	}
}