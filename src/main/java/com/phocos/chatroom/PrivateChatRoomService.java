package com.phocos.chatroom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.member.Member;

@Service
public class PrivateChatRoomService {
	
	@Autowired
	private PrivateChatRoomRepository privateChatRoomRepository;
	
	public PrivateChatRoom findById(Long privateChatRoomID) {
		PrivateChatRoom chatRoom = privateChatRoomRepository.findByPrivateChatRoomID(privateChatRoomID);
		
		return chatRoom;
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
