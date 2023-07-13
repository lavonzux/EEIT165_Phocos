package com.phocos.loggers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.phocos.utils.PrintValueHelper;

import ch.qos.logback.core.joran.action.ParamAction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class PhotoServiceLogger {

	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession httpSession;
	
	
	private static final String LOGGER_FILE_PATH= "static/backstage/photoService/photoServiceLogger.txt";
	
	
	
	@Pointcut("execution(* com.phocos.photoService.controller..*(..))")
	public void psController() { 
		
	}
	
	@Pointcut("execution(* com.phocos.photoService.controllerRestful..*(..))")
	public void psRestfulController() {
		
	}
	
	
	@Before("psController() || psRestfulController()")
	public void before(JoinPoint joinPoint) {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		
		String methodName = joinPoint.getSignature().getName();
		String memberID = "guest account";
		
		
		
		PrintValueHelper.printSessionAttributesAndValues(httpSession);
		
		
		
		
		
		Object memberIDinReq = httpSession.getAttribute("memberID");
		if (memberIDinReq != null) memberID = Integer.toString((int)memberIDinReq); 
		
		
		logger.info(methodName+" was called by member: "+ memberID+"......");
		
		
		
		
		// ========== IP Address =========
		String requestMethod = request.getMethod();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String string = (String) parameterNames.nextElement();
			System.out.print(string + " ...value:...");
			
			try {
				Object attribute = request.getAttribute(string);
				String valueOfObj = String.valueOf(attribute);
				System.out.println(valueOfObj);
			} catch (Exception e) {
				System.out.println("fail to convert "+string+" attribute to string");
			}
			
		}
		String remoteAddr = request.getRemoteAddr();
		
		
		// ========== Final log String ==========
		
//		System.out.printf("MemberID: %s requesting %s from: %s --- ",memberID, requestMethod, remoteAddr);
//		System.out.printf("with parameter: ",parameterNames);
		
		
	}
	
	
	@After("psController() || psRestfulController()")
	public void after(JoinPoint joinPoint) {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		String methodName = joinPoint.getSignature().getName();

		
		
		
		
		try {
			new ClassPathResource(LOGGER_FILE_PATH).getFile();
			
			
			
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(methodName+" end......");
		
	}
	
	
	
	
}
