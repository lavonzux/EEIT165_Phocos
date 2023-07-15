package com.phocos.chatroom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.member.Member;
import com.phocos.member.MemberService;

@Service
public class PrivateChatRoomService {
	
	@Autowired
	private PrivateChatRoomRepository privateChatRoomRepository;
	
	@Autowired
	private PrivateMessageService privateMessageService;
	
	
	@Autowired
	private MemberService memberService;
	
	public PrivateChatRoom findById(Long privateChatRoomID) {
		PrivateChatRoom chatRoom = privateChatRoomRepository.findByPrivateChatRoomID(privateChatRoomID);
		
		return chatRoom;
	}
	
    // 計算所有的聊天室內，每個聊天室對登入會員來說有多少來自發信者的未讀訊息。
    public Integer calculateUnreadMessagesCount(PrivateChatRoom chatRoom,Integer currentMemberID) {
        int unreadCount = 0;
        List<PrivateMessage> messages = privateMessageService.findByPrivateChatRoom(chatRoom);
        
        for (PrivateMessage message : messages) {
        	Integer senderMemberID = message.getSender().getMemberID();
        	Integer isRead = message.getIsRead();
        	
            if (isRead == 0 &&  senderMemberID != currentMemberID ) {
                unreadCount++;
            }
        }
        return unreadCount;
    }
	
	// 找尋當前登入的會員的聊天室有哪些
	public List<PrivateChatRoom> findChatRoomsByMember(Member currentMember) {
		List<PrivateChatRoom> chatRooms = privateChatRoomRepository.findChatRoomsByMember(currentMember);
		
		return chatRooms;
	}
	
	// 找尋是否有這個聊天室存在
	public Optional<PrivateChatRoom> findPrivateChatRoom(Member member1 , Member member2){
		return privateChatRoomRepository.findByMember1AndMember2(member1, member2);
	}
	
	//建立聊天室
	public PrivateChatRoom createPrivateChatRoom(Member member1 , Member member2) {
		PrivateChatRoom privateChatRoom = new PrivateChatRoom();
		
		privateChatRoom.setMember1(member1);
		privateChatRoom.setMember2(member2);
		return privateChatRoomRepository.save(privateChatRoom);
	}
}
