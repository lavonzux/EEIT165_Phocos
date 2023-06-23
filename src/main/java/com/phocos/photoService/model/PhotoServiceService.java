package com.phocos.photoService.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PhotoServiceService {

	@Autowired
	private PhotoServiceRepository psRepo;


	public PhotoService createEntry(PhotoService photoService) {
		return psRepo.save(photoService);
	}

	
	/**
	 * Find the photoService entry based on given {@code photoServiceID}
	 * @param photoServiceID
	 * @return The entry found, or null if no entry match the id
	 */
	public PhotoService readEntry(int photoServiceID) {
		Optional<PhotoService> optional = psRepo.findById(photoServiceID);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	
	public List<PhotoService> readAllEntries() {
		return psRepo.findAll();
	}

	/**
	 * Find a entry by given {@code photoServiceID} first, 
	 * then update the entry with .save() method if entry was found. 
	 * @param photoServiceID
	 * @param updatedPhotoService
	 * @return
	 */
	public PhotoService updateEntry(int photoServiceID, PhotoService updatedPhotoService) {
		Optional<PhotoService> optional = psRepo.findById(photoServiceID);
		
		if (optional.isEmpty()) {
			return null;
		}else {
			return psRepo.save(updatedPhotoService);
		}
	}

	
	/**
	 * Find a entry by given {@code photoServiceID} first, 
	 * then delete the entry with .delete() method if entry was found. 
	 * 
	 * !!! THIS FUNCTION WILL BECOME SETTING STATUS FIELD IN FUTURE !!!
	 * 
	 * @param photoServiceID
	 * @return boolean value for whether the deletion is successful 
	 */
	public boolean deleteEntry(int photoServiceID) {
		Optional<PhotoService> optional = psRepo.findById(photoServiceID);
		if (optional.isEmpty()) {
			return false;
		}else {			
			psRepo.delete(optional.get());
			return true;
		}
	}

	
	
	
}
