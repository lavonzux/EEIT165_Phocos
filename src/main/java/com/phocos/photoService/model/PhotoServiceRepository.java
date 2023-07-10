package com.phocos.photoService.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoServiceRepository extends JpaRepository<PhotoService, Integer> {

	public List<PhotoService> findByServiceDeleted(int serviceDeleted);
	
	public Page<PhotoService> findByServiceDeleted(int serviceDeleted, Pageable pageable);
	
}
