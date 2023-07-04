package com.phocos.login;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.phocos.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="login")
@Component
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer loginID;
	
	@Column(nullable = false)
    private String memberAccount;
	
	@Column(nullable = false)
    private String memberPassword;
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;
    
    private String lastLoginIP;
    
	@JsonBackReference // 不由這邊做JSON序列化
	@ManyToOne
	@JoinColumn(name="memberID")
	private Member member;
	
	@PrePersist
	public void onCreate() {
		if( lastLoginTime == null) {
			lastLoginTime = new Date();
		}
	}
	
    @PreUpdate
    public void onUpdate() {
        this.lastLoginTime = new Date();
    }
   
	
	
	
}
