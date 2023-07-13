package com.phocos.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phocos.forum.model.ArticleLikes;
import com.phocos.forum.model.ArticleLikesRepository;

@Transactional
@Service
public class ArticleLikesService {

	@Autowired
	public ArticleLikesRepository articleLikesRepo;

//	---------------------------------------- 把按讚數加到資料庫 ----------------------------------------
	public void insert(ArticleLikes articleLikes) {
		articleLikesRepo.save(articleLikes);
	}

//	---------------------------------------- 查詢該會員是否按過讚 ----------------------------------------
	public ArticleLikes findByMemberIDAndArticleId(Integer memberID, Integer articleId) {
		ArticleLikes liked = articleLikesRepo.findByMemberMemberIDAndArticleArticleId(memberID, articleId);
		return liked;
	}
	
//	---------------------------------------- 尚未了解 ----------------------------------------
	public List<ArticleLikes> findById(Integer articleId) {
		List<ArticleLikes> articleLikesList = articleLikesRepo.findByArticleArticleId(articleId);
		return articleLikesList;
	}
	
	
//	---------------------------------------- 點了會+1 ----------------------------------------
	public int getLikeCount(Integer articleId) {
		List<ArticleLikes> articleLikes = articleLikesRepo.findByArticleArticleId(articleId);
		int likeCount = 0;
		for (ArticleLikes like : articleLikes) {
			if (like.getLiked() == 1) {
				likeCount++;
			}
		}
		return likeCount;
	}

}
