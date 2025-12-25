package com.restassured.automation.pages;

import static io.restassured.RestAssured.given;

import com.restassured.automation.models.Order;
import com.restassured.automation.utils.JsonUtil;
import com.restassured.automation.utils.LoggerUtil;

import io.restassured.response.Response;

public class StoreAPI {

	public static Response createOrder(Order order) {
		String requestBody = JsonUtil.toJsonString(order);

		LoggerUtil.logApiRequest("POST", "/store/order", requestBody);

		Response response = given().header("Content-Type", "application/json").body(requestBody).when()
				.post("/store/order").then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}

	public static Response getOrder(Long orderId) {
		LoggerUtil.logApiRequest("GET", "/store/order/" + orderId, null);

		Response response = given().header("Content-Type", "application/json").pathParam("orderId", orderId).when()
				.get("/store/order/{orderId}").then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}

	public static Response deleteOrder(Long orderId) {
		LoggerUtil.logApiRequest("DELETE", "/store/order/" + orderId, null);

		Response response = given().header("Content-Type", "application/json").pathParam("orderId", orderId).when()
				.delete("/store/order/{orderId}").then().extract().response();

		LoggerUtil.logApiResponse(response.getStatusCode(), response.getBody().asString());
		return response;
	}
}