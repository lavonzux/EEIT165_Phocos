package com.phocos.photoService.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity @Table(name="ServiceType")
@Component
@Data
public class ServiceType implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id 
	private String typeName;

	
	@ToString.Exclude
	@OneToMany(mappedBy = "serviceType", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<PhotoService> photoService;


	// ==================== CONSTRUCTOR ====================

	public ServiceType() {
		//System.out.println("New PhotoService constructed!");
	}

	public ServiceType(String serviceType) {
		this.typeName = serviceType;
	}

	


}
