package com.phocos.register;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phocos.member.Member;

public interface RegisterRepository extends JpaRepository<Member, Integer> {
	
	 boolean existsByMemberAccount(String memberAccount);
	
}
