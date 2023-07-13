package com.phocos.chatroom;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name ="privatemessage")
public class PrivateMessage {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;
	
	@JsonBackReference
	@ManyToOne()
    @JoinColumn(name = "privateChatRoomID")
    private PrivateChatRoom privateChatRoom;
	
	
    @ManyToOne
    @JoinColumn(name = "senderID")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiverID")
    private Member receiver;

    private String message;
    
    private LocalDateTime timestamp;
    
    
}
