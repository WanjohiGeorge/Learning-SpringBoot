package com.master.demo.shared.constants;

import com.master.demo.SpringApplicationContext;
import com.master.demo.security.ApplicationProperties;

public class Constants {
	public final static String HEADER = "Authorization";
	public final static String TOKEN_PREFIX = "Bearer ";
	public final static long AUTH_DURATION = 86400000; // seconds roughly 10 hours/days

	public final static String SIGN_UP_URL = "/users";
	public final static String SIGN_IN_URL ="/users/login";

	public static String getTokenSecret() {
		ApplicationProperties appP = (ApplicationProperties) SpringApplicationContext.getBean("ApplicationProperties");
		return appP.getTokenSecret();
	}
}
