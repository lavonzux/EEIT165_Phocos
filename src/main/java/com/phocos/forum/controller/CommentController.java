package com.phocos.forum.controller;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.Comment;
import com.phocos.forum.model.CommentDto;
import com.phocos.forum.service.ArticleService;
import com.phocos.forum.service.CommentService;
import com.phocos.member.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private ArticleService articleService;

//	---------------------------------------- 使用DTO新增回覆 ----------------------------------------
	@PostMapping("/comment/add")
	@ResponseBody
	public CommentDto createComment(@RequestBody Map<String, Object> payload, HttpSession session) {
		if (!payload.containsKey("articleId") || !payload.containsKey("commentContent")) {
			System.out.println("Invalid payload received: " + payload);
			throw new RuntimeException("Invalid payload");
		}

		Integer articleId = Integer.parseInt(payload.get("articleId").toString());
		String commentContent = payload.get("commentContent").toString();

		Comment newComment = new Comment();
		newComment.setCommentContent(commentContent);

		Member member = (Member) session.getAttribute("member");
		newComment.setMember(member);

		Article article = articleService.findById(articleId);
		newComment.setArticle(article);

		Comment comment = commentService.insert(newComment);

		CommentDto commentDto = new CommentDto();
		commentDto.setCommentContent(comment.getCommentContent());
		commentDto.setCommentPostTime(comment.getCommentPostTime());
		commentDto.setMemberName(member.getMemberName());
		String avatarBase64 = Base64.getEncoder().encodeToString(member.getMemberAvatar());
		commentDto.setMemberAvatar(avatarBase64);
		commentDto.setCommentId(comment.getCommentId());

		return commentDto;
	}

//	---------------------------------------- 使用DTO更新回覆 ----------------------------------------
	@PutMapping("/comment/update/{id}")
	@ResponseBody
	public CommentDto updateComment(@PathVariable Integer id, @RequestBody Map<String, Object> payload,
			HttpSession session) {
		if (!payload.containsKey("commentContent")) {
			System.out.println("Invalid payload received: " + payload);
			throw new RuntimeException("Invalid payload");
		}

		String commentContent = payload.get("commentContent").toString();

		// 從數據庫找到要更新的留言
		Comment commentToUpdate = commentService.findById(id);
		if (commentToUpdate == null) {
			throw new RuntimeException("Comment not found");
		}
		System.out.println("找到你囉回覆RRRRRR");

		// 更新留言內容
		commentToUpdate.setCommentContent(commentContent);
		// 更新留言時間
		commentToUpdate.setCommentUpdateTime(new Date());
		// 存儲更新的留言
		Comment updatedComment = commentService.update(commentToUpdate);

		// 創建並返回CommentDto對象
		CommentDto commentDto = new CommentDto();
		commentDto.setCommentContent(updatedComment.getCommentContent());
		commentDto.setCommentPostTime(updatedComment.getCommentPostTime());
		commentDto.setCommentUpdateTime(updatedComment.getCommentUpdateTime());
		Member member = updatedComment.getMember();
		commentDto.setMemberName(member.getMemberName());
		String avatarBase64 = Base64.getEncoder().encodeToString(member.getMemberAvatar());
		commentDto.setMemberAvatar(avatarBase64);
		// 設置commentId
		commentDto.setCommentId(updatedComment.getCommentId());
		System.out.println("去更新走走走走走走");

		return commentDto;
	}

//	---------------------------------------- 使用DTO刪除回覆 ----------------------------------------

	@Transactional
	@DeleteMapping("/comment/delete/{commentId}")
	@ResponseBody
	public void deleteComment(@PathVariable("commentId") Integer commentId, HttpSession session) {
		// 從數據庫找到要刪除的留言
		Comment commentToDelete = commentService.findById(commentId);
		if (commentToDelete == null) {
			throw new RuntimeException("Comment not found");
		}

		// 從文章的評論列表中移除該評論
		Article article = commentToDelete.getArticle();
		article.getComments().remove(commentToDelete);

		// 儲存文章的改變
		articleService.insert(article);

		// 刪除留言
		commentService.delete(commentId);

	}
	
	
	
//	@Transactional
//	@DeleteMapping("/comment/delete/{commentId}")
//	@ResponseBody
//	public void deleteComment(@PathVariable("commentId") Integer commentId, HttpSession session) {
//		// 從數據庫找到要刪除的留言
//		Comment commentToDelete = commentService.findById(commentId);
//		if (commentToDelete == null) {
//			throw new RuntimeException("Comment not found");
//		}
//
//		// 刪除留言
//		commentService.delete(commentId);
//	}

//	@GetMapping("/front/{articleId}")
//	public String showArticle(@PathVariable("articleId") Integer articleId, Model model) {
//		Article article = articleService.findById(articleId);
//		model.addAttribute("article", article);
//		return "forestage/forum/readArticle";
//	}

//	@PostMapping("/comment/add")
//	public String createComment(@RequestBody Map<String, Object> payload, Model model, HttpSession session) {
//
//		if (!payload.containsKey("articleId") || !payload.containsKey("commentContent")) {
//			System.out.println("Invalid payload received: " + payload);
//			return "error";
//		}
//
//		Integer articleId = Integer.parseInt(payload.get("articleId").toString());
//		String commentContent = payload.get("commentContent").toString();
//
//		Comment newComment = new Comment();
//		newComment.setCommentContent(commentContent);
//
//		// 從session獲取會員信息
//		Member member = (Member) session.getAttribute("member");
//
//		// 將評論的創建者設置為該會員
//		newComment.setMember(member);
//
//		Article article = articleService.findById(articleId);
//		newComment.setArticle(article);
//
//		Comment comment = commentService.insert(newComment);
//		model.addAttribute("comment", comment);
//
//		return "redirect:/front/" + articleId;
//	}

}
