package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
//	---------------------------------------- 用日期查全部文章 ----------------------------------------
	 List<Article> findAllByOrderByArticlePostTimeDesc();
//	---------------------------------------- 用日期查全部文章(可換分頁) ----------------------------------------
	 Page<Article> findAllByOrderByArticlePostTimeDesc(Pageable pageable);
//	---------------------------------------- 用讚數查全部文章(可換分頁) ----------------------------------------
	 Page<Article> findAllByOrderByArticleLikesCountDesc(Pageable pageable);

}
