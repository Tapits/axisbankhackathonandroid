package com.reva.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FilesUtil {
	// private static final String file = "bulkUpload.properties";
	private static Properties props = null;
	private static String fileName = "config.properties";
	private static void loadPropertiesFromClasspath() throws IOException {
		props = new Properties();
		InputStream inputStream = FilesUtil.class.getClassLoader()
				.getResourceAsStream(fileName);

		if (inputStream == null) {
			throw new FileNotFoundException("Property file 'bulkUpload.properties' not found in the classpath");
		}

		props.load(inputStream);
	}

	public static String getProperty(String key) {
		if (props == null) {
			try {
				loadPropertiesFromClasspath();
			} catch (Exception IOException) {
				return null;
			}
		}
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (props == null) {
			try {
				loadPropertiesFromClasspath();
			} catch (Exception IOException) {
				return null;
			}
		}
		return props.getProperty(key, defaultValue);
	}

	public static int getIntProperty(String key) {
		String property = getProperty(key);

		if (property == null) {
			return -1;
		}
		try {
			return Integer.parseInt(property);
		} catch (NumberFormatException nfe) {
			return (Integer) null;
		}
	}
}
