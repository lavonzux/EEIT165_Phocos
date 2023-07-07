package com.phocos.studio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class StudioController {
	@Autowired
	private StudioService sServ;
	
	@Autowired
	private ShedService shServ;
	
	@Autowired
	private StudioPicService spServ;

	//取得所有資料
	@GetMapping("/studios")
	public String findAll(Model model) {
		List<Studio> studios = sServ.findAll();
		
		model.addAttribute("studios",studios);
		return "backstage/studio/Studio";
	}
	
//	//前台取得所有資料
//	@GetMapping("/browsestudios")
//	public String findAllForIndex(Model model) {
//		List<Studio> studios = sServ.findAll();
//		
//		model.addAttribute("studios",studios);
//		return "forestage/studio/studioForeIndex";
//	}
	//前台煥頁
	@GetMapping("/browsestudios/page")
	public String showStudio(@RequestParam(name="p", defaultValue = "1") Integer pageNumber, Model model) {
		Page<Studio> page = sServ.findByPage(pageNumber);
		model.addAttribute("page", page);
		
		
		return "forestage/studio/studioForeIndex";
	}
	
	//前台取得單筆資料
	@GetMapping("/browsestudio")
	public String getStudioByID(@RequestParam("studioID") Integer studioID, Model model) {
		Studio studio = sServ.getById(studioID);
		List<Shed> sheds = shServ.findShedByStudioId(studioID);
		List<StudioPic> sPicsList = spServ.getStudioPicsByStudioID(studioID);
		model.addAttribute("studio", studio);
		model.addAttribute("sheds", sheds);
		model.addAttribute("sPicsList", sPicsList);
	    for (StudioPic studioPic : sPicsList) {
	        System.out.println("找到的 studioPicID: " + studioPic.getStudioPicID());
	    }
		return "forestage/studio/studioForePage";
	}
	
	
	

	//跳到新增頁面
	@GetMapping("/studio/insert")
	public String insertPage() {
		return "backstage/studio/InsertStudio";
	}
	
	//新增資料
	@PostMapping("/studio/insertData")
	public String insert(@ModelAttribute("StudioInfo") Studio studio) {
		sServ.insertStudio(studio);
		return "redirect:/studios";
	}

	//刪除資料
	@DeleteMapping("/studio/delete")
	public String deleteStudio(@RequestParam("studioID") Integer studioID) {
		sServ.deleteByStudioId(studioID);
		return "redirect:/studios";
	}

//	//取得修改資料
//	@GetMapping("/studio/{id}")
//	public Studio getById(@PathVariable Integer studioID) {
//		Optional<Studio> optional = sServ.findById(studioID);
//		
//		if(optional.isPresent()) {
//			Studio result = optional.get();
//			return result;
//		}
//		
//		Studio studio = new Studio();
//		studio.setStudioName("沒有這筆資料");
//		
//		return studio;
//	}
	
	//修改資料
	@GetMapping("/studio/edit")
	public String editPage(@RequestParam("studioID") Integer studioID, Model model) {
		Studio studio = sServ.getById(studioID);
		model.addAttribute("studio", studio);
		return "backstage/studio/UpdateStudio";
	}
	
	@PutMapping("/studio/editData")
	public String editPost(@ModelAttribute(name="StudioInfo") Studio studio) {
		sServ.updateStudioById(studio.getStudioID(), studio.getMemberID(), studio.getStudioName(), studio.getStudioAddress(), studio.getStudioLong(),
			    studio.getStudioLat(), studio.getStudioPhone(), studio.getStudioEmail(), studio.getStudioTime(),
			    studio.getStudioLink(), studio.getStudioIntro(), studio.getStudioPicID());
		
		return "redirect:/studios";
	}

}
