package com.phocos.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	boolean existsByMemberAccount(String memberAccount);
	
	Member findByMemberAccount(String memberAccount);
	
	@Query("SELECT m FROM Member m WHERE m.memberName LIKE %?1%")
    List<Member> searchByMemberName(String keyword);
	
}
