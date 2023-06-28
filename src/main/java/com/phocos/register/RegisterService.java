package com.phocos.register;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.email.EmailService;
import com.phocos.member.Member;
import com.phocos.member.MemberService;

@Service
public class RegisterService {

	private RegisterRepository registerRepository;

	private VerificationCodeRepository verificationCodeRepository;
	
	private MemberService memService;

	private EmailService emailService;

	@Autowired
	public RegisterService(RegisterRepository registerRepository, EmailService emailService,
			VerificationCodeRepository verificationCodeRepository,
			MemberService memService) {
		this.registerRepository = registerRepository;
		this.emailService = emailService;
		this.memService=memService;
		this.verificationCodeRepository = verificationCodeRepository;
	}

	// 註冊完帳號後點擊按鈕來發送驗證碼到信箱
	public String sendVerificationEmail(String memberEmail) {

		String verificationCode = generateVerification();

		emailService.sendVerificationCode(memberEmail, verificationCode);

		return verificationCode;
	}

	// 產生驗證碼的程式
	public String generateVerification() {
		// 生成6位隨機數字驗證碼
		int codeLength = 6;
		String chars = "0123456789";
		StringBuilder verificationCode = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < codeLength; i++) {
			int index = random.nextInt(chars.length());
			verificationCode.append(chars.charAt(index));
		}
		return verificationCode.toString();
	}

	// 驗證是否驗證碼輸入相同
	public boolean verifyCode(String inputCode, String sentCode) {
		return inputCode.equals(sentCode);
	}

	// 實際註冊成功帳號會做的事情
	public void registerMember(RegisterFormDTO formDTO) {

		Member newMember = new Member();
		newMember.setMemberAccount(formDTO.getMemberAccount());
		newMember.setMemberPassword(formDTO.getMemberPassword());
		newMember.setMemberEmail(formDTO.getMemberEmail());
		newMember.setMemberName(formDTO.getMemberName());
		newMember.setMemberGender(formDTO.getMemberGender());
		newMember.setMemberAvatar(formDTO.getMemberAvatar());

		registerRepository.save(newMember);

		// 創建VerificationCode實體
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setMember(newMember);
		verificationCode.setEmail(newMember.getMemberEmail());
		verificationCode.setVerificationCode(generateVerification());

		// 保存VerificationCode實體到資料庫
		verificationCodeRepository.save(verificationCode);

	}
	
}
