package com.leo.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Validator {
	protected static final Pattern EMAIL_PATTERN = Pattern.compile("([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)");
	protected static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Z|a-z|0-9|\\_|\\-|\\.]{5,26}$");

	private Validator() {

	}

	public final static boolean isEmail(String email) {
		return EMAIL_PATTERN.matcher(email).find();
	}

	public final static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	public final static boolean isUserName(String userName) {
		if (!isBlank(userName))
			return USERNAME_PATTERN.matcher(userName).find();
		return false;
	}

	public final static boolean isPassword(String password) {
		if(!isBlank(password) && password.length()>=6 && password.length()<=26)
			return isEn(password);
		return false;
	}

	public final static boolean isEn(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < 1 || str.charAt(i) > 255) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < 48 || str.charAt(i) > 57) {
				return false;
			}
		}
		return true;
	}
}
