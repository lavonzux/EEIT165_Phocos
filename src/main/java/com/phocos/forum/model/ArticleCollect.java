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
@Table(name = "articleCollect")
public class ArticleCollect {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleCollectId;

	private Integer collected;

// -------------------- 跟文章關聯 --------------------
	@ManyToOne
	@JoinColumn(name = "articleId")
	private Article article;

// -------------------- 跟會員關聯 --------------------
	@ManyToOne
	@JoinColumn(name = "memberID")
	private Member member;

}
