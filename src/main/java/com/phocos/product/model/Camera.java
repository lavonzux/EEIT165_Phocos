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
@Table(name = "camera")
public class Camera {

	@Id
	@GeneratedValue(generator = "camera_sequence")
	@GenericGenerator(name = "camera_sequence", strategy = "com.phocos.product.model.CameraID")
	@Column(name = "productID")
	private Integer productID;

	@Column(name = "cameraModel")
	private String cameraModel;

	@Column(name = "cameraBrand")
	private String cameraBrand;

	@Column(name = "cameraPrice")
	private Integer cameraPrice;

	@Nullable
	@Column(name = "cameraSensor")
	private String cameraSensor;

	@Nullable
	@Column(name = "cameraPx")
	private Integer cameraPx;

	@Nullable
	@Column(name = "cameraRecPx")
	private String cameraRecPx;

	@Nullable
	@Column(name = "cameraMount")
	private String cameraMount;

	@Nullable
	@Column(name = "cameraIBIS")
	private String cameraIBIS;

	@Nullable
	@Column(name = "cameraEVF")
	private String cameraEVF;

	@Nullable
	@Column(name = "cameraLCD")
	private String cameraLCD;

	@Nullable
	@Column(name = "cameraFocusSys")
	private String cameraFocusSys;

	@Nullable
	@Column(name = "cameraPhotometry")
	private String cameraPhotometry;

	@Nullable
	@Column(name = "cameraISOMin")
	private Float cameraISOMin;

	@Nullable
	@Column(name = "cameraISOMax")
	private Integer cameraISOMax;

	@Nullable
	@Column(name = "cameraShutter")
	private String cameraShutter;

	@Nullable
	@Column(name = "cameraBurst")
	private String cameraBurst;

	@Nullable
	@Column(name = "cameraMemCard")
	private String cameraMemCard;

	@Nullable
	@Column(name = "cameraBattery")
	private String cameraBattery;

	@Nullable
	@Column(name = "cameraDims")
	private String cameraDims;

	@Nullable
	@Column(name = "cameraWeight")
	private Integer cameraWeight;
	
	@Lob
	@Column(name = "cameraPhoto")
	private byte[] cameraPhoto;
	
	@Nullable
	@Column(name = "cameraStocks")
	private int cameraStocks;


}
