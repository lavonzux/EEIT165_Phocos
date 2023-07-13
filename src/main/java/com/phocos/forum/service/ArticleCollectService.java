package com.phocos.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.forum.model.ArticleCollect;
import com.phocos.forum.model.ArticleCollectRepository;

@Service
public class ArticleCollectService {

	@Autowired
	private ArticleCollectRepository articleCollectRepo;

//	---------------------------------------- 把收藏數加到資料庫 ----------------------------------------	
	public void insert(ArticleCollect articleCollect) {
		articleCollectRepo.save(articleCollect);
	}

//	---------------------------------------- 文章收藏查詢 ----------------------------------------	
	public ArticleCollect findByMemberIDAndArticleId(Integer memberID, Integer articleId) {
		ArticleCollect collected = articleCollectRepo.findByMemberMemberIDAndArticleArticleId(memberID, articleId);
		return collected;
	}

//	---------------------------------------- 文章收藏查詢 ----------------------------------------	
	public List<ArticleCollect> findById(Integer articleId) {
		List<ArticleCollect> articleCollectList = articleCollectRepo.findByArticleArticleId(articleId);
		return articleCollectList;
	}

}
