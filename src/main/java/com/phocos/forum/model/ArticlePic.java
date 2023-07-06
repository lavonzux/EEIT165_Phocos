package com.phocos.forum.model;

import jakarta.persistence.Entity;
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
	
	@Lob
	private byte[] articlePic;
	
	@ManyToOne
	@JoinColumn(name = "articleId")
	private Article article;
}
	