package com.phocos.studio.util;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;

@Service
public class ShedService {
	@Autowired
	private ShedRepository sRepo;

	public List<Shed> findShedByStudioId(Integer studioID) {
	    return sRepo.findAllByStudioID(studioID);
	}

	//新增取額資料
	@GetMapping("/shed/{id}")
	public Integer getStudioID(Integer shedID) {
	    Optional<Shed> optional = sRepo.findById(shedID);

	    if (optional.isPresent()) {
	        Shed shed = optional.get();
	        return shed.getStudioID();
	    }

	    // If the Shed with the specified ID is not found, return null or an appropriate value
	    return null;
	}
	
	//新增資料
	public void insertShed(Shed shed) {
		sRepo.save(shed);
	}

	//刪除資料
	public void deleteByShedId(Integer shedID) {
		sRepo.deleteById(shedID);
	}

	//取得修改資料
	@GetMapping("/shed/{id}")
	public Shed getById(@PathVariable Integer shedID) {
		Optional<Shed> optional = sRepo.findById(shedID);
		
		if(optional.isPresent()) {
			Shed result = optional.get();
			return result;
		}
		
		Shed shed = new Shed();
		shed.setShedName("沒有這筆資料");
		
		return shed;
	}
	
	//修改資料
	@Transactional
	public Shed updateShedById(@RequestParam int shedID, @RequestParam String shedName, @RequestParam Integer shedSize, @RequestParam Integer shedFee,
	                            @RequestParam String shedFeature, @RequestParam String shedEquip, @RequestParam String shedType,
	                            @RequestParam String shedIntro, @RequestParam Integer studioPicID) {
	    Optional<Shed> optional = sRepo.findById(shedID);
	    
	    if (optional.isPresent()) {
	        Shed shed = optional.get();
	        shed.setShedID(shedID);
	        shed.setShedName(shedName);
	        shed.setShedSize(shedSize);
	        shed.setShedFee(shedFee);
	        shed.setShedFeature(shedFeature);
	        shed.setShedEquip(shedEquip);
	        shed.setShedType(shedType);
	        shed.setShedIntro(shedIntro);
	        shed.setStudioPicID(studioPicID);
	        
	        return shed;
	    }
		
		System.out.println("no update data");
		
		return null;
		
	}
}
