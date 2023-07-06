package com.phocos.member;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
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
@Table(name="memberPicture")
@Component
public class MemberPicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
    private Integer pictureID;
	
	@Column(nullable = false)
	private String pictureName;
	
    @Lob
    @Column(nullable = false)
    private byte[] pictureData;
	
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;

    
}
