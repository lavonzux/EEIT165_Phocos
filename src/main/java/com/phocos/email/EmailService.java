package com.phocos.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.phocos.utils.CodeUtil;

@Service
public class EmailService {
	
	
	private JavaMailSender mailSender;
	
	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	//註冊發送驗證碼
	public void sendVerificationCode(String recipientEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Verification Code");
        message.setText("這是你的驗證碼:" + verificationCode + "，請點擊下方連結來輸入驗證碼完成帳號的驗證。" + 
                "http://localhost:8080/phocos/register/verifyPage?email="+ recipientEmail );
        mailSender.send(message);
    }
	//重新發送驗證碼
	public void resendVerificationCode(String recipientEmail) {
		SimpleMailMessage message = new SimpleMailMessage();
		//產生隨機驗證碼
		CodeUtil codeUtil = new CodeUtil();
		String verificationCode = codeUtil.generateVerification();
		
		message.setTo(recipientEmail);
		message.setSubject("Verification Code");
		message.setText("這是你的驗證碼:" + verificationCode + "，請點擊下方連結來輸入驗證碼完成帳號的驗證。" + 
				"http://localhost:8080/phocos/register/verifyPage?email="+ recipientEmail );
		mailSender.send(message);
	}
	
}
