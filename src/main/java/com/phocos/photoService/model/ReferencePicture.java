package com.phocos.photoService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ReferencePicture")
@Data
public class ReferencePicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pictureID")
	private int pictureID;
	
	
	@Column(name = "pictureName")
	private String pictureName;
	
	
	@Lob
	@Column(name = "pictureFile")
	private byte[] pictureFile;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceID")
	@JsonBackReference
	private PhotoService photoService;
	
}