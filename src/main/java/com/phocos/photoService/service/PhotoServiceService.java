package com.phocos.photoService.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.member.Member;
import com.phocos.member.MemberRepository;
import com.phocos.photoService.Dto.PhotoServiceDto;
import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.PhotoServiceRepository;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.photoService.model.ServiceTypeRepository;

@Service
@Transactional
public class PhotoServiceService {

	@Autowired
	private PhotoServiceRepository psRepo;
	
	@Autowired
	private ServiceTypeRepository stRepo;

	@Autowired
	private MemberRepository mRepo;
	
	
	
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

	
	public Page<PhotoService> readAllByPage() {
		PageRequest page = PageRequest.of(0, 5, Direction.DESC, "createdOn");
		Page<PhotoService> pages = psRepo.findAll(page);
		return pages; 
	}
	
	
	public Page<PhotoService> readAllByPage(int index, int size) {
		PageRequest page = PageRequest.of(index, size, Direction.DESC, "createdOn");
		Page<PhotoService> pages = psRepo.findAll(page);
		return pages; 
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
			/*
			PhotoService targetPhotoService = optional.get();
			targetPhotoService.setServiceName(updatedPhotoService.getServiceName());
			targetPhotoService.setServiceType(updatedPhotoService.getServiceType());
			targetPhotoService.setServiceType(updatedPhotoService.getServiceType());
			targetPhotoService.setServicePrice(updatedPhotoService.getServicePrice());
			targetPhotoService.setServiceDuration(updatedPhotoService.getServiceDuration());
			targetPhotoService.setServiceLocation(updatedPhotoService.getServiceLocation());
			targetPhotoService.setServiceCreator(updatedPhotoService.getServiceCreator());
			targetPhotoService.setServiceDesc(updatedPhotoService.getServiceDesc());
			return psRepo.save(targetPhotoService);
			 */
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

	
	
	public PhotoService updateEntry(int serviceID, PhotoServiceDto dto) {
		
		Optional<PhotoService> optional = psRepo.findById(serviceID);
		if (optional.isEmpty()) return null; 
		
		PhotoService returnPSB = optional.get();
		
		if (dto.getServiceName()!=null) returnPSB.setServiceName(dto.getServiceName());
		if (dto.getServicePrice()!=null) returnPSB.setServicePrice(dto.getServicePrice());
		
		String serviceDuration = dto.getServiceDuration();
		if (serviceDuration.length() > 0) returnPSB.setServiceDuration(serviceDuration);
		
		if (dto.getServiceLocation()!=null) returnPSB.setServiceLocation(dto.getServiceLocation());
		
		if (dto.getServiceCreatorId()!=null) {
			Optional<Member> creatorMember = mRepo.findById(dto.getServiceCreatorId());
			if (creatorMember.isPresent()) returnPSB.setServiceCreator(creatorMember.get());
		}
		
		if (dto.getInputRefPics() != null && dto.getInputRefPics().length > 0) {
			ArrayList<ReferencePicture> refPics = new ArrayList<ReferencePicture>();
			for (MultipartFile oneInput : dto.getInputRefPics()) {
				ReferencePicture oneRefPic = new ReferencePicture();
				oneRefPic.setPhotoService(returnPSB);
				try {oneRefPic.setPictureFile(oneInput.getBytes());} catch (IOException e){e.printStackTrace();}
				oneRefPic.setPictureName(oneInput.getOriginalFilename());
				refPics.add(oneRefPic);
				returnPSB.setReferencePictures(refPics);
			}
		}
		if (dto.getServiceType()!=null && stRepo.findById(dto.getServiceType()).isPresent() ) {
			returnPSB.setServiceType(stRepo.findById(dto.getServiceType()).get());
		}
		return psRepo.save(returnPSB);
	}
	
	
	/**
	 * Create an PhotoService Entry with DTO, mainly for restful API
	 * @param dto, {@code PhotoServiceDto} input data
	 * @return A PhotoService just created, {@code null} if creation failed
	 */
	public PhotoService createEntry(PhotoServiceDto dto) {
		if (!parentExist(dto)) { return null; }
		
		PhotoService newPSB = new PhotoService();
		newPSB.setServiceName(dto.getServiceName());
		
		
		newPSB.setServiceType(stRepo.findById(dto.getServiceTypeName()).get());
		newPSB.setServicePrice(dto.getServicePrice());
		newPSB.setServiceDuration(dto.getServiceDuration());
		newPSB.setServiceLocation(dto.getServiceLocation());
		
		newPSB.setServiceCreator(mRepo.findById(dto.getServiceCreatorId()).get());
		newPSB.setServiceDesc(dto.getServiceDesc());
		
		
		if (dto.getInputRefPics() != null && dto.getInputRefPics().length > 0) {
			ArrayList<ReferencePicture> refPics = new ArrayList<ReferencePicture>();
			for (MultipartFile oneInput : dto.getInputRefPics()) {
				ReferencePicture oneRefPic = new ReferencePicture();
				oneRefPic.setPhotoService(newPSB);
				try {oneRefPic.setPictureFile(oneInput.getBytes());} catch (IOException e){e.printStackTrace();}
				oneRefPic.setPictureName(oneInput.getOriginalFilename());
				refPics.add(oneRefPic);
			}
		}
		
		
		return newPSB;
	}
	
	
	public boolean parentExist(PhotoServiceDto dto) {
		boolean typeExist = stRepo.findById(dto.getServiceTypeName()).isPresent();
		boolean memberExist = mRepo.findById(dto.getServiceCreatorId()).isPresent();
		
		if (!typeExist) return false;
		if (!memberExist) return false;
		return true;
	}
	
	
	
	public boolean inDB(int serviceID) {return(psRepo.findById(serviceID).isPresent()?true:false);}
	
}

