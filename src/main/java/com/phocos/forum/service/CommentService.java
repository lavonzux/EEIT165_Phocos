package com.phocos.forum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.forum.model.Comment;
import com.phocos.forum.model.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepo;

//	---------------------------------------- 根據ID找回覆 ----------------------------------------
	public Comment findById(Integer id) {
		Optional<Comment> commentOptional = commentRepo.findById(id);
		if (commentOptional.isPresent()) {
			return commentOptional.get();
		} else {
			throw new RuntimeException("Comment not found with id: " + id);
		}
	}

//	---------------------------------------- 新增回覆 ----------------------------------------
	public Comment insert(Comment comment) {
		return commentRepo.save(comment);
	}

//	---------------------------------------- 更新回覆 ----------------------------------------
	public Comment update(Comment comment) {
		return commentRepo.save(comment);
	}

//	---------------------------------------- 刪除回覆 ----------------------------------------
	public void delete(Integer commentId) {
		commentRepo.deleteById(commentId);
	}

	public List<Comment> findCommentsByArticleId(Integer articleId) {
		return commentRepo.findByArticleArticleId(articleId);
	}
	
	public List<Comment> findByArticleArticleIdOrderByCommentPostTimeDesc(Integer articleId) {
	    return commentRepo.findByArticleArticleIdOrderByCommentPostTimeDesc(articleId);
	}


}
