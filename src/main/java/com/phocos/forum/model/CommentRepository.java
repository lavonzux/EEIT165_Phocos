package com.phocos.forum.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Page<Comment> findByArticleArticleId(Integer articleId, Pageable pageable);

	List<Comment> findByArticleArticleId(Integer articleId);
	
	List<Comment> findByArticleArticleIdOrderByCommentPostTimeDesc(Integer articleId);
}
