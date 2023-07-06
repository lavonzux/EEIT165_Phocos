package com.phocos.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phocos.forum.model.ArticleLikes;
import com.phocos.forum.model.Comment;
import com.phocos.login.Login;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="member")
@Component
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer memberID;
	
	@Column(nullable = false , unique = true)	
    private String memberAccount;
	
	@Column(nullable = false)
    private String memberPassword;
	
	@Column(nullable = false)
    private String memberEmail;
	
	@Column(nullable = false)
    private String memberName;
	
	@Column(nullable = false)
    private String memberGender;
	
    private String memberAvatar;
    
    private int accountStatusId = 0;
    
    @JsonManagedReference // 由這邊做JSON序列化
	@OneToMany(mappedBy = "member" , cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Login> login = new ArrayList<>();
   
    
// -------------------- 文章留言 --------------------
 	@JsonIgnore
 	@JsonManagedReference
 	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
 	private List<Comment> comments = new ArrayList<>(0);
    
// -------------------- 文章按讚 --------------------
 	@JsonIgnore
 	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
 	private List<ArticleLikes> articleLikes = new ArrayList<>();
}
