package com.phocos.photoService.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.photoService.model.ServiceType;
import com.phocos.photoService.model.ServiceTypeRepository;
import com.phocos.photoService.Dto.ServiceTypeAndCount;

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
		List<ServiceType> returnList = allTypes.subList(0, topN);
		return returnList;
	}
	
	
	public List<ServiceTypeAndCount> readNthPopularWithCount(int topN) {
		ArrayList<ServiceTypeAndCount> returnList = new ArrayList<ServiceTypeAndCount>();
		List<ServiceType> topNservices = read_Nth_Popular(topN);
		
		for (ServiceType oneService : topNservices) {
			ServiceTypeAndCount oneTypeAndCount = new ServiceTypeAndCount(oneService, oneService.getPhotoService().size());
			returnList.add(oneTypeAndCount);
			System.out.println(oneService.getTypeName() +"出現"+ oneService.getPhotoService().size() +"次");
		}
		
		return returnList;
	}
	
	
	
	public boolean inDB(String typeName){return sTypeRepo.findById(typeName).isPresent();}
}
