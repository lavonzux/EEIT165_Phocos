package com.phocos.chatroom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phocos.member.Member;

@Repository
public interface PrivateChatRoomRepository extends JpaRepository<PrivateChatRoom, Long> {
	
    Optional<PrivateChatRoom> findByMember1AndMember2(Member member1 , Member member2);
    
    @Query("SELECT r FROM PrivateChatRoom r WHERE r.member1 = :member OR r.member2 = :member")
    List<PrivateChatRoom> findChatRoomsByMember(Member member);
    
    PrivateChatRoom findByPrivateChatRoomID(Long privateChatRoomID);
   
}
