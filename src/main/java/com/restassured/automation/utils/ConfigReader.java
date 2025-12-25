
package com.restassured.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;

	static {
		properties = new Properties();
		try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				throw new RuntimeException("Unable to find config.properties");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Error loading config.properties", e);
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getBaseUrl() {
		return getProperty("base.url");
	}

	public static String getApiKey() {
		return getProperty("api.key");
	}
}
