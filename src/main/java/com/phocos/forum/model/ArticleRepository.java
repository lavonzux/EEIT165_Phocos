package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
//	---------------------------------------- 用日期查全部文章 ----------------------------------------
	 List<Article> findAllByOrderByArticlePostTimeDesc();
//	---------------------------------------- 用日期查全部文章(可換分頁) ----------------------------------------
	 Page<Article> findAllByOrderByArticlePostTimeDesc(Pageable pageable);
//	---------------------------------------- 用讚數查全部文章(可換分頁) ----------------------------------------
	 Page<Article> findAllByOrderByArticleLikesCountDesc(Pageable pageable);

	 
	 Page<Article> findAllByMemberMemberIDOrderByArticlePostTimeDesc(Integer memberID, Pageable pageable);

	 @Query("select a from Article a, ArticleLikes al where al.member.memberID = :memberID and al.liked = :liked and a.articleId = al.article.articleId")
	 Page<ArticleLikes> findAllByMemberMemberIDAndLiked(Integer memberID, Integer liked, Pageable pageable);

//	 @Query("select a from Article a where a.hashtag like %:hashtag%")
//	 Page<Article> findByHashtag(String hashtag, Pageable pageable);

	 
}


