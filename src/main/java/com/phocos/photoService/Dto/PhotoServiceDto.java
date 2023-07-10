package com.phocos.photoService.Dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ReferencePicture;
import lombok.Data;

@Data
public class PhotoServiceDto {

	private int serviceID;

	private String serviceName;

	private String serviceType;

	private String servicePrice;

	private String serviceDuration;
	
	private String serviceLocation;

	private String serviceCreator;
	
	private String serviceDesc;

	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	
	private List<ReferencePicture> referencePictures;
	
	
	
	
	
	// =========================================================================================================
	// ========== Fields holding inputs for the above fields that have relationship with other Entity ==========
	
	// ========== Correlated to serviceType -> ServiceType ==========
	private String serviceTypeName;
	
	// ========== Correlated to serviceCreator -> Member ==========
	private Integer serviceCreatorId;
	
	// ========== Correlated to List<referencPictures> -> ReferencePicture ==========
    private MultipartFile[] inputRefPics;
	
    
    
    // ========== other ==========
	private final String datetimeFormatPattern = "yyyy-MM-dd hh:mm:ss E";
	

	
	// ==================== FORMATTED DATETIME GETTER ====================
	public String getFormattedCreatedOn() { return formatDatetime2Str(createdOn); }
	public String getFormattedUpdatedOn() { return formatDatetime2Str(updatedOn); }

	
	
	// ==================== CONSTRUCTOR ====================
	public PhotoServiceDto() {
		final String EMPTY_INDICATOR = "Data is empty!";
		serviceID = 0;
		serviceName = EMPTY_INDICATOR;
		serviceType = EMPTY_INDICATOR;
		servicePrice = EMPTY_INDICATOR;
		serviceDuration = EMPTY_INDICATOR;
		serviceLocation = EMPTY_INDICATOR;
		serviceCreator = EMPTY_INDICATOR;
		createdOn = LocalDateTime.of(2000, 1, 1, 00, 00);
		updatedOn = LocalDateTime.of(2000, 1, 1, 00, 00);
	}
	
	public PhotoServiceDto(PhotoService photoService) {
		parsePhotoService(photoService);
	}
	
	
	// ==================== Utilities ====================
	public void parsePhotoService(PhotoService photoService) {
		serviceID = photoService.getServiceID();
		serviceName = photoService.getServiceName();
		serviceType = photoService.getServiceType().getTypeName();
		servicePrice = photoService.getServicePrice();
		serviceDuration = photoService.getServiceDuration();
		serviceLocation = photoService.getServiceLocation();
		serviceCreator = photoService.getServiceCreator().getMemberName();
		serviceDesc = photoService.getServiceDesc();
		createdOn = photoService.getCreatedOn();
		updatedOn = photoService.getUpdatedOn();
		referencePictures = photoService.getReferencePictures();
	}
	
	
	
	// Deprecated method for transferring inputMultiPartFile to referencePicture list
	/*
    public void setReferencePicsFromInput() {
		if (inputRefPics.length>0) { System.out.println("No Input reference Pics!"); }
		
		for (MultipartFile oneRefPic : inputRefPics) {
			ReferencePicture refPic = new ReferencePicture();
			try {
				refPic.setPictureFile(oneRefPic.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			refPic.setPictureName(oneRefPic.getOriginalFilename());
			referencePictures.add(refPic);
		}
	}
    */
    
    
    
	public void encodeRefPicFile() {
		if (referencePictures.size()>0) {			
			for (ReferencePicture oneRefPic : referencePictures) {
				byte[] pictureFile = oneRefPic.getPictureFile();
				oneRefPic.setPictureFile(Base64.getEncoder().encode(pictureFile));
			}
		}
	}

    
    
    
	
	private String formatDatetime2Str(LocalDateTime originDT) {
		if (originDT==null) return "origin datetime is null";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeFormatPattern);
		return originDT.format(formatter);
	}
}
