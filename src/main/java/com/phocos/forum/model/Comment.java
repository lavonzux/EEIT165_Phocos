package com.phocos.forum.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	private Integer parentCommentId;
	private String commentContent;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentPostTime;
	public Comment() {
	    this.commentPostTime = new Date();
	}
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentUpdateTime;
	
	private Integer commentLikes = 0;
	
	@JsonIgnore
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "articleId")
	private Article article;
	
	@JsonIgnore
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "memberID")
	private Member member;
}
