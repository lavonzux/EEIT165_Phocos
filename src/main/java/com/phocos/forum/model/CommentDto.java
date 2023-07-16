package com.phocos.forum.model;

import java.util.Date;

public class CommentDto {
	private Integer commentId;

	private String commentContent;
	private Date commentPostTime;
	private Date commentUpdateTime;
	private String memberName;
	private String memberAvatar; // 定義為 String 以存儲 Base64 字串

	public Integer getCommentId() {
		return commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public Date getCommentPostTime() {
		return commentPostTime;
	}

	public Date getCommentUpdateTime() {
		return commentUpdateTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberAvatar() {
		return memberAvatar;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public void setCommentPostTime(Date commentPostTime) {
		this.commentPostTime = commentPostTime;
	}

	public void setCommentUpdateTime(Date commentUpdateTime) {
		this.commentUpdateTime = commentUpdateTime;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setMemberAvatar(String memberAvatar) {
		this.memberAvatar = memberAvatar;
	}

}
