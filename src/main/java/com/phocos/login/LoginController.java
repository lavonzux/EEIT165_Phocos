package com.phocos.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(names = "adminName")
public class LoginController {
	
	@GetMapping("/login")
	public String loginPage() {
		return "forestage/loginSystem/loginPage";
	}
	
	
	@PostMapping("/adminLogin")
	public String adminLogin(@RequestParam("memberAccount") String memberAccount,
			@RequestParam("memberPassword") String memberPassword, 
			@SessionAttribute(value = "adminName", required = false) String adminName,
			Model m) {
		
	    if (adminName != null) {
	        return "redirect:/dashboard";
	    }

		if ("admin".equals(memberAccount) && "1234".equals(memberPassword)) {

			m.addAttribute("adminName", memberAccount);

			return "redirect:/dashboard";
		} else {
			return "forestage/loginSystem/login";
		}

	}
	
	@GetMapping("/dashboard")
	public String adminIndex(Model model) {
	    return "/backstage/dashboard";
	}
	
	@GetMapping("/adminlogout")
	public String adminLogout(SessionStatus status) {
		status.setComplete();
		return "redirect:/login";
	}

}
