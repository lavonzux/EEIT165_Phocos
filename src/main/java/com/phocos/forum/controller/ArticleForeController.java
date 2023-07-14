package com.phocos.forum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticlePic;
import com.phocos.forum.service.ArticlePicService;
import com.phocos.forum.service.ArticleService;
import com.phocos.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleForeController {

	@Autowired
	private ArticleService articleService;


//	---------------------------------------- 測試有無吃到套版 ----------------------------------------
//	@GetMapping("/forum/article/mainPage")
//	public String forumMainPage() {
//		return "forestage/forum/MainPage";
//	}

	@GetMapping("/forum/test")
	public String forumMainPage() {
		return "forestage/forum/test";
	}

//  ---------------------------------------- 最新文章 ----------------------------------------
//	@GetMapping("/forum/article/mainPage")
//	public String forumMainPage(Model model) {
//	    List<Article> articleList = articleService.findAllByOrderByArticlePostTimeDesc();
//	    model.addAttribute("articleList", articleList);
//	    return "forestage/forum/mainPage";
//	}
//	
	@GetMapping("/forum/article/mainPage")
	public String forumMainPage(Model model, @RequestParam(defaultValue = "0") Integer page) {
		Page<Article> articleList = articleService.findAllByOrderByArticlePostTimeDesc(page, 6);
		model.addAttribute("articleList", articleList);
		return "forestage/forum/mainPage";
	}

//	---------------------------------------- 用讚數查全部文章(Ajax) ----------------------------------------
	@GetMapping("/forum/article/hotArticles")
	@ResponseBody
	public ResponseEntity<?> getHotArticles(@RequestParam(defaultValue = "0") Integer page) {
		Page<Article> hotArticles = articleService.findAllByOrderByArticleLikesCountDesc(page, 6);
		return ResponseEntity.ok(hotArticles.getContent());
	}

//  ---------------------------------------- 讀取文章 ----------------------------------------
	@GetMapping("/forum/article/read/{articleId}")
	public String readOne(@PathVariable("articleId") Integer articleId, Model model) {
		Article article = articleService.findById(articleId);
		model.addAttribute("article", article);
		return "forestage/forum/readArticle";
	}

//	---------------------------------------- 抓取圖片讓首頁可以顯示 ----------------------------------------
	@GetMapping("/forum/article/{articleId}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable("articleId") Integer articleId) {
		Article article = articleService.findById(articleId);
		byte[] image = article.getArticlePics().get(0).getArticlePic();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
	}

//	---------------------------------------- 新增 ----------------------------------------
//	這是按了"新增文章"之後會出現的網址
	@GetMapping("/forum/article/add")
	public String addArticle() {
		return "forestage/forum/insertArticle";
	}

//	---------------------------------------- 實際新增 ----------------------------------------
//  這是在新增文章頁面按了"確定"會出現的網址,但我直接跳轉回首頁,所以不會出現
	@PostMapping("/forum/article/insert")
	public String insert(@RequestParam("articleTitle") String articleTitle,
			@RequestParam("articleContent") String articleContent, @RequestParam("hashtag") String hashTag,
			@RequestParam("articlePic") MultipartFile[] pics, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
//		++++++++++++++++++++++++ 對於Hashtag新增的部分 ++++++++++++++++++++++++
		String[] hashtags = hashTag.split(" ");
		if (hashtags.length > 5) {
			model.addAttribute("errorMessage", "Too many hashtags. Maximum is 5.");
			return "redirect:/forum/article/mainPage";
		}

		String processedHashtags = Arrays.stream(hashtags)
//				檢查輸入的 hashtag 是否已經包含 "#" 符號，如果已經包含，那麼就不需要再添加
				.map(hashtag -> hashtag.startsWith("#") ? hashtag : "#" + hashtag).collect(Collectors.joining(" "));

//		String processedHashtags = Arrays.stream(hashtags)
//				.map(hashtag -> "#" + hashtag)
//				.collect(Collectors.joining(" "));

//		++++++++++++++++++++++++ 對於Hashtag新增的部分 ++++++++++++++++++++++++

		Article article = new Article();
		List<ArticlePic> articlePics = new ArrayList<ArticlePic>();

		article.setArticleTitle(articleTitle);
		article.setArticleContent(articleContent);
		article.setHashtag(processedHashtags);
		article.setMember(member);

		// Save article before attempting to save related ArticlePic
		article = articleService.insert(article); // assuming you have an articleService that can save an Article

		for (MultipartFile pic : pics) {
			try {
				ArticlePic articlePic = new ArticlePic();
				byte[] picByte = pic.getBytes();
				articlePic.setArticlePic(picByte);
				articlePic.setArticle(article);
				articlePics.add(articlePic);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		article.setArticlePics(articlePics);
		articleService.insert(article); // Assuming you have an articleService method to update the Article

		return "redirect:/forum/article/mainPage";
	}

//	---------------------------------------- 假刪除 ----------------------------------------
	@PostMapping("/forum/article/delete/(articleId)")
	public String fakeDelete(@RequestParam("articleId") Integer articleId) {
		articleService.fakeDelete(articleId);
		return "redirect:/forum/article/foreMainPage";
	}

//  ---------------------------------------- 顯示首頁查全部(無任何排序) ----------------------------------------
//	@GetMapping("/forum/article/mainPage")
//	public String forumMainPage(Model model) {
//		List<Article> articleList = articleService.findAll();
//		model.addAttribute("articleList", articleList);
//		return "forestage/forum/mainPage";
//	}

//  ---------------------------------------- 舊版查全部 ----------------------------------------
//	@GetMapping("/forum/article/foreMainPage")
//	public String forumMainPage(Model model) {
//		List<Article> articleList = articleService.findAll();
//		model.addAttribute("articleList", articleList);
//		return "forestage/forum/forumForePage";
//	}

}
