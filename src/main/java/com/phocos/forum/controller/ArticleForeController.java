package com.phocos.forum.controller;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticlePic;
import com.phocos.forum.service.ArticlePicService;
import com.phocos.forum.service.ArticleService;

@Controller
public class ArticleForeController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticlePicService articlePicService;
//		========== 測試有無吃到套版 ==========
//	@GetMapping("/forum/article/mainPage")
//	public String forumMainPage() {
//		return "forestage/forum/forumForePage";
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
			@RequestParam("articlePic") MultipartFile[] pics, Model model) {
		Article article = new Article();
		List<ArticlePic> articlePics = new ArrayList<ArticlePic>();

		article.setArticleTitle(articleTitle);
		article.setArticleContent(articleContent);
		article.setHashtag(hashTag);
		
		// Save article before attempting to save related ArticlePic
	    article = articleService.insert(article);  // assuming you have an articleService that can save an Article
		
		for (MultipartFile pic : pics) {
			try {
				ArticlePic articlePic = new ArticlePic();
				byte[] picByte = pic.getBytes();
				articlePic.setArticlePic(picByte);
				articlePic.setArticle(article);
				articlePics.add(articlePic);
				articlePicService.insertPic(articlePic);
			} catch (IOException e) {
				e.printStackTrace();
			}
			article.setArticlePics(articlePics);
			articleService.insert(article);
		}

		return "redirect:/forum/article/foreMainPage";

	}

//       ========== 新增圖片 ==========

//       ========== 查全部 ==========
	@GetMapping("/forum/article/foreMainPage")
	public String forumMainPage(Model model) {
		List<Article> articleList = articleService.findAll();
		model.addAttribute("articleList", articleList);
		return "forestage/forum/forumForePage";
	}
	
	
//	     ========== 假刪除 ==========
	@PostMapping("/forum/article/delete/(articleId)")
		public String fakeDelete(@RequestParam("articleId") Integer articleId) {
		articleService.fakeDelete(articleId);
		return "redirect:/forum/article/foreMainPage";
	}
	

}
