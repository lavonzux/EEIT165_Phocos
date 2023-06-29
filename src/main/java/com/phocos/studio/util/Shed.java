package com.phocos.studio.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data
@Table(name = "StudioDetail")
public class Shed {
	@Id
	@Column(name = "shedID")
	private Integer shedID;
	@Column(name = "shedName")
	private String shedName;
	@Column(name = "shedSize")
	private Integer shedSize;
	@Column(name = "shedFee")
	private Integer shedFee;
	@Column(name = "shedFeature")
	private String shedFeature;
	@Column(name = "shedEquip")
	private String shedEquip;
	@Column(name = "shedType")
	private String shedType;
	@Column(name = "shedIntro")
	private String shedIntro;
	@Column(name = "studioPicID")
	private Integer studioPicID;
	
	public Shed() {
		
	}
	
}
