package com.phocos.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.forum.service.CommentService;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
//	@PostMapping("/forum/article/{articleId}/addComment")
	
	
}
