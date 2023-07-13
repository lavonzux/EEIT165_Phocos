package com.phocos.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.websocket.server.PathParam;

@Controller
public class OldMemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/dashboard/memberChart")
	public String memberChart() {
		return "backstage/member/memberChart";
	}
	
	@ResponseBody
	@GetMapping("/searchMemberByMemberName")
	public List<Member> searchMemberByMemberName(
			@RequestParam String keyword){
		if("".equals(keyword)) {
			return null;
		}
		List<Member> searchByMemberName = memberService.searchByMemberName(keyword);
		return searchByMemberName;
	}
	
	
	@GetMapping("/dashboard/member")
	public String dashboardMemberTable(Model m) {
		List<Member> members = memberService.findAll();
		m.addAttribute("members", members);
		return "backstage/member/member";
	}
		
	@GetMapping("/dashboard/member/create")
	public String insertPage() {
		return "backstage/member/CreateMember";
	}
	
	@PostMapping("/dashboard/member/created" )
	public String createMember(@ModelAttribute("members") Member createdMember) {
		memberService.insert(createdMember);
		return "redirect:/dashboard/member";
	}

	@PostMapping("/dashboard/member/delete")
	public void deleteMember(@RequestParam("memberID") Integer memberID) {
		memberService.deleteById(memberID);
	}

	@PostMapping("/dashboard/member/update")
	public String updateMember1(@RequestParam("memberID") Integer memberID, Model m) {
		Member member = memberService.findById(memberID);
		m.addAttribute("member", member);
		return "backstage/member/UpdateMember";
	}

	@PostMapping("/dashboard/member/updated")
	public String updateMember2(@RequestParam("memberID") Integer memberID,
			@ModelAttribute("updateBean") Member updatedMember) {
		memberService.updateMemberById(memberID, updatedMember);
		return "redirect:/dashboard/member";
	}


}
