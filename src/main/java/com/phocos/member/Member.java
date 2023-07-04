package com.phocos.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phocos.login.Login;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

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
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Login> login = new ArrayList<>();

	@JsonManagedReference // 由這邊做JSON序列化
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberPicture> memberPictures = new ArrayList<>();

}
