package com.phocos.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.forum.model.Article;
import com.phocos.forum.service.ArticleService;

@Controller
public class ArticleForeController {
	
	@Autowired
	private ArticleService articleService;
//		========== 測試有無吃到套版 ==========
//	@GetMapping("/forum/article/mainPage")
//	public String forumMainPage() {
//		return "taptacfore/forum/forumForePage";
//	}
	
	
//		========== 新增 ==========
//	    這是按了"新增文章"之後會出現的網址
	@GetMapping("/forum/article/add")
	public String addArticle() {
		return "forestage/forum/insertArticle";
	}

//	    ========== 新增 ==========
//    這是在新增文章頁面按了"確定"會出現的網址,但我直接跳轉回首頁,所以不會出現
	@PostMapping("/forum/article/insert")
	public String insert(@RequestParam("articleTitle") String articleTitle,
			@RequestParam("articleContent") String articleContent, @RequestParam("hashtag") String hashTag,
			Model model) {
		Article article = new Article();
		article.setArticleTitle(articleTitle);
		article.setArticleContent(articleContent);
		article.setHashtag(hashTag);
		
		articleService.insert(article);
		return "redirect:/forum/article/foreMainPage";

	}
	
//       ========== 查全部 ==========
	@GetMapping("/forum/article/foreMainPage")
	public String forumMainPage(Model model) {
		List<Article> articleList = articleService.findAll();
		model.addAttribute("articleList", articleList);
		return "forestage/forum/forumForePage";
	}
	
}
