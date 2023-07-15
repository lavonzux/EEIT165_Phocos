package com.phocos.chatroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.member.Member;

@Service
public class PrivateMessageService {
	@Autowired
	private  PrivateMessageRepository privateMessageRepository;

    public List<PrivateMessage> getPrivateMessagesForUser(Member sender,Member receiver) {
        return privateMessageRepository.findBySenderOrReceiver(sender, receiver);
    }
    
    //查詢這個聊天室有多少訊息
    public List<PrivateMessage> findByPrivateChatRoom(PrivateChatRoom privateChatRoom) {
        return privateMessageRepository.findByPrivateChatRoom(privateChatRoom);
    }
    
    //
	//把當前這個聊天室privateChatRoom內的私訊privateMessages的isRead的欄位改成1
//	public String updateMessageIsReadTo1(PrivateChatRoom privateChatRoom) {
//		List<PrivateMessage> privateMessages = privateChatRoom.getPrivateMessages();
//		for(PrivateMessage privateMessage : privateMessages) {
//			Integer isRead = privateMessage.getIsRead();
//			if (condition) {
//				
//			}
//		}
//		
//	}
    
}
