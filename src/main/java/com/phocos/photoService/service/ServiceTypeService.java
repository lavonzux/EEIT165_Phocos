package com.phocos.photoService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.photoService.model.ServiceType;
import com.phocos.photoService.model.ServiceTypeRepository;

@Service
public class ServiceTypeService {

	@Autowired
	private ServiceTypeRepository sTypeRepo;


	public ServiceType createEntry(ServiceType serviceType) {
		return sTypeRepo.save(serviceType);
	}

	

	public ServiceType readEntry(String typeName) {
		Optional<ServiceType> optional = sTypeRepo.findById(typeName);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	
	public List<ServiceType> readAllEntries() {
		return sTypeRepo.findAll();
	}

	
}
