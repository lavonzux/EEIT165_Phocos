package com.phocos.loggers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
	
	
	
//	@Pointcut("execution(String com.phocos.photoService.controller..*(..))")
//	public void psControllerDestructive() {
//		
//	}
	
	
	
	
	
	
	@Before("psController() ")
	public void before(JoinPoint joinPoint) throws IOException {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		
		String calledMethod = joinPoint.getSignature().getName();
		String memberID = "guest account";
		
		
		
		PrintValueHelper.printSessionAttributesAndValues(httpSession);
		
		
		
		
		
		Object memberIDinReq = httpSession.getAttribute("memberID");
		if (memberIDinReq != null) memberID = Integer.toString((int)memberIDinReq); 
		
		
		logger.info(calledMethod+" was called by member: "+ memberID+"......");
		
		
		
		
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
		
		
		
		
		
		
		String logLine = memberID+" is "+requestMethod.toUpperCase()+"ing "+calledMethod+" from "+remoteAddr;
		System.out.println(logLine);
		
		File loggerFile = new ClassPathResource(LOGGER_FILE_PATH).getFile();
		System.out.println( loggerFile.isFile() );
		System.out.println( loggerFile.canWrite() );
		
		
		
		FileWriter logWriter = new FileWriter(loggerFile, true);
		logWriter.write(logLine);
		logWriter.close();
		
		
		
		
		// ========== Final log String ==========
		
//		System.out.printf("MemberID: %s requesting %s from: %s --- ",memberID, requestMethod, remoteAddr);
//		System.out.printf("with parameter: ",parameterNames);
		
		
	}
	
	
	@After("psController()")
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
