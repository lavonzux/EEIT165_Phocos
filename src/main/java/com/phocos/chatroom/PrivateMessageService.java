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
}
