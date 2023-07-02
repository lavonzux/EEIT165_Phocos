package com.phocos.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phocos.member.Member;
import com.phocos.member.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private MemberService memService;

	// 註冊帳號時檢查帳號是否存在
	@PostMapping("/register/checkAccount")
	@ResponseBody
	public String checkAccount(@RequestParam String memberAccount) {
		boolean result = memService.checkMemberAccountExists(memberAccount);
		if (result) {
			return "帳號已存在，請更換一個";
		} else {
			return "帳號可使用";
		}

	}

	@PostMapping("/register/verify")
	public String verify(@RequestParam("verificationCode") String inputCode, HttpSession session) {
		String storedCode = (String) session.getAttribute("code");
		Integer memberID = (Integer) session.getAttribute("memberID");
		if (storedCode != null && storedCode.equals(inputCode)) {
			// 驗證碼正確，執行相應的操作

			Member member = memService.findById(memberID);
			member.setAccountStatusId(1);
			memService.updateMemberById(memberID, member);
			// 從 session 中刪除驗證碼
			session.removeAttribute("memberID");
			session.removeAttribute("code");
			return "forestage/register/registerVerify";
		} else {
			// 驗證碼不正確，執行相應的操作

			return null;
		}
	}

	@GetMapping("/register/verifyPage")
	public String verifyPage(HttpSession session) {
		String storedCode = (String) session.getAttribute("code");
		if (storedCode ==null) {
			return "redirect:/";
		}
		return "forestage/register/registerVerify";
	}

	@GetMapping("/register")
	public String register() {
		return "forestage/register/register";
	}

	// 點擊按鈕之後會發送驗證碼訊息到Email，並將驗證碼存在session中
	@PostMapping("/register/sendVerificationCode")
	public ResponseEntity<String> sendMail(@RequestParam String memberEmail, HttpSession session) {
		String code = registerService.sendVerificationEmail(memberEmail);

		session.setAttribute("code", code);
		return ResponseEntity.ok("驗證碼寄送成功！");
	}

	// 根據用戶輸入的驗證碼，要是OK就進到下一步。
	@PostMapping("/register/verifyCode")
	public ResponseEntity<String> verifyCode(@RequestParam("inputCode") String inputCode,
			@RequestParam("verificationCode") String verificationCode) {

		registerService.verifyCode(inputCode, verificationCode);

		return null;
	}

	// 註冊完成後，將註冊資訊存到會員資料表中
	@ResponseBody
	@PostMapping("/register/addMember")
	public void addMember(@RequestBody Member mem, HttpSession session) {
		memService.insert(mem);
		session.setAttribute("memberID", mem.getMemberID());
	}

	@PostMapping("/register/success")
	public ResponseEntity<String> registerUser() {

		return ResponseEntity.ok("redirct:/login");

	}

}
