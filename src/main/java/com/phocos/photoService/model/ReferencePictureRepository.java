package com.phocos.photoService.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReferencePictureRepository extends JpaRepository<ReferencePicture, Integer> {

	public List<ReferencePicture> findAllByPhotoServiceServiceID(Integer serviceID);
	
	
}
