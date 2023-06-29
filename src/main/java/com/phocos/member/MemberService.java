package com.phocos.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepo;
	
	// 新增單筆資料
	public void insert(Member member) {
		memberRepo.save(member);
		
	}
	
	// 帳號是否真的存在
	public boolean checkMemberAccountExists(String memberAccount) {
		boolean result = memberRepo.existsByMemberAccount(memberAccount);
		if (result) {
			return result;
		}else {
			return false;
		}
		
	}
	
	
	// 用ID查詢
	public Member findById(Integer memberID) {
		Optional<Member> optional = memberRepo.findById(memberID);
		if ( optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// 查詢全部
	public List<Member> findAll() {
		return memberRepo.findAll();
	}
	
	@Transactional
	// 修改
	public Member updateMemberById(Integer memberID, Member updateBean) {
		Optional<Member> optional = memberRepo.findById(memberID);
		String memberAccount = updateBean.getMemberAccount();
		String memberPassword = updateBean.getMemberPassword();
		String memberEmail = updateBean.getMemberEmail();
		String memberAvatar = updateBean.getMemberAvatar();
		String memberGender = updateBean.getMemberGender();
		String memberName = updateBean.getMemberName();
		if(optional.isPresent()) {
			Member member = optional.get();
			member.setMemberAccount(memberAccount);
			member.setMemberPassword(memberPassword);
			member.setMemberEmail(memberEmail);
			member.setMemberAvatar(memberAvatar);
			member.setMemberGender(memberGender);
			member.setMemberName(memberName);
			return member;
		}
		return null;
	}
	
	//刪除
	public void deleteById(Integer memberID) {
		memberRepo.deleteById(memberID);
	}
	
	// 分頁查詢
	public Page<Member> findByPage(Integer pageNumber) {
		PageRequest pgb = PageRequest.of(pageNumber - 1, 10, Sort.Direction.ASC, "id");

		Page<Member> page = memberRepo.findAll(pgb);

		return page;
	}

	
}
