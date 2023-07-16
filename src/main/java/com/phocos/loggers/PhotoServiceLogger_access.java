package com.phocos.loggers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Locale;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.phocos.utils.PrintValueHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class PhotoServiceLogger_access {

	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SimpMessagingTemplate msger;
	
	private static final String LOGGER_FILE_PATH= "./src/main/resources/static/backstage/photoService/photoServiceLogger_accessing.txt";
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd(EE) HH:mm:ss '['O']'").withZone(ZoneId.systemDefault()).withLocale(Locale.TRADITIONAL_CHINESE);
	
	
	
	
	@Pointcut("execution(* com.phocos.photoService.controller.*.goto*(..))")
	public void psController() {
		
	}
	
	@Pointcut("execution(* com.phocos.photoService.controllerRestful.*.goto*(..))")
	public void psRestfulController() {
		
	}
	
	
	
	
	@Before("psController() || psRestfulController()")
	public void before(JoinPoint joinPoint) throws IOException {
		Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
		
		
		String calledMethod = joinPoint.getSignature().getName();
		
		String memberID = "Guest";
		Object memberIDinReq = httpSession.getAttribute("memberID");
		if (memberIDinReq != null) memberID = Integer.toString((int)memberIDinReq); 
		
		
		logger.info("["+calledMethod+"] was called by member: ["+ memberID+"] with below data......");
		
		PrintValueHelper.printSessionAttributesAndValues(httpSession);
		PrintValueHelper.printRequestParamsAndValues(request);
		
		
		// ========== request info =========
		String requestMethod = request.getMethod();
		String remoteAddr = request.getRemoteAddr();
		
		Instant currentTime = Instant.now();
		String currentTimeString = DATE_TIME_FORMATTER.format(currentTime);
		
		// ========== Composing the log line ==========
		String logLine = currentTimeString + " --- " + memberID+" is "+requestMethod.toUpperCase()+"ing "+calledMethod+" from "+remoteAddr;
		System.out.println(logLine);
		
		// ========== Getting & Write into the log file ==========		
		FileWriter logWriter = new FileWriter(new File(LOGGER_FILE_PATH), true);
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
	
	
	@Before("psController() || psRestfulController()")
	public void sendLogToWebsocket(JoinPoint joinPoint) {
		
		String memberID = "Guest";
		String requestMethod = request.getMethod();
		String remoteAddr = request.getRemoteAddr();
		
		String calledMethod = joinPoint.getSignature().getName();
		
		Instant currentTime = Instant.now();
		String currentTimeString = DATE_TIME_FORMATTER.format(currentTime);
		
		String logLine = currentTimeString + " --- " + memberID+" is "+requestMethod.toUpperCase()+"ing "+calledMethod+" from "+remoteAddr;
		
		msger.convertAndSend("/pslog/accessing", logLine);
	}
	
	
}
