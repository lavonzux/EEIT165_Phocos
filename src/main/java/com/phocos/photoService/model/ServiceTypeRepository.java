package com.phocos.photoService.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, String> {

	
	public List<ServiceType> findServiceTypeByOrderByTypeIndexAsc();  
	
	
}
