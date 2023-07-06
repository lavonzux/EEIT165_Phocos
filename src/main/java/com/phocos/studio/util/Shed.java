package com.phocos.studio.util;

import java.util.List;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data
@Table(name = "StudioDetail")
public class Shed {
	@Id
	@Column(name = "shedID")
	private Integer shedID;
	@Column(name = "studioID")
	private Integer studioID;
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
	
	@Nullable
	@OneToMany(mappedBy = "shed")
	private List<StudioPic> studioPics;
	
	public List<StudioPic> getStudioPics() {
		return studioPics;
	}

	public void setStudioPics(List<StudioPic> studioPics) {
		this.studioPics = studioPics;
	}

	public Shed() {
		
	}

	public Integer getShedID() {
		return shedID;
	}

	public void setShedID(Integer shedID) {
		this.shedID = shedID;
	}

	public Integer getStudioID() {
		return studioID;
	}

	public void setStudioID(Integer studioID) {
		this.studioID = studioID;
	}

	public String getShedName() {
		return shedName;
	}

	public void setShedName(String shedName) {
		this.shedName = shedName;
	}

	public Integer getShedSize() {
		return shedSize;
	}

	public void setShedSize(Integer shedSize) {
		this.shedSize = shedSize;
	}

	public Integer getShedFee() {
		return shedFee;
	}

	public void setShedFee(Integer shedFee) {
		this.shedFee = shedFee;
	}

	public String getShedFeature() {
		return shedFeature;
	}

	public void setShedFeature(String shedFeature) {
		this.shedFeature = shedFeature;
	}

	public String getShedEquip() {
		return shedEquip;
	}

	public void setShedEquip(String shedEquip) {
		this.shedEquip = shedEquip;
	}

	public String getShedType() {
		return shedType;
	}

	public void setShedType(String shedType) {
		this.shedType = shedType;
	}

	public String getShedIntro() {
		return shedIntro;
	}

	public void setShedIntro(String shedIntro) {
		this.shedIntro = shedIntro;
	}

	public Integer getStudioPicID() {
		return studioPicID;
	}

	public void setStudioPicID(Integer studioPicID) {
		this.studioPicID = studioPicID;
	}
	
}
