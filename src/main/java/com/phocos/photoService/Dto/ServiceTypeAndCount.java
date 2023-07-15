package com.phocos.photoService.Dto;

import com.phocos.photoService.model.ServiceType;

import lombok.Data;
import lombok.ToString;

@Data
public class ServiceTypeAndCount {

	@ToString.Exclude
	private ServiceType serviceType;

	
	private int count;

	public ServiceTypeAndCount(ServiceType serviceType, int count) {
		this.serviceType = serviceType;
		this.count = count;
	}
}
