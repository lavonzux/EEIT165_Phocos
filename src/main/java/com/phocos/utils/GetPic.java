package com.phocos.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GetPic {
	public static byte[] convertImageToBinary(String imagePath) throws IOException {
		File file = new File(imagePath);
		FileInputStream fis = new FileInputStream(file);

		byte[] imageBytes = new byte[(int) file.length()];
		fis.read(imageBytes);

		fis.close();

		return imageBytes;
	}

	public static void main(String[] args) {
		try {
			String imagePath = "C:/images/1.jpg";
			byte[] binaryData = convertImageToBinary(imagePath);
			String binaryString = bytesToHexString(binaryData);

			System.out.println("Binary Data: " + binaryString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
}
