package com.phocos.photoService.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
	
	
	private List<ReferencePicture> referencePictures = new ArrayList<>();
	
	
	private final String datetimeFormatPattern = "yyyy-MM-dd hh:mm:ss E";
	private String formatedCreatedOn = formatDatetime2Str(createdOn);
	private String formatedUpdatedOn = formatDatetime2Str(updatedOn);
	
	
	
	public PhotoServiceDto(PhotoService photoService) {
		parsePhotoService(photoService);
	}
	
	
	public void parsePhotoService(PhotoService photoService) {
		this.setServiceID(photoService.getServiceID());
		this.setServiceName(photoService.getServiceName());
		this.setServiceType(photoService.getServiceType());
		this.setServicePrice(photoService.getServicePrice());
		this.setServiceDuration(photoService.getServiceDuration());
		this.setServiceLocation(photoService.getServiceLocation());
		this.setServiceCreator(photoService.getServiceCreator());
		this.setCreatedOn(photoService.getCreatedOn());
		this.setUpdatedOn(photoService.getUpdatedOn());
	}
	
	
	private String formatDatetime2Str(LocalDateTime originDT) {
		if (originDT==null) {
			return "origin datetime is null";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeFormatPattern);
		return originDT.format(formatter);
	}
}
