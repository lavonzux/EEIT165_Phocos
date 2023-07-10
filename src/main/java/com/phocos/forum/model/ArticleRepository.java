package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	 List<Article> findAllByOrderByArticlePostTimeDesc();
}
