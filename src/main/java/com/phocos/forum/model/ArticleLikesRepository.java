package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikesRepository extends JpaRepository<ArticleLikes, Integer> {
	ArticleLikes findByMemberMemberIDAndArticleArticleId(Integer memberID, Integer articleId);
	List<ArticleLikes> findByArticleArticleId(Integer articleId);

}
