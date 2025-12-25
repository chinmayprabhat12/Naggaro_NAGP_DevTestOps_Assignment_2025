
package com.restassured.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String toJsonString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("Error converting object to JSON", e);
		}
	}

	public static <T> T fromJsonString(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Error converting JSON to object", e);
		}
	}
}