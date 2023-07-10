package com.phocos.studio.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.studio.util.Studio;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.studio.util.Shed;
import com.phocos.studio.util.StudioPic;
import com.phocos.studio.util.StudioService;
import com.phocos.studio.util.ShedService;
import com.phocos.studio.util.StudioPicService;

@Controller
public class StudioPicController {

	private static final String NOT_FOUND_IMAGE_PATH = "static/backstage/photoService/Notfound.jpg";
	
	@Autowired
	private StudioService sService;
	
	@Autowired
	private ShedService shService;
	
	@Autowired
	private StudioPicService spService;
	
	//上傳圖片
//	@PostMapping("/studioPic/upload")
//	public ResponseEntity<String> uploadPhotoAction(
//	        @RequestParam(name = "studioID") Integer studioID,
//	        @RequestParam(name = "shedID") Integer shedID,
//	        @RequestParam(name = "studioPicName") String studioPicName,
//	        @RequestParam(name = "studioPicFile") MultipartFile studioPicFile) throws IOException {
//
//	    try {
//			Studio studio = sService.getById(studioID);
//			Shed shed = shService.getById(shedID);
//			
//			StudioPic StudioPic = new StudioPic();
//			StudioPic.setStudioPicFile(studioPicFile.getBytes());
//			StudioPic.setStudioPicName(studioPicName);
//			StudioPic.setStudioID(studio);
//			StudioPic.setShedID(shed);
//			StudioPic.setStudioPicName(studioPicName);
//			
//			spService.createEntry(StudioPic);
//
//	        // 返回成功的响应数据
//	        return ResponseEntity.ok().body(success);
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        // 返回错误的响应数据
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
//	    }
//	}
	@PostMapping("/studioPic/upload")
	public String uploadPhotoAction(
			@RequestParam(name = "studioID") Integer studioID,
			@RequestParam(name = "shedID") Integer shedID,
			@RequestParam(name = "studioPicName") String studioPicName,
			@RequestParam(name = "studioPicFile") MultipartFile studioPicFile) throws IOException {
	
		try {
			
			Studio studio = sService.getById(studioID);
			Shed shed = shService.getById(shedID);
			
			StudioPic StudioPic = new StudioPic();
			StudioPic.setStudioPicFile(studioPicFile.getBytes());
			StudioPic.setStudioPicName(studioPicName);
			StudioPic.setStudio(studio);
			StudioPic.setShedID(shed);
			StudioPic.setStudioPicName(studioPicName);
			
			spService.createEntry(StudioPic);
			
		    String redirectUrl = "redirect:/shed/edit?shedID=" + shedID;
		    return redirectUrl;
		} catch (IOException e) {
			e.printStackTrace();
			return "there is somthing wrong";
		}
	}
	
	//ID倒出圖片
	@GetMapping("/studioPic/{studioPicID}")
	@ResponseBody
	public ResponseEntity<byte[]> getStudioPicById(@PathVariable("studioPicID") int studioPicID) {
		
		StudioPic result = spService.readOneEntry(studioPicID);
		System.out.println("目前有找到"+ studioPicID);
		byte[] studioPicFile=null;
		if (result != null) studioPicFile=result.getStudioPicFile();
		System.out.println("圖片檔案"+ studioPicFile);
		
		
		HttpHeaders photoHeader=new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(studioPicFile, photoHeader, HttpStatus.OK);
	}
	
//	@GetMapping("/studioPic/showImage/{shedID}")
//	public String showImage(@PathVariable Integer shedID, Model model) {
//	    List<StudioPic> studioPics = spService.findPicByShedID(shedID);
//	    Shed shed = shService.getById(shedID);
//
//	    if (shed == null) {
//	        System.out.println("Cannot find the Shed!");
//	        return "redirect:/error-page"; // 如果找不到Shed，重定向到错误页面
//	    }
//
//	    model.addAttribute("sPicsList", studioPics);
//	    model.addAttribute("shed", shed);
//	    System.out.println("你有成功輸出"+studioPics);
//
//	    return "backstage/studio/UpdateShed";
//	}
	
//	@GetMapping("/photo/listAllPhoto")
//	public String getAllPhoto(Model model) {
//		List<GoodPhoto> list = gpService.listGoodPhoto();
//		model.addAttribute("photoList", list);
//		return "photo/listPhoto";
//	}
//
//@GetMapping("/downloadImage/{id}")
//	public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id){
//		GoodPhoto photo1 = gpService.getPhotoById(id);
//		byte[] photoFile = photo1.getPhotoFile();
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.IMAGE_JPEG);
//		                                // 檔案, header, HttpStatus
//		return new ResponseEntity<byte[]>(photoFile, headers, HttpStatus.OK);
//	}
	
	
	//取得第一張照片
	@GetMapping("/studioPic/indexPic/{studioID}")
	public ResponseEntity<byte[]> getPSStudioPic(@PathVariable(name = "studioID") int studioID) throws IOException {
	    StudioPic result = spService.readFirstByStudioID(studioID);
	    byte[] studioPicFile = null;

	    if (result != null) {
	        studioPicFile = result.getStudioPicFile();
	    } else {
	        // Handle the case when StudioPic is not found
	        // For example, return an appropriate error response
	        return ResponseEntity.notFound().build();
	    }

	    HttpHeaders photoHeader = new HttpHeaders();
	    photoHeader.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(studioPicFile, photoHeader, HttpStatus.OK);
	}
	//刪除照片
	@DeleteMapping("/studioPic/delete")
	public String deleteStudioPic(@RequestParam("studioPicID") Integer studioPicID,
	                              @RequestParam("shedID") Integer shedID) {
	    System.out.println("要刪除" + studioPicID);
	    spService.deleteByStudioPicID(studioPicID);
	    
	    String redirectUrl = "redirect:/shed/edit?shedID=" + shedID;
	    return redirectUrl;
	}
	

}
	
