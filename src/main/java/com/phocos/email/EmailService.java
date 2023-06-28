package com.phocos.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	
	private JavaMailSender mailSender;
	
	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendVerificationCode(String recipientEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Verification Code");
        message.setText("Please click the following link to verify your email:\n\n" +
                "http://localhost:8080/phocos/register/verifyPage?email="+ recipientEmail +"&code=" + verificationCode);
        mailSender.send(message);
    }
	
	
}
