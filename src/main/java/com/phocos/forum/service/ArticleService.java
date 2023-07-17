package com.phocos.forum.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phocos.forum.model.Article;
import com.phocos.forum.model.ArticleLikes;
import com.phocos.forum.model.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepo;

//	---------------------------------------- 新增文章 ----------------------------------------
	public Article insert(Article article) {
		return articleRepo.save(article);
	}

//	---------------------------------------- 查全部文章(無排序) ----------------------------------------
	public List<Article> findAll() {
		return articleRepo.findAll();
	}
	
//	---------------------------------------- 用日期查全部文章 ----------------------------------------
	public List<Article> findAllByOrderByArticlePostTimeDesc() {
		return articleRepo.findAllByOrderByArticlePostTimeDesc();
	}

//	---------------------------------------- 用日期查全部文章(可換分頁) ----------------------------------------
	public Page<Article> findAllByOrderByArticlePostTimeDesc(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return articleRepo.findAllByOrderByArticlePostTimeDesc(pageable);
	}

//	---------------------------------------- 用讚數查全部文章(可換分頁) ----------------------------------------	
	public Page<Article> findAllByOrderByArticleLikesCountDesc(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return articleRepo.findAllByOrderByArticleLikesCountDesc(pageable);
	}

//	---------------------------------------- 真刪除 ----------------------------------------
	public void realDelete(Integer articleId) {
		articleRepo.deleteById(articleId);
	}

//	---------------------------------------- 假刪除 ----------------------------------------
	@Transactional
	public void fakeDelete(Integer articleId) {
		Optional<Article> optional = articleRepo.findById(articleId);
		if (optional.isPresent()) {
			Article article = optional.get();
			article.setArticleState(0);
			articleRepo.save(article);
		}
	}

//	---------------------------------------- 單筆查詢並更新 ----------------------------------------
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

//	新的更新
	public void update(Article updatedArticle) {
		// Find the article in the database
		Optional<Article> articleOpt = articleRepo.findById(updatedArticle.getArticleId());

		if (articleOpt.isPresent()) {
			Article article = articleOpt.get();

			// Update the article details
			article.setArticleTitle(updatedArticle.getArticleTitle());
			article.setArticleContent(updatedArticle.getArticleContent());
			article.setHashtag(updatedArticle.getHashtag());
			article.setArticlePics(updatedArticle.getArticlePics());

			// Save the updated article
			articleRepo.save(article);
		} else {
			// The article was not found
			throw new EntityNotFoundException(
					"The article with ID " + updatedArticle.getArticleId() + " was not found.");
		}
	}

//	hashtag查詢
//	public Page<Article> searchArticlesByHashtag(String hashtag, Pageable pageable) {
//		return articleRepo.findByHashtag(hashtag, pageable);
//	}

// 先隔開一下
	public Page<Article> findAllByMemberIDOrderByArticlePostTimeDesc(Integer memberID, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return articleRepo.findAllByMemberMemberIDOrderByArticlePostTimeDesc(memberID, pageable);
	}

	public Page<ArticleLikes> findLikedArticlesByMemberID(Integer memberID, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return articleRepo.findAllByMemberMemberIDAndLiked(memberID, 1, pageable);
	}

}
