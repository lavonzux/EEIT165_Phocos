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
import com.phocos.forum.model.ArticleCollect;
import com.phocos.forum.service.ArticleCollectService;
import com.phocos.forum.service.ArticleService;
import com.phocos.member.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleCollectController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCollectService articleCollectService;

	// 文章收藏
	@PostMapping("/article/{articleId}/collect")
	public ResponseEntity<Map<String, Object>> toggleArticleCollect(@PathVariable Integer articleId, HttpSession session) {
	    try {
	        Member member = (Member) session.getAttribute("member");
	        Article article = articleService.findById(articleId);
	        Map<String, Object> response = new HashMap<>();
	        if (member == null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        } else {
	            ArticleCollect collected = articleCollectService.findByMemberIDAndArticleId(member.getMemberID(), articleId);
	            // 已收藏
	            if (collected != null) {
	                if (collected.getCollected() != 0) {
	                    // 已收藏，取消收藏
	                    collected.setCollected(0);
	                    articleCollectService.insert(collected);
	                    response.put("isCollected", collected.getCollected() == 1);
	                    return new ResponseEntity<>(response, HttpStatus.OK);
	                } else {
	                    // 之前取消過收藏，重新收藏
	                    collected.setCollected(1);
	                    articleCollectService.insert(collected);
	                    response.put("isCollected", collected.getCollected() == 1);
	                    return new ResponseEntity<>(response, HttpStatus.OK);
	                }
	            } else {
	                // 從未收藏，添加收藏
	                ArticleCollect collect = new ArticleCollect();
	                collect.setArticle(article);
	                collect.setMember(member);
	                collect.setCollected(1);
	                articleCollectService.insert(collect);
	                response.put("isCollected", collect.getCollected() == 1);
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	        }
	    } catch (Exception e) {
	        // 输出错误信息
	        e.printStackTrace();
	        // 返回500错误
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
