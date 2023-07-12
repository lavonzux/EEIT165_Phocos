package com.phocos.chatroom;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageDTO {
	
	private String content;
	private String senderName;
	private Integer senderID;
    private Integer receiverID;
    private Long privateChatRoomID;
    private LocalDateTime time = LocalDateTime.now();

}
