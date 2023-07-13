package com.phocos.photoService.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

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
			System.out.println("===== "+typeName+" found in DB!");
			return optional.get();
		}
		System.out.println("===== "+typeName+" cannot be found in DB!");
		return null;
	}

	
	public List<ServiceType> readAllEntries() {
		return sTypeRepo.findAll();
	}

	
	public List<ServiceType> testRead() {
		return sTypeRepo.findServiceTypeByOrderByTypeIndexAsc();
	}
	
	
	public List<ServiceType> read_Nth_Popular(int topN) {
		List<ServiceType> allTypes = sTypeRepo.findAll();
        Comparator<ServiceType> sortByCount = new Comparator<ServiceType>() {
			@Override
			public int compare(ServiceType type1, ServiceType type2) {
				return ( type2.getPhotoService().size() - type1.getPhotoService().size() );
			}
		};
		allTypes.sort(sortByCount);
		/*
		for (ServiceType oneType : allTypes) {
			int containedCount = oneType.getPhotoService().size();
			System.out.println(oneType.getTypeName()+" 出現了 "+containedCount+" 次!");
		}*/
		List<ServiceType> returnList = allTypes.subList(0, topN);
		return returnList;
	}
	
	
	
	public boolean inDB(String typeName){return sTypeRepo.findById(typeName).isPresent();}
}
