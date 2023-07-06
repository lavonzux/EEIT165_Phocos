package com.phocos.member;

import lombok.Data;

@Data
public class MemberProfileDTO {
	
    private Integer profileID;
	
	private String bio;
	
	private Member member;
    
}
