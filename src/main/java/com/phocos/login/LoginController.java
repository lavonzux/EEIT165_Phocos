package com.phocos.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.phocos.email.EmailService;
import com.phocos.member.Member;
import com.phocos.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/login")
	public String loginPage(HttpSession session) {
		String isLoggedIn = (String) session.getAttribute("isLoggedIn");
		if (isLoggedIn == "true") {
			return "redirect:/";
		}
		return "forestage/loginSystem/loginPage";
	}
	
	@PostMapping("/googleLoginCheck")
	public String googleLoginCheck(
			@RequestParam("gmailPicture") String picture,
			@RequestParam("gmail") String gmail,
			@RequestParam("gmailName") String gmailName,
			HttpSession session
			) {
		session.setAttribute("isLoggedIn", "true");
		session.setAttribute("gmail", gmail);
		session.setAttribute("memberName", gmailName);
		session.setAttribute("memberAvatar", picture);
		
		return "redirect:/";
		
	}
	
	@ResponseBody
	@PostMapping("/login")
	public String loginCheck(
			@RequestParam("memberAccount") String memberAccount,
			@RequestParam("memberPassword") String memberPassword, 
			HttpSession session) {
		
		// 驗證是否為admin帳號
		if ("admin".equals(memberAccount) && "1234".equals(memberPassword)) {

			session.setAttribute("adminName", "管理員1號");

			return "adminLoginSuccess";
		}

		if (memberAccount != null) {
			Member member = memberService.findByMemberAccount(memberAccount);
			Integer status = member.getAccountStatusId();
			
			System.out.println(member.getAccountStatusId());
			
			String memberEmail = member.getMemberEmail();
			Integer memberID = member.getMemberID();
			String memberName = member.getMemberName();
			
			System.out.println(member.getMemberName());
			
			byte[] memberAvatar = member.getMemberAvatar();

			switch (status) {
			// 0為未驗證狀態
			case 0: {
				session.removeAttribute("memberID");
				session.removeAttribute("code");

				// 未驗證狀態，重新發送驗證碼
				emailService.resendVerificationCode(memberEmail);
				
				return "nonverifyLogin";

			}
			// 1為已驗證狀態
			case 1: {
				session.setAttribute("isLoggedIn", "true");
				session.setAttribute("memberAccount", memberAccount);
				session.setAttribute("memberID", memberID);				
				session.setAttribute("memberName", memberName);
				session.setAttribute("member", member);
				
				System.out.println(session.getAttribute("memberName"));
				
				if (memberAvatar != null) {
					session.setAttribute("avatarExist", "avatarExist");					
				}
				// 驗證成功，將資訊存到session
				return "loginSuccess";

			}
			// 2為被封禁的狀態
			case 2: {
				return "banned";
			}

			}

			return null;
		}
		return null;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	
//	@PostMapping("/adminLogin")
//	public String adminLogin(@RequestParam("memberAccount") String memberAccount,
//			@RequestParam("memberPassword") String memberPassword, 
//			@SessionAttribute(value = "adminName", required = false) String adminName,
//			Model m) {
//		
//	    if (adminName != null) {
//	        return "redirect:/dashboard";
//	    }
//
//		if ("admin".equals(memberAccount) && "1234".equals(memberPassword)) {
//
//			m.addAttribute("adminName", memberAccount);
//
//			return "redirect:/dashboard";
//		} else {
//			return "forestage/loginSystem/login";
//		}
//
//	}

	@GetMapping("/dashboard")
	public String dashboardPage(HttpSession session) {
		String adminName = (String) session.getAttribute("adminName");
		if (adminName != null) {
			return "/backstage/dashboard";
		}
		return "redirect:/";
	}

	@GetMapping("/adminlogout")
	public String adminLogout(HttpSession session) {
		session.removeAttribute("adminName");
		return "redirect:/login";
	}

}
