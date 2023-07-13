package com.phocos.photoService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.PhotoServiceRepository;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.photoService.model.ReferencePictureRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReferencePictureService {

	
	@Autowired
	private ReferencePictureRepository rpRepo;
	
	@Autowired
	private PhotoServiceRepository psRepo;
	
	
	public ReferencePicture createEntry(ReferencePicture pic) {
		ReferencePicture savedPicture = rpRepo.save(pic);
		return savedPicture;
	}
	
	
	
	
	public ReferencePicture readOneEntry(int pictureID) {
		Optional<ReferencePicture> optional = rpRepo.findById(pictureID);
		
		if (optional.isEmpty()) {
			return null;
		}else {
			return optional.get();
		}
	}
	
	
	
	public List<ReferencePicture> readMultiPicture(List<Integer> pictureIDList){
		
		ArrayList<ReferencePicture> pictureList = new ArrayList<>();
		for (Integer pictureID : pictureIDList) {
			Optional<ReferencePicture> optional = rpRepo.findById(pictureID);
			if (optional.isPresent()) {
				pictureList.add(optional.get());
			}
		}
		
		return pictureList;
	}
	
	
	public ReferencePicture readFirstByPhotoServiceID(int serviceID) {
		
		Optional<PhotoService> optional = psRepo.findById(serviceID);
		if (optional.isEmpty()) {
//			System.out.println("Cannot find the PhotoService!");
			return null;
		}
		
		List<ReferencePicture> refPic = rpRepo.findAllByPhotoServiceServiceID(optional.get().getServiceID());
		if (refPic.size()==0) {
//			System.out.println("Cannot find related ref Pics!");
			return null;
		}
		
//		System.out.println("Returning the first ref pic!");
		return refPic.get(0);
		
	}
	
	
	
	public List<ReferencePicture> readAllPicturesByServiceID(int serviceID) {
		
//		System.out.println("checking if "+serviceID+" exist......");
		Optional<PhotoService> optional = psRepo.findById(serviceID);
		if (optional.isEmpty()) {
//			System.out.println("Cannot find the PhotoService!");
			return null;
		}
		
//		System.out.println("looking for Ref Pic IDs for: "+optional.get().getServiceID());
		List<ReferencePicture> refPicsList = rpRepo.findAllByPhotoServiceServiceID(optional.get().getServiceID());
		if (refPicsList.size()==0) {
//			System.out.println("Cannot find related ref Pics!");
			return null;
		}
		
		return refPicsList;
	}
	
	
	public void deleteReferencePicture(int pictureID) {
		Optional<ReferencePicture> optional = rpRepo.findById(pictureID);
		
		if (optional.isPresent()) {
			rpRepo.deleteById(optional.get().getPictureID());			
		}
	}
	
	
	public void deleteReferencePictures(Set<Integer> deleteID) {
		for (Integer onePicID : deleteID) {
			deleteReferencePicture(onePicID);
		}
	}
	
	
	
	
	
}
