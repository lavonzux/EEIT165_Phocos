package com.phocos.photoService.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReferencePictureService {

	
	@Autowired
	private ReferencePictureRepository rpRepo;
	
	
	
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
	
	
}
