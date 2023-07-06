package com.phocos.photoService.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	
	private List<ReferencePicture> referencePictures;
	
	
    private MultipartFile[] inputRefPics;
	
	private final String datetimeFormatPattern = "yyyy-MM-dd hh:mm:ss E";
	private String formatedCreatedOn = formatDatetime2Str(createdOn);
	private String formatedUpdatedOn = formatDatetime2Str(updatedOn);
	
	
	
	// ==================== CONSTRUCTOR ====================
	public PhotoServiceDto() {
	}
	
	public PhotoServiceDto(PhotoService photoService) {
		parsePhotoService(photoService);
	}
	
	
	// ==================== Utilities ====================
	public void parsePhotoService(PhotoService photoService) {
		this.setServiceID(photoService.getServiceID());
		this.setServiceName(photoService.getServiceName());
		this.setServiceType(photoService.getServiceType().getTypeName());
		this.setServicePrice(photoService.getServicePrice());
		this.setServiceDuration(photoService.getServiceDuration());
		this.setServiceLocation(photoService.getServiceLocation());
		this.setServiceCreator(photoService.getServiceCreator());
		this.setCreatedOn(photoService.getCreatedOn());
		this.setUpdatedOn(photoService.getUpdatedOn());
	}
	
	
    public PhotoService toPhotoService () {
        PhotoService returnPS = new PhotoService();
        returnPS.setServiceID(serviceID);
        returnPS.setServiceName(serviceName);
        returnPS.setServiceType(new ServiceType(serviceType));
        returnPS.setServicePrice(servicePrice);
        returnPS.setServiceDuration(serviceDuration);
        returnPS.setServiceLocation(serviceLocation);
        returnPS.setServiceCreator(serviceCreator);

        if (inputRefPics.length>0) {
        	ArrayList<ReferencePicture> refPics = new ArrayList<>();
        	for (MultipartFile oneRefPic : inputRefPics) {
        		ReferencePicture refPic = new ReferencePicture();
        		refPic.setPhotoService(returnPS);
        		try {
					refPic.setPictureFile(oneRefPic.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
        		refPic.setPictureName(oneRefPic.getOriginalFilename());
        		refPics.add(refPic);
			}
            returnPS.setReferencePictures(refPics);
		}
        return returnPS;
    }
	
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
	
	private String formatDatetime2Str(LocalDateTime originDT) {
		if (originDT==null) {
			return "origin datetime is null";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeFormatPattern);
		return originDT.format(formatter);
	}
}
