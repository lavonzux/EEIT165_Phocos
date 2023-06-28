package com.phocos.forum.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	public Article insert(Article article) {
		return articleRepo.save(article);
	}

	public List<Article> findAll() {
		return articleRepo.findAll();
	}

	public void realDelete(Integer articleId) {
		articleRepo.deleteById(articleId);
	}

	@Transactional
	public void fakeDelete(Integer articleId) {
		Optional<Article> optional = articleRepo.findById(articleId);
		if (optional.isPresent()) {
			Article article = optional.get();
			article.setArticleState(0);
			articleRepo.save(article);
		}
	}

	public Article findById(Integer articleId) {
		Optional<Article> optional = articleRepo.findById(articleId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Transactional
	public Article updateArticleById(Integer articleId, String articleTitle, String articleContent, String hashtag) {
		Optional<Article> optional = articleRepo.findById(articleId);

		if (optional.isPresent()) {
			Article article = optional.get();
			article.setArticleTitle(articleTitle); // 需要設定傳入的值，而非原本的值
			article.setArticleContent(articleContent); // 需要設定傳入的值，而非原本的值
			article.setHashtag(hashtag);
			article.setArticleUpdateTime(new Date());
			articleRepo.save(article);
			return article;
		}
		System.out.println("no update data");
		return null;
	}

}
