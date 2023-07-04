package com.phocos.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.forum.model.Article;
import com.phocos.forum.service.ArticleService;

@Controller
public class ArticleBackController {

	@Autowired
	private ArticleService articleService;

//	========== 測試用 ==========
	/*
	 * @GetMapping("/test") public String test() { return
	 * "taptacback/forum/forumBackPage"; }
	 */

//	========== 查全部 ==========

	@GetMapping("/forum/backPage")
	public String forumAdmin(Model model) {
		List<Article> articleList = articleService.findAll();
		model.addAttribute("articleList", articleList);
		return "backstage/forum/forumBackPage";
	}

//	========== 編輯 ==========
	@GetMapping("/forum/edit")
	public String searchForEdit(@RequestParam("articleId") Integer articleId, Model model) {
		Article article = articleService.findById(articleId);
		model.addAttribute("article", article);
		return "backstage/forum/updateArticle";

	}

	@PutMapping("/forum/edit")
	public String editArticle(@RequestParam("articleId") Integer articleId,
			@RequestParam("articleTitle") String articleTitle, @RequestParam("articleContent") String articleContent,
			@RequestParam("hashtag") String hashTag, Model model) {
		Article article = articleService.updateArticleById(articleId, articleTitle, articleContent, hashTag);
		model.addAttribute("article", article);
		return "redirect:/forum/backPage";
	}

//	========== 刪除 ==========
	@DeleteMapping("/forum/delete")
	public String realDeleteForum(@RequestParam("articleId") Integer articleId) {
		articleService.realDelete(articleId);
		return "redirect:/forum/backPage";
	}
	


}
