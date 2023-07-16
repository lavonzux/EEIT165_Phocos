package com.phocos.forum.model;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Data
@Entity
@Table(name = "articlePic")
public class ArticlePic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articlePicId;
	
	@JsonIgnore
	@Lob
	private byte[] articlePic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name = "articleId")
	private Article article;
//	Test
	 public String getBase64Image() {
	        return Base64.getEncoder().encodeToString(articlePic);
	    }
}
	