//	@GetMapping("/studioPic/upload")
//	public String getStudioPic() {	
//		return "backstage/studio/updateShed";
//	}
//	
//	
//	
//	@PostMapping("/studioPic/upload")
//	@ResponseBody
//	public String uploadStudioPic(
//			@RequestParam(name = "studioID") Integer studioID,
//			@RequestParam(name = "shedID") Integer shedID,
//			@RequestParam(name = "studioPicName") String studioPicName,
//			@RequestParam(name = "studioPicFile") MultipartFile studioPicFile) throws IOException {
//		
//		Studio queryStudio = sService.readEntry(studioID);
//		
//		StudioPic newStudioPic = new StudioPic();
//		newStudioPic.setStudio(queryStudio);
//		newStudioPic.setStudioPicFile(studioPicFile.getBytes());
//		newStudioPic.setStudioPicName(studioPicName);
//		System.out.println(studioPicName);
//		
//		spService.createEntry(newStudioPic);
//		
//		return "StudioPic uploaded to DB successfully!";
//	}
//	
//	
//	@GetMapping("/studioPic/readOneWStudioPic")
//	public String readPSwithStudioPic(@RequestParam(name = "studioID") int studioID, Model model) {
//		
//		Studio resultBean = sService.readEntry(studioID);
//		model.addAttribute("resultBean", resultBean);
//		
//		return "backstage/studio/studioPic/readStudioWithStudioPic";
//	}
//	
//	
//	@GetMapping("/studioPic/getStudioPic/{studioID}")
//	public ResponseEntity<byte[]> getPSStudioPic(@PathVariable(name = "studioID") int studioID) throws IOException {
//		
//		FileInputStream defaultPic = new FileInputStream("/Users/lavonzux/Documents/examplePicture/DSCF4967.jpg");
//		
//		StudioPic result = spService.readFirstByStudioID(studioID);
//		byte[] pictureFile;
//		
//		if (result != null) {
//			pictureFile = result.getStudioPicFile();
//		}else {
//			pictureFile = defaultPic.readAllBytes();
//		}
//		defaultPic.close();
//
//		HttpHeaders photoHeader = new HttpHeaders();
//		photoHeader.setContentType(MediaType.IMAGE_JPEG);
//		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
//	}
//	
//	
//	@GetMapping("/studioPic/api/getPicById")
//	@ResponseBody
//	public ResponseEntity<byte[]> getStudioPicById(@RequestParam(name = "studioPicID") int studioPicID) {
//		
//		StudioPic result = spService.readOneEntry(studioPicID);
//		byte[] pictureFile=null;
//		
//		if (result != null) pictureFile=result.getStudioPicFile();
//		
//		try {
//			File notFoundImg=new ClassPathResource(NOT_FOUND_IMAGE_PATH).getFile();
//			FileInputStream notFoundImgFis=new FileInputStream(notFoundImg);
//			pictureFile=notFoundImgFis.readAllBytes();
//			notFoundImgFis.close();
//		} catch (IOException e) {
//			System.out.println("========== !!! Exception thorwn in getStudioPicAPI !!! ==========");
//			e.printStackTrace();
//		}
//		
//		HttpHeaders photoHeader=new HttpHeaders();
//		photoHeader.setContentType(MediaType.IMAGE_JPEG);
//		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
//	}
//	
//	
//	@GetMapping("/studioPic/api/getPicIds")
//	@ResponseBody
//	public ArrayList<Integer> getStudioPicIDs(@RequestParam(name = "studioID") int studioID) {
//		
//		List<StudioPic> readAllByStudioID = spService.readAllPicturesByStudioPicID(studioID);
//		if (readAllByStudioID.size()>0) { System.out.println("Ref Pics Found!"); }
//		
//		ArrayList<Integer> studioPicIDs = new ArrayList<>();
//		for (StudioPic studioPic : readAllByStudioID) { studioPicIDs.add(studioPic.getStudioPicID()); }
//		
//		return studioPicIDs;
//	}
//	
//	
//	@PostMapping("/studioPic/delete")
//	public void deleteStudioPic(@RequestParam("studioPicID") int studioPicID) {
//	    spService.deleteStudioPic(studioPicID);
//	}
//
//}
