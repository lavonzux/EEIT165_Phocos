package com.phocos.loggers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phocos.utils.PrintValueHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class PhotoServiceLogger_desctructive {

	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession httpSession;
	
	
//	private static final String LOGGER_FILE_PATH= "static/backstage/photoService/photoServiceLogger.txt";
	
	
	
	@Pointcut("execution(* com.phocos.photoService.controller.*.do*(..))")
	public void psController() {
		
	}
	
	@Pointcut("execution(* com.phocos.photoService.controllerRestful.*.do*(..))")
	public void psRestfulController() {
		
	}
	
	
	
	
	
	
	
	
	
	@Before("psController() || psRestfulController()")
	public void before(JoinPoint joinPoint) throws IOException {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		
		String calledMethod = joinPoint.getSignature().getName();
		String memberID = "Guest";
		
		
		
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
				Object paramVal = request.getParameter(string);
				System.out.println(paramVal.toString());
//				PrintValueHelper.printFieldsAndValues(paramVal);
				
			} catch (Exception e) {
				System.out.println("fail to convert "+string+" attribute to string");
			}
			
		}
		String remoteAddr = request.getRemoteAddr();
		

		Instant currentTime = Instant.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(EE) HH:mm:ss '['O']'").withZone(ZoneId.systemDefault());
		String currentTimeString = formatter.format(currentTime);
		
		
		
		String logLine = currentTimeString + " --- " + memberID+" is "+requestMethod.toUpperCase()+"ing "+calledMethod+" from "+remoteAddr;
		
		
		String path = "/Users/lavonzux/Documents/EEIT65/SpringBoot/workspace/Phocos/src/main/resources/static/backstage/photoService/photoServiceLogger_destructive.txt";
		
		File loggerFile = new File(path);
		System.out.println(logLine);
		
		
		FileWriter logWriter = new FileWriter(loggerFile, true);
		logWriter.write(logLine);
		logWriter.write("\r\n");
		logWriter.close();
		
		
		
		
		
	}
	
	
	@After("psController() || psRestfulController()")
	public void after(JoinPoint joinPoint) {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		String methodName = joinPoint.getSignature().getName();
		logger.info(methodName+" end......");
		
	}
	
	
	
	
}
