package com.phocos.forum.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticleReport;
import com.phocos.forum.service.ArticleReportService;
import com.phocos.forum.service.ArticleService;
import com.phocos.member.Member;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/article")
public class ArticleReportController {

	@Autowired
	private ArticleReportService articleReportService;

	@Autowired
	private ArticleService articleService;

	@PostMapping("/{articleId}/report")
	public ResponseEntity<Map<String, Object>> reportArticle(@PathVariable Integer articleId,
			@RequestBody ArticleReport report, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		try {
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				response.put("message", "請先登入再進行檢舉");
				return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
			}

			Article article = articleService.findById(articleId);
			if (article == null) {
				response.put("message", "找不到文章");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			// 檢查文章是否存在，以及是否有關聯的會員
			if (article.getMember() == null) {
				response.put("message", "找不到文章的作者");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			if (member.getMemberID().equals(article.getMember().getMemberID())) {
				response.put("message", "不能檢舉自己的文章");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			report.setArticle(article);
			report.setMember(member);
			report.setReportTime(new Date());
			report.setReportState(1); // 1 表示已被檢舉
			articleReportService.insert(report);

			// 檢查 articleReportCount 是否為 null
			if (article.getArticleReportCount() == null) {
				System.out.println("Warning: articleReportCount is null for articleId = " + article.getArticleId());
				article.setArticleReportCount(0);
			}

			article.setArticleReportCount(article.getArticleReportCount() + 1);
			articleService.insert(article);

			response.put("message", "檢舉成功");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
