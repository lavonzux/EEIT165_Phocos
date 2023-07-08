package com.phocos.photoService.controllerRestful;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
