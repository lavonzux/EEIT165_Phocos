package com.phocos.product.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "lens")
public class Lens{
	
	@Id
	@GeneratedValue(generator = "lens_sequence")
	@GenericGenerator(name = "lens_sequence", strategy = "com.phocos.product.model.LensID")
	@Column(name = "productID")
	private Integer productID;
	
	@Column(name = "lensModel")
	private String lensModel;
	
	@Column(name = "lensBrand")
	private String lensBrand;
	
	@Column(name = "lensPrice")
	private Integer lensPrice;
	
	@Nullable
	@Column(name = "lensMount")
	private String lensMount;

	@Nullable
	@Column(name = "lensFocalLength")
	private String lensFocalLength;
	
	@Nullable
	@Column(name = "lensGroup")
	private String lensGroup;
	
	@Nullable
	@Column(name = "lensOIS")
	private String lensOIS;
	
	@Nullable
	@Column(name = "lensMagnification")
	private String lensMagnification;
	
	@Nullable
	@Column(name = "lensMinFocusDist")
	private String lensMinFocusDist;
	
	@Nullable
	@Column(name = "lensApertureMin")
	private Float lensApertureMin;
	
	@Nullable
	@Column(name = "lensApertureMax")
	private Integer lensApertureMax;
	
	@Nullable
	@Column(name = "lensBlades")
	private String lensBlades;
	
	@Nullable
	@Column(name = "lensFilterSize")
	private String lensFilterSize;
	
	@Nullable
	@Column(name = "lensDims")
	private String lensDims;
	
	@Nullable
	@Column(name = "lensWeight")
	private Integer lensWeight;
	
	@Nullable
	@Column(name = "lensFOV")
	private String lensFOV;
	
	@Nullable
	@Column(name = "lensDrive")
	private String lensDrive;
	
	@Lob
	@Column(name = "lensPhoto")
	private byte[] lensPhoto;

	@Nullable
	@Column(name = "lensStocks")
	private Integer lensStocks;

}