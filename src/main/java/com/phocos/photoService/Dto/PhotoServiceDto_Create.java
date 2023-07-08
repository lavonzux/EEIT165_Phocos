package com.phocos.photoService.Dto;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.member.Member;
import com.phocos.member.MemberService;
import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.photoService.model.ServiceType;
import com.phocos.photoService.service.ServiceTypeService;

import lombok.Data;

@Data
public class PhotoServiceDto_Create {
	
	@Autowired
	private MemberService mService;
	
	@Autowired
	private ServiceTypeService sService;
	

	private String serviceName;

	private String serviceType;

	private String servicePrice;

	private String serviceDuration;
	
	private String serviceLocation;

//	private Member serviceCreator;

//	private LocalDateTime createdOn;
	
//	private LocalDateTime updatedOn;
	
	
	// Match the List<ReferencePicture> referencePictures; in DAO;
	private MultipartFile[] inputRefPics;
	
	// Match the Member serviceCreator; in DAO 
	private int serviceCreator;
	
	
	private final String datetimeFormatPattern = "yyyy-MM-dd hh:mm:ss E";
//	private String formatedCreatedOn = formatDatetime2Str(createdOn);
//	private String formatedUpdatedOn = formatDatetime2Str(updatedOn);
	
	
	// ==================== FORMATTED DATETIME GETTER ====================
	
	
	
	
	
	// ==================== CONSTRUCTOR ====================
	public PhotoServiceDto_Create() {
	}
	
	
	
	// ==================== Utilities ====================
	/*
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
	*/
	
	
    public PhotoService toPhotoService () {
        PhotoService returnPS = new PhotoService();
        returnPS.setServiceName(serviceName);
        
		returnPS.setServiceType(new ServiceType(serviceType));
        
        returnPS.setServicePrice(servicePrice);
        returnPS.setServiceDuration(serviceDuration);
        returnPS.setServiceLocation(serviceLocation);
        
        
        Member creatorMember = mService.findById(serviceCreator);
        if (creatorMember != null) {
        	returnPS.setServiceCreator(creatorMember);
		}

        if (inputRefPics != null && inputRefPics.length>0) {
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
	
    /*
	private String formatDatetime2Str(LocalDateTime originDT) {
		if (originDT==null) {
			return "origin datetime is null";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeFormatPattern);
		return originDT.format(formatter);
	}
	*/
}
