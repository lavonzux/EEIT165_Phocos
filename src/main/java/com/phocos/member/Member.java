package com.phocos.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticleLikes;
import com.phocos.forum.model.ArticleReport;
import com.phocos.forum.model.Comment;
import com.phocos.login.Login;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "member")
@Component
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer memberID;

	private Integer accountStatusId = 0;

	@Column(nullable = false, unique = true)
	private String memberAccount;

	@Column(nullable = false)
	private String memberPassword;

	@Column(nullable = false)
	private String memberEmail;

	@Column(nullable = false)
	private String memberName;

	@Column(nullable = false)
	private String memberGender;

	private String memberProfileBio;
	@Lob
	private byte[] memberAvatar;

  @JsonManagedReference // 由這邊做JSON序列化
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Login> login = new ArrayList<>();

  
  
//	@JsonManagedReference // 由這邊做JSON序列化
//	@JsonIgnore
//	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Login> login = new ArrayList<>();
  
// -------------------- 文章 --------------------
	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Article> article = new ArrayList<>();


    
// -------------------- 文章留言 --------------------
 	@JsonIgnore
 	@JsonManagedReference
 	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
 	@ToString.Exclude
 	private List<Comment> comments = new ArrayList<>(0);
    
// -------------------- 文章按讚 --------------------
 	@JsonIgnore
 	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
 	@ToString.Exclude
 	private List<ArticleLikes> articleLikes = new ArrayList<>();
 	
// -------------------- 文章檢舉 --------------------
 	@JsonIgnore
 	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
 	@ToString.Exclude
 	private List<ArticleReport> articleReport = new ArrayList<>();

	@JsonManagedReference // 由這邊做JSON序列化
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<MemberPicture> memberPictures = new ArrayList<>();

}
