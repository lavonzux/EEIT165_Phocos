package com.phocos.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.forum.model.Comment;
import com.phocos.forum.service.CommentService;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("comment/add")
	public Comment createComment(@RequestBody Comment comment) {
		return commentService.insert(comment);
	}
	

	@PutMapping("/comment/{id}")
	public Comment updateComment(@PathVariable Integer id, @RequestBody Comment comment) {
		comment.setCommentId(id);
		return commentService.insert(comment);
	}

	@DeleteMapping("/comment/{id}")
	public void deleteComment(@PathVariable Integer id) {
		commentService.delete(id);
	}

}
