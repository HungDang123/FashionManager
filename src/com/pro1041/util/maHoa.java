package com.pro1041.util;

import java.security.MessageDigest;
import java.util.Base64;

public class maHoa {
	public static String toSha1(String text) {
		String salt = "quanlycuahangthoitrang";
		String result = null;
		text += salt;
		try {
			byte[] dataBytes = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			result = Base64.getEncoder().encodeToString(dataBytes);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(toSha1("hungdz"));
	}
}
