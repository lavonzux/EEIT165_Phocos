package com.phocos.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.forum.model.Comment;
import com.phocos.forum.model.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepo;
//	---------------------------------------- 新增回覆 ----------------------------------------
	public Comment insert(Comment comment) {
		return commentRepo.save(comment);
	}
	
//	---------------------------------------- 刪除回覆 ----------------------------------------
	public void delete(Integer id) {
	    commentRepo.deleteById(id);
	}

}
