package com.phocos.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.forum.model.ArticlePic;
import com.phocos.forum.model.ArticlePicRepository;

@Service
public class ArticlePicService {

	@Autowired
	private ArticlePicRepository articlePicRepo;
//	---------------------------------------- 新增文章圖片 ----------------------------------------
	public void insertPic(ArticlePic articlePic) {
//		System.out.println("---------------------------");
//		System.out.println(articlePic);
//		System.out.println("---------------------------");
//		System.out.println(articlePic.getArticlePicId());
//		System.out.println("---------------------------");
        articlePicRepo.save(articlePic);
    }
	

	
}
