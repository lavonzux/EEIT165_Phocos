package com.phocos.photoService.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phocos.member.Member;
import com.phocos.photoService.Dto.PhotoServiceDto;
import com.phocos.photoService.Dto.PhotoServiceDto_Create;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Table(name="PhotoService")
@Component
@Data
public class PhotoService implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int serviceID;

	private String serviceName;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceType")
	@JsonManagedReference
	private ServiceType serviceType;

	private String servicePrice;

	private String serviceDuration;
	
	private String serviceLocation;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceCreator")
//	@JsonManagedReference
	private Member serviceCreator;
	

	private String serviceDesc;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime updatedOn;
	
	@OneToMany(mappedBy = "photoService", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ReferencePicture> referencePictures;


	// ==================== CONSTRUCTOR ====================

	public PhotoService() {
//		System.out.println("New PhotoService constructed!");
	}
	
	

	/*
	// ==================== GETTERs ====================

	public int getServiceID() {
		return serviceID;
	}
	public String getServiceName() {
		return serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getServicePrice() {
		return servicePrice;
	}
	public String getServiceDuration() {
		return serviceDuration;
	}
	public String getServiceLocation() {
		return serviceLocation;
	}
	public String getServiceCreator() {
		return serviceCreator;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	*/

	public String getFormattedCreatedOn() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
		String formatedCreatedOn = createdOn.format(format);
		
		return formatedCreatedOn;
	}
	
	
	public String getFormattedUpdatedOn() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
		String formatedUpdatedOn = updatedOn.format(format);
		
		return formatedUpdatedOn;
	}
	

	/**
	/* ==================== DEPRICATED MULTI-GETTER ====================
	 * ==================== REPLACED BY HIBERNATE FRAMEWORK ====================
	 * !!! DO NOT CHANGE THE ARRAY UNLESS NESSESARY !!!
	 * @return {@code String[]} which contains value of all fields
	 */
	/*
	public String[] getAllFields() {
		String[] fields = {Integer.toString(serviceID),serviceName,serviceType,servicePrice,serviceDuration,serviceLocation,serviceCreator};
		return fields;
	}


	// ==================== SETTERs ====================
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}
	public void setServiceDuration(String serviceDuration) {
		this.serviceDuration = serviceDuration;
	}
	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	public void setServiceCreator(String serviceCreator) {
		this.serviceCreator = serviceCreator;
	}
	*/



	/**
	 * ==================== MULTI-SETTER ====================
	 * ==================== REPLACED BY HIBERNATE FRAMEWORK ====================
	 *
	 */
	/*
	public void setAll(String serviceName,String serviceType,String servicePrice,String serviceDuration,String serviceLocation,String serviceCreator) {
		this.serviceName = serviceName;
		this.serviceType = serviceType;
		this.servicePrice = servicePrice;
		this.serviceDuration = serviceDuration;
		this.serviceLocation = serviceLocation;
		this.serviceCreator = serviceCreator;
	}
	*/


	// ==================== Utilities ====================
	public boolean dataIsValid() {

		boolean serviceNameValid = (serviceName!=null && serviceName.length()>0 ? true : false );
		boolean serviceTypeValid = (serviceType!=null ? true : false );
		boolean servicePriceValid = (servicePrice!=null && servicePrice.length()>0 ? true : false );
		boolean serviceDurationValid = (serviceDuration!=null && serviceDuration.length()>0 ? true : false );
		boolean serviceLocationValid = (serviceLocation!=null && serviceLocation.length()>0 ? true : false );
		boolean serviceCreatorValid = (serviceCreator!=null ? true : false );

		if (serviceNameValid && serviceTypeValid && servicePriceValid && serviceDurationValid && serviceLocationValid && serviceCreatorValid) {
			return true;
		}
		return false;
	}

	public PhotoServiceDto toDto() {
		return new PhotoServiceDto(this);
	}
	
	
	
	public void encodeRefPicFile() {
		for (ReferencePicture oneRefPic : referencePictures) {
			byte[] pictureFile = oneRefPic.getPictureFile();
			oneRefPic.setPictureFile(Base64.getEncoder().encode(pictureFile));
		}
	}

	// ==================== DEPRICATED Utilities ====================
	/*
	public ArrayList<String> getBeanFields() {

		Field[] declaredFields = this.getClass().getDeclaredFields();
		ArrayList<String> allFields = new ArrayList<>();

		for (Field field : declaredFields) {
			allFields.add(field.getName());
		}

		return allFields;
	}
	*/

}
