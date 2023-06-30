package com.phocos.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OldMemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member/profile")
	public String profile() {
		return "forestage/member/profile";
	}
	
	
	
	@GetMapping("/dashboard/member")
	public String processMainAction(Model m) {
		List<Member> members = memberService.findAll();
		System.out.println(members);
		m.addAttribute("members", members);
		return "backstage/member/member";
	}
		
	@GetMapping("/dashboard/member/create")
	public String insertPage() {
		return "backstage/member/CreateMember";
	}
	
	@PostMapping("/dashboard/member/created" )
	public String createMember(@ModelAttribute("members") Member mem2) {
		memberService.insert(mem2);
		return "redirect:/dashboard/member";
	}

	@PostMapping("/dashboard/member/delete")
	public void deleteMember(@RequestParam("memberID") int memberID) {
		memberService.deleteById(memberID);
	}

	@PostMapping("/dashboard/member/update")
	public String updateMember1(@RequestParam("memberID") int memberID, Model m) {
		Member member = memberService.findById(memberID);
		m.addAttribute("member", member);
		return "backstage/member/UpdateMember";
	}

	@PostMapping("/dashboard/member/updated")
	public String updateMember2(@RequestParam("memberID") int memberID,
			@ModelAttribute("updateBean") Member updatedMember) {
		memberService.updateMemberById(memberID, updatedMember);
		return "redirect:/dashboard/member";
	}


}
