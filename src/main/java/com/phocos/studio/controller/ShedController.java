package com.phocos.studio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.studio.util.Shed;
import com.phocos.studio.util.ShedService;
import com.phocos.studio.util.Studio;
import com.phocos.studio.util.StudioPic;
import com.phocos.studio.util.StudioPicService;
import com.phocos.studio.util.StudioService;



@Controller
public class ShedController {
	@Autowired
	private ShedService sServ;
	@Autowired
	private StudioPicService spServ;
	
	
	//取得所有資料
	@GetMapping("/shed")
	public String showShed(@RequestParam("studioID") Integer studioID, Model model) {
	    List<Shed> sheds = sServ.findShedByStudioId(studioID);
	    model.addAttribute("sheds", sheds);
	    return "backstage/studio/shed";
	}

	//跳到新增頁面
	@GetMapping("/shed/insert")
	public String insertPage(@RequestParam("studioID") Integer studioID, Model model) {
	    Shed shed = new Shed();
	    shed.setStudioID(studioID);
	    System.out.println(studioID);

	    model.addAttribute("shed", shed);
	    return "backstage/studio/InsertShed";
	}
	
//	@GetMapping("/browsestudios/price")
//	public String findByMaxPrice(@RequestParam(name = "price", required = false) String maxPrice, Model model) {
//	    List<Shed> studioSheds = new LinkedList<>();
//	    
//	    if (maxPrice != null && !maxPrice.isEmpty()) {
//	        int price = convertPriceStringToInt(maxPrice);
//	        List<Shed> sheds = sServ.findByPrice(price);
//	        
//	        for (Shed shed : sheds) {
//	            Integer studioID = sServ.findStudioIdByShedId(shed.getShedId());
//	            if (studioID != null) {
//	                studioSheds.add(shed);
//	            }
//	        }
//	    } else {
//	        // 若未指定價位，可以執行其他處理邏輯
//	    }
//	    
//	    List<Studio> studios = ssServ.findBySheds(studioSheds);
//	    model.addAttribute("studios", studios);
//	    return "forestage/studio/studioForeSearch";
//	}
	
	//新增資料
	@PostMapping("/shed/insertData")
	public String insertData(@ModelAttribute(name="StudioDetail") Shed shed) {
	    sServ.insertShed(shed);  // 執行新增資料的邏輯
	    Integer studioID = shed.getStudioID();
	    String redirectUrl = "redirect:/shed?studioID=" + studioID;
	    return redirectUrl;
	}

	//刪除資料
	@DeleteMapping("/shed/delete")
	public String deleteShed(@RequestParam("shedID") Integer shedID) {
	    sServ.deleteByShedId(shedID);
	    Integer studioID = sServ.findStudioIdByShedId(shedID); 
	    String redirectUrl = "redirect:/shed?studioID=" + studioID;
	    return redirectUrl;
	}

//	//取得修改資料
//	@GetMapping("/shed/{id}")
//	public Shed getById(@PathVariable Integer shedID) {
//		Optional<Shed> optional = sServ.findById(shedID);
//		
//		if(optional.isPresent()) {
//			Shed result = optional.get();
//			return result;
//		}
//		
//		Shed shed = new Shed();
//		shed.setShedName("沒有這筆資料");
//		
//		return shed;
//	}
	
	@GetMapping("/shed/edit")
	public String editPage(@RequestParam("shedID") Integer shedID, Model model) {
		Shed shed = sServ.getById(shedID);
		System.out.println("測試有拿到shedID+ "+shedID); 
	    List<StudioPic> sPicsList = spServ.getStudioPicsByShedID(shedID);
	    
	    model.addAttribute("shed", shed);
	    model.addAttribute("sPicsList", sPicsList);
	    System.out.println(sPicsList);
	    
	    for (StudioPic studioPic : sPicsList) {
	        System.out.println("找到的 studioPicID: " + studioPic.getStudioPicID());
	    }
	    
	    return "backstage/studio/UpdateShed";
	}

	
	@PutMapping("/shed/editData")
	public String editPost(@ModelAttribute(name="StudioDetail") Shed shed) {
		System.out.println(shed);
		sServ.updateShedById(	        
		shed.getShedID(),
		shed.getStudioID(),
		shed.getShedName(),
        shed.getShedSize(),
        shed.getShedFee(),
        shed.getShedFeature(),
        shed.getShedEquip(),
        shed.getShedType(),
        shed.getShedIntro()
	 );

	    Integer studioID = shed.getStudioID();
	    String redirectUrl = "redirect:/shed?studioID=" + studioID;
	    return redirectUrl;
	}

}
