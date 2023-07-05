package com.phocos.studio.util;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.photoService.model.PhotoService;

import jakarta.transaction.Transactional;

@Service
public class StudioService {
	@Autowired
	private StudioRepository sRepo;

	//取得所有資料
	public List<Studio> findAll(){
		return sRepo.findAll();
	}
	//取得單筆資料
	public Studio readEntry(int studioID) {
		Optional<Studio> optional = sRepo.findById(studioID);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	//新增資料
	public void insertStudio(Studio studio) {
		sRepo.save(studio);
	}

	//刪除資料
	public void deleteByStudioId(Integer studioID) {
		sRepo.deleteById(studioID);
	}

	//取得修改資料
	@GetMapping("/studio/{id}")
	public Studio getById(@PathVariable Integer studioID) {
		Optional<Studio> optional = sRepo.findById(studioID);
		
		if(optional.isPresent()) {
			Studio result = optional.get();
			return result;
		}
		
		Studio studio = new Studio();
		studio.setStudioName("沒有這筆資料");
		
		return studio;
	}
	
	//修改資料
	@Transactional
	public Studio updateStudioById(@RequestParam int studioID,@RequestParam int memberID, @RequestParam String studioName, @RequestParam String studioAddress, @RequestParam float studioLong,
            @RequestParam float studioLat, @RequestParam String studioPhone, @RequestParam String studioEmail, @RequestParam String studioTime,
            @RequestParam String studioLink, @RequestParam String studioIntro, @RequestParam int studioPicID) {
		Optional<Studio> optional = sRepo.findById(studioID);
		
		if(optional.isPresent()) {
			Studio studio = optional.get();
			studio.setStudioName(studioName);
	        studio.setMemberID(memberID);
	        studio.setStudioAddress(studioAddress);
	        studio.setStudioLong(studioLong);
	        studio.setStudioLat(studioLat);
	        studio.setStudioPhone(studioPhone);
	        studio.setStudioEmail(studioEmail);
	        studio.setStudioTime(studioTime);
	        studio.setStudioLink(studioLink);
	        studio.setStudioIntro(studioIntro);
	        studio.setStudioPicID(studioPicID);
			return studio;
		}
		
		System.out.println("no update data");
		
		return null;
		
	}

}
