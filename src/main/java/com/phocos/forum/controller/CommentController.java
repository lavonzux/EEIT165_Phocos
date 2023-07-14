package com.phocos.forum.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.Comment;
import com.phocos.forum.service.ArticleService;
import com.phocos.forum.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private ArticleService articleService;

//	@PostMapping("/comment/add")
//	public String createComment(@RequestParam("articleId") Integer articleId,
//			@RequestParam("commentContent") String commentContent, Model model) {
//
//		Comment newComment = new Comment();
//		newComment.setCommentContent(commentContent);
//
//		Article article = articleService.findById(articleId);
//		newComment.setArticle(article);
//
//		Comment comment = commentService.insert(newComment);
//		model.addAttribute("comment", comment);
//		return "redirect:/front/" + articleId;
//	}

	@PostMapping("/comment/add")
	public String createComment(@RequestBody Map<String, Object> payload, Model model) {

	    if (!payload.containsKey("articleId") || !payload.containsKey("commentContent")) {
	        System.out.println("Invalid payload received: " + payload);
	        return "error";
	    }

	    Integer articleId = Integer.parseInt(payload.get("articleId").toString());
	    String commentContent = payload.get("commentContent").toString();

	    Comment newComment = new Comment();
	    newComment.setCommentContent(commentContent);

	    Article article = articleService.findById(articleId);
	    newComment.setArticle(article);

	    Comment comment = commentService.insert(newComment);
	    model.addAttribute("comment", comment);

	    return "redirect:/front/" + articleId;
	}

//	@PostMapping("/comment/add")
//	public String createComment(@RequestParam("articleId") Integer articleId,
//			@RequestParam("parentCommentId") Integer parentCommentId,
//			@RequestParam("commentContent") String commentContent, Model model) {
//
//		Comment newComment = new Comment();
//		newComment.setParentCommentId(parentCommentId);
//		newComment.setCommentContent(commentContent);
//
//		Comment comment = commentService.insert(newComment);
//		model.addAttribute("comment", comment);
//		return "redirect:/front/" + articleId;
//	}

	@PostMapping("/comment/update/{id}")
	public String updateComment(@PathVariable Integer id, @RequestParam("commentContent") String commentContent,
			@RequestParam("articleId") Integer articleId, Model model) {

		Comment updatedComment = new Comment();
		updatedComment.setCommentContent(commentContent);
		updatedComment.setCommentId(id);

		Comment comment = commentService.insert(updatedComment);
		model.addAttribute("comment", comment);
		return "redirect:/front/" + articleId;
	}

	@GetMapping("/comment/delete/{id}")
	public String deleteComment(@PathVariable Integer id, @RequestParam("articleId") Integer articleId) {

		commentService.delete(id);
		return "redirect:/front/" + articleId;
	}

	@GetMapping("/front/{articleId}")
	public String showArticle(@PathVariable("articleId") Integer articleId, Model model) {
		// 加載並添加文章和其評論到模型，然後返回視圖名
		Article article = articleService.findById(articleId);
		model.addAttribute("article", article);
		return "forestage/forum/readArticle"; // 替換為實際的視圖名
	}

}
