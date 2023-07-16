package com.phocos.chatroom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phocos.member.Member;
import com.phocos.member.MemberService;

@Controller
public class PrivateChatRoomController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PrivateMessageRepository privateMessageRepository;

	@Autowired
	private PrivateChatRoomService privateChatRoomService;

	@GetMapping("/privateMessage")
	public String chatRoomPage() {
		return "forestage/chatroom/chat";
	}

	// 根據當前的會員ID去找到這個會員的資料，並透過這個會員資料去找到他有那些聊天室。
	@ResponseBody
	@GetMapping("/chatRooms/{memberID}")
	public List<PrivateChatRoom> getChatRoomsByMember(@PathVariable Integer memberID) {
		Member member = memberService.findById(memberID);
		List<PrivateChatRoom> chatRooms = privateChatRoomService.findChatRoomsByMember(member);
		for (PrivateChatRoom chatRoom : chatRooms) {
			Integer unreadCount = privateChatRoomService.calculateUnreadMessagesCount(chatRoom, memberID);
			chatRoom.setUnreadMessagesCount(unreadCount);
		}
		return chatRooms;

	}

	@ResponseBody
	@GetMapping("/createChatRoom")
	public PrivateChatRoom createChatRoom(@RequestParam Integer currentMemberID,
			@RequestParam Integer receiverMemberID) {
		Member member1 = memberService.findById(currentMemberID);
		Member member2 = memberService.findById(receiverMemberID);

		return privateChatRoomService.createPrivateChatRoom(member1, member2);
	}

	// 進入聊天室，存在了就進去存在的聊天室，不存在就建立聊天室並進入。
	@ResponseBody
	@GetMapping("/privateChatRoom")
	public PrivateChatRoom findChatRoom(@RequestParam Member member1, @RequestParam Member member2) {
		Optional<PrivateChatRoom> optionalChatRoom = privateChatRoomService.findPrivateChatRoom(member1, member2);

		PrivateChatRoom privateChatRoom = optionalChatRoom
				.orElseGet(() -> privateChatRoomService.createPrivateChatRoom(member1, member2));

		return privateChatRoom;
	}

	// 發送訊息
	@MessageMapping("/chat")
	public void sendMessage(MessageDTO message) {

		String content = message.getContent();
		Integer senderID = message.getSenderID();
		Integer receiverID = message.getReceiverID();
		Long privateChatRoomID = message.getPrivateChatRoomID();

		Member receiver = memberService.findById(receiverID);
		Member sender = memberService.findById(senderID);

		PrivateChatRoom privateChatRoom = privateChatRoomService.findById(privateChatRoomID);

		PrivateMessage privateMessageToReceiver = new PrivateMessage();
		privateMessageToReceiver.setSender(sender);
		privateMessageToReceiver.setReceiver(receiver);
		privateMessageToReceiver.setMessage(content);
		privateMessageToReceiver.setTimestamp(LocalDateTime.now());
		privateMessageToReceiver.setPrivateChatRoom(privateChatRoom);
		privateMessageRepository.save(privateMessageToReceiver);
		// 發送訊息到這個私訊聊天室
		messagingTemplate.convertAndSend("/topic/messages/" + privateChatRoomID, message);

		messagingTemplate.convertAndSend("/topic/" + receiverID, message);

	}

}
