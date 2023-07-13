package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, Integer> {

	ArticleReport findByMemberMemberIDAndArticleArticleId(Integer memberID, Integer articleId);
	List<ArticleReport> findByArticleArticleId(Integer articleId);
	List< ArticleReport> findAllByMemberMemberID(Integer articleId);
}
