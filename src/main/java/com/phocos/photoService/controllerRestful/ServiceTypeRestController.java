package com.phocos.photoService.controllerRestful;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.photoService.Dto.ServiceTypeAndCount;
import com.phocos.photoService.model.ServiceType;
import com.phocos.photoService.service.ServiceTypeService;

@RestController
public class ServiceTypeRestController {

	@Autowired
	private ServiceTypeService sService;
	
	
	@GetMapping("/serviceType/api/ReadAll")
	public List<ServiceType> getAllServiceTypes() {
		return sService.readAllEntries();
	}
	
	
	@GetMapping("/serviceType/api/testRead")
	public List<ServiceType> testRead() {
		return sService.testRead();
	}
	
	
	@GetMapping("/serviceType/api/popular")
	public List<ServiceType> popular5(@RequestParam(name = "topN", required = false) Integer topN) {
		if(topN == null) topN = 6;
		return sService.read_Nth_Popular(topN);
	}
	
	
	
	
	@GetMapping("/serviceType/api/popularAndCount")
	public List<ServiceTypeAndCount> countAndPopular(@RequestParam(name = "topN", required = false) Integer topN) {
		if(topN == null) topN = 5;
		return sService.readNthPopularWithCount(topN);
	}
	
}
