package com.app.helper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

	public static int getRandom(final int upto) {
		return ThreadLocalRandom.current().nextInt(0, upto);
	}

	public static Properties loadPropertiesOfFile(final String fileName) {
		final Properties properties = new Properties();
		try {
			final InputStream in = new FileInputStream(fileName);
			properties.load(in);
		} catch (final Exception e) {
			System.out.println("Error while loading file : " + fileName);
			e.printStackTrace();
		}
		return properties;
	}

	public static int toInt(final String a) {
		try {
			return Integer.parseInt(a);
		} catch (final Exception e) {
			return 0;
		}
	}

	private Utils() {

	}

}
