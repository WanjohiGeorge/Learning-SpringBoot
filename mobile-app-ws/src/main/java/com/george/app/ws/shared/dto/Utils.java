package com.george.app.ws.shared.dto;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

//make it a component so as to be able to autowire
@Component
public class Utils {
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public String generateUserId(int length)
	{
		return generateRandomString(length);
	}
	private String generateRandomString(int length) {
		StringBuilder stringBuilder = new StringBuilder(length);
		for(int i = 0; i < length; i++) {
			stringBuilder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		
		return new String(stringBuilder);
	}

}
