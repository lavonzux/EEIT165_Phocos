package com.phocos.utils;

import java.lang.reflect.Field;
import java.util.Enumeration;

import jakarta.servlet.http.HttpSession;

public class PrintValueHelper {

	
	
	
	
	public static void printFieldsAndValues(Object obj) {
		
			Field[] declaredFields = obj.getClass().getDeclaredFields();
			System.out.println("===================== printing all fields and values ===========================");
			for (Field field : declaredFields) {
				field.setAccessible(true);
				System.out.print(field.getName()+" : ");
				try {
					System.out.println( (field.get(obj) == null ? "" : field.get(obj).toString()) );
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			System.out.println("===================== end printing all fields and values ===========================");
	}
	
	
	
	public static void printSessionAttributesAndValues(HttpSession httpSession) {
		Enumeration<String> sessionAttrs = httpSession.getAttributeNames();
		while (sessionAttrs.hasMoreElements()) {
			String attrName = sessionAttrs.nextElement();
			System.out.print(attrName);
			
			System.out.print(" ...: is :... ");
			Object attributeVal = httpSession.getAttribute(attrName);
			System.out.println(attributeVal.toString());
		}
	}	
	
	
	
	
	
}
