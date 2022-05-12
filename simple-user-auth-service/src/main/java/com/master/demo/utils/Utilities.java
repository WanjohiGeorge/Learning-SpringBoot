package com.master.demo.utils;

import java.util.Random;

public class Utilities {

	private static int length = 50;
	private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public String GenerateUserID() {
		return generateRandomString(length);
	}

	private String generateRandomString(final int length) {
		StringBuilder stringBuilder = new StringBuilder();

		Random random = new Random();

		for (int i = 0; i < length; i++) {

			stringBuilder.append(ALPHABETS.charAt(random.nextInt(length)));
		}

		return new String(stringBuilder.toString());
	};
}
