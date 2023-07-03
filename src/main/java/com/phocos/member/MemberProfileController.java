package com.phocos.member;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberProfileController {

	@Autowired
	private MemberService memberService;

	@GetMapping("/profile/{id}")
	public String insertProfile(@PathVariable("id") Integer memberID, Model model) {
		Member member = memberService.findById(memberID);
		model.addAttribute("member", member);
		return "forestage/member/profile";
	}

	@GetMapping("/profile/edit/{id}")
	public String profileEdit(@PathVariable("id") Integer memberID, Model model) {
		Member member = memberService.findById(memberID);
		model.addAttribute("member", member);
		return "forestage/member/profileEditPage";
	}

	@PostMapping("/profile/edit/upload-avatar")
	public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
		// 检查用户是否已登录
		Integer memberID = (Integer) session.getAttribute("memberID");
		if (memberID == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("會員未登錄");
		}

		// 处理文件上传
		if (!file.isEmpty()) {
			try {
				byte[] avatarData = file.getBytes();

				Member updatedMember = memberService.updateMemberAvatar(memberID, avatarData);

				// 返回成功响应
				return ResponseEntity.ok().body("上傳成功");
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上傳失敗");
			}
		}

		return ResponseEntity.badRequest().body("上傳文件為空");
	}

	@GetMapping("/avatar/{memberId}")
	@ResponseBody
	public ResponseEntity<byte[]> getMemberAvatar(@PathVariable("memberId") Integer memberId) {
		Member member = memberService.findById(memberId);
		if (member != null && member.getMemberAvatar() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // 根据实际情况设置图片类型
			return new ResponseEntity<>(member.getMemberAvatar(), headers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/profile/edited")
	public String updateMember2(@RequestParam("memberID") Integer memberID,
			@ModelAttribute("updateBean") Member updatedMember) {
		memberService.updateMemberById(memberID, updatedMember);
		return "redirect:/profile/"+ memberID;
	}

	
	
	@ResponseBody
	@PutMapping("/profile/edit/{memberID}")
	public ResponseEntity<Member> updateMember(@PathVariable("memberID") Integer memberID, @RequestBody Member updatedMember) {
		Member member = memberService.findById(memberID);
		if (member == null) {
			return ResponseEntity.notFound().build();
		}
		member = memberService.updateMemberById(memberID, updatedMember);
		return ResponseEntity.ok(member);
	}

}