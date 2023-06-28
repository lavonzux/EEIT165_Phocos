package com.phocos.register;

import lombok.Data;

@Data
public class RegisterFormDTO {

	private Integer memberID;
	
    private String memberAccount;
	
    private String memberPassword;
	
    private String memberEmail;
	
    private String memberName;
	
    private String memberGender;
	
    private String memberAvatar;
}
