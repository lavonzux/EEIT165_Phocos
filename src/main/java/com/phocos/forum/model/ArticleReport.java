package com.phocos.forum.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.phocos.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "articleReport")
public class ArticleReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleReportId;
	private String reportContent;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reportTime;

	private String reportType;

	private Integer reportState;
	
	 public Integer getArticleId() {
	        return article.getArticleId();
	    }

// -------------------- 跟文章關聯 --------------------
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "articleId")
	private Article article;

// -------------------- 跟會員關聯 --------------------
	@ManyToOne
	@JoinColumn(name = "memberID")
	private Member member;

}
