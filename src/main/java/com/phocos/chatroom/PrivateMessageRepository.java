package com.phocos.chatroom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phocos.member.Member;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {
	
    List<PrivateMessage> findBySenderOrReceiver(Member sender, Member receiver);

}
