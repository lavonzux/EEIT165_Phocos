package com.phocos.forum.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticleLikes;
import com.phocos.forum.service.ArticleLikesService;
import com.phocos.forum.service.ArticleService;
import com.phocos.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleLikesController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleLikesService articleLikesService;

	@PostMapping("/article/{articleId}/likes")
	public ResponseEntity<Map<String, Object>> toggleArticleLike(@PathVariable Integer articleId,
			HttpServletRequest request, HttpSession session) {
		try {
			session = request.getSession();
			Member member = (Member) session.getAttribute("member");

			Article article = articleService.findById(articleId);
			Map<String, Object> response = new HashMap<>();
			if (member == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else {
				ArticleLikes isLiked = articleLikesService.findByMemberIDAndArticleId(member.getMemberID(), articleId);
// ---------------------------------------- 按過讚 ----------------------------------------
				if (isLiked != null) {
// ---------------------------------------- 看按讚狀態去做+-讚數並更改狀態 ----------------------------------------
					if (isLiked.getLiked() != 0) {
// ---------------------------------------- 曾經按過讚 ----------------------------------------
						int likeCount = article.getArticleLikesCount();
						article.setArticleLikesCount(likeCount - 1);
						articleService.insert(article);
						isLiked.setLiked(0);
						articleLikesService.insert(isLiked);
						response.put("likeCount", article.getArticleLikesCount());
						response.put("isLiked", isLiked.getLiked());
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
// ---------------------------------------- 可能曾經按過卻收回讚 ----------------------------------------
						int likeCount = article.getArticleLikesCount();
						article.setArticleLikesCount(likeCount + 1);
						articleService.insert(article);
						isLiked.setArticle(article);
						isLiked.setMember(member);
						isLiked.setLiked(1);
						articleLikesService.insert(isLiked);
						likeCount = article.getArticleLikesCount();
						response.put("likeCount", article.getArticleLikesCount());
						response.put("isLiked", isLiked.getLiked());
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
				} else {
// ---------------------------------------- 沒按過讚 ----------------------------------------
					int likeCount = article.getArticleLikesCount();
					article.setArticleLikesCount(likeCount + 1);
					articleService.insert(article);
					ArticleLikes like = new ArticleLikes();
					like.setArticle(article);
					// System.out.println("Test:"+member);
					like.setMember(member);
					like.setLiked(1);
					articleLikesService.insert(like);
					response.put("likeCount", article.getArticleLikesCount());
					response.put("isLiked", like.getLiked());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
// ---------------------------------------- 返回500錯誤 ----------------------------------------
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
