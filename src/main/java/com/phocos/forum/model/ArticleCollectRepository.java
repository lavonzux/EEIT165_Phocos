package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCollectRepository extends JpaRepository<ArticleCollect, Integer> {
	ArticleCollect findByMemberMemberIDAndArticleArticleId(Integer memberID, Integer articleId);
	List<ArticleCollect> findByArticleArticleId(Integer articleId);

}
