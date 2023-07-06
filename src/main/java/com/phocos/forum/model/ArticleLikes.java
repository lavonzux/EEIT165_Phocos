package com.phocos.forum.model;

import com.phocos.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "articleLikes")
public class ArticleLikes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleLikeId;
	
	@ManyToOne
	@JoinColumn(name="memberID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "articleId")
	private Article article;
	
	private Integer liked;
}
