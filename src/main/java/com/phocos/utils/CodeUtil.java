package com.phocos.utils;

import java.util.Random;

public class CodeUtil {
	
	// 產生驗證碼的程式
	public String generateVerification() {
		// 生成6位隨機數字驗證碼
		int codeLength = 6;
		String chars = "0123456789";
		StringBuilder verificationCode = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < codeLength; i++) {
			int index = random.nextInt(chars.length());
			verificationCode.append(chars.charAt(index));
		}
		return verificationCode.toString();
	}
}
