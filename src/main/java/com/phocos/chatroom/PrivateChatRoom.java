package com.phocos.chatroom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phocos.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "privateChatRoom")
public class PrivateChatRoom {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privateChatRoomID;

    @ManyToOne
    @JoinColumn(name = "member1ID")
    private Member member1;

    @ManyToOne
    @JoinColumn(name = "member2ID")
    private Member member2;
    
    @JsonManagedReference
	@OneToMany(mappedBy = "privateChatRoom", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PrivateMessage> privateMessages = new ArrayList<>();
    
}
