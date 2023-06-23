package com.phocos.forum.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer articleId;
	private String articleTitle;
	private String articleContent;
	private String hashtag;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date articlePostTime;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date articleUpdateTime;
	private Integer articleState;
	private Integer articleLikes;
	private Integer articleCollect;

	@PrePersist // 當物件轉換成persist狀態以前，要做的事情放在方法裡面
	public void onCreate() {
		if (articlePostTime == null) {
			articlePostTime = new Date();
		}

	}
}
