package com.phocos.loggers.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PSloggerController {


//	final static String LOGGER_FILE_DESTRUCTIVE = "H:/STS4/workspace/Phocos/src/main/resources/static/backstage/photoService/photoServiceLogger_destructive.txt";
//	final static String LOGGER_FILE_ACCESSING = "H:/STS4/workspace/Phocos/src/main/resources/static/backstage/photoService/photoServiceLogger_accessing.txt";
	
//	final static String LOGGER_FILE_DESTRUCTIVE = "/Users/lavonzux/Documents/EEIT65/SpringBoot/workspace/Phocos/src/main/resources/static/backstage/photoService/photoServiceLogger_destructive.txt";
//	final static String LOGGER_FILE_ACCESSING = "/Users/lavonzux/Documents/EEIT65/SpringBoot/workspace/Phocos/src/main/resources/static/backstage/photoService/photoServiceLogger_accessing.txt";
	
	private static final String LOGGER_FILE_ACCESSING= "./src/main/resources/static/backstage/photoService/photoServiceLogger_accessing.txt";
	private static final String LOGGER_FILE_DESTRUCTIVE= "./src/main/resources/static/backstage/photoService/photoServiceLogger_destructive.txt";
	

	@GetMapping("/photoService/log")
	public String gotoPhotoServiceLog() {
		return "backstage/photoService/Readlog";
	}
	
	@GetMapping("/photoService/logws")
	public String gotoPhotoServiceWebsocketLog() {
		return "backstage/photoService/ReadLogWeb";
	}
	
	
	

	
	@GetMapping("/photoService/api/log/accessing")
	@ResponseBody
	public List<String> readAccessingLog() throws IOException {
		Path path = Paths.get(LOGGER_FILE_ACCESSING);
		List<String> readLine = Files.readAllLines(path);
		
		return readLine;
	}
	
	
	
	@GetMapping("/photoService/api/log/destructive")
	@ResponseBody
	public List<String> readDestructiveLog() throws IOException {
		Path path = Paths.get(LOGGER_FILE_DESTRUCTIVE);
		List<String> readLine = Files.readAllLines(path);
		return readLine;
	}
	
	
}
