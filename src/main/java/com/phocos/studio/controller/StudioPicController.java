package com.phocos.studio.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.studio.util.Studio;
import com.phocos.studio.util.StudioPic;
import com.phocos.studio.util.StudioService;
import com.phocos.studio.util.StudioPicService;

@Controller
public class StudioPicController {

	private static final String NOT_FOUND_IMAGE_PATH = "static/backstage/studio/Notfound.jpg";
	
	@Autowired
	private StudioService sService;
	
	@Autowired
	private StudioPicService spService;
	
	
	@GetMapping("/studioPic/upload")
	public String getReferencePhoto() {	
		return "backstage/studio/upload";
	}
	
	
	
	@PostMapping("/studioPic/upload")
	@ResponseBody
	public String uploadStudioPic(
			@RequestParam(name = "studioID") Integer studioID,
			@RequestParam(name = "shedID") Integer shedID,
			@RequestParam(name = "studioPicName") String studioPicName,
			@RequestParam(name = "studioPicFile") MultipartFile studioPicFile) throws IOException {
		
		Studio queryStudio = sService.readEntry(studioID);
		
		
		StudioPic newStudioPic = new StudioPic();
		newStudioPic.setStudio(queryStudio);
		newStudioPic.setStudioPicFile(studioPicFile.getBytes());
		newStudioPic.setStudioPicName(studioPicName);
		
		spService.createEntry(newStudioPic);
		
		return "StudioPic uploaded to DB successfully!";
	}
	
	
	@GetMapping("/studioPic/readOneWStudioPic")
	public String readPSwithStudioPic(@RequestParam(name = "studioID") int studioID, Model model) {
		
		Studio resultBean = sService.readEntry(studioID);
		model.addAttribute("resultBean", resultBean);
		
		return "backstage/studio/studioPic/readStudioWithStudioPic";
	}
	
	
	@GetMapping("/studioPic/getStudioPic/{studioID}")
	public ResponseEntity<byte[]> getPSStudioPic(@PathVariable(name = "studioID") int studioID) throws IOException {
		
		FileInputStream defaultPic = new FileInputStream("/Users/lavonzux/Documents/examplePicture/DSCF4967.jpg");
		
		StudioPic result = spService.readFirstByStudioID(studioID);
		byte[] pictureFile;
		
		if (result != null) {
			pictureFile = result.getStudioPicFile();
		}else {
			pictureFile = defaultPic.readAllBytes();
		}
		defaultPic.close();

		HttpHeaders photoHeader = new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
	}
	
	
	@GetMapping("/studioPic/api/getPicById")
	@ResponseBody
	public ResponseEntity<byte[]> getStudioPicById(@RequestParam(name = "studioPicID") int studioPicID) {
		
		StudioPic result = spService.readOneEntry(studioPicID);
		byte[] pictureFile=null;
		
		if (result != null) pictureFile=result.getStudioPicFile();
		
		try {
			File notFoundImg=new ClassPathResource(NOT_FOUND_IMAGE_PATH).getFile();
			FileInputStream notFoundImgFis=new FileInputStream(notFoundImg);
			pictureFile=notFoundImgFis.readAllBytes();
			notFoundImgFis.close();
		} catch (IOException e) {
			System.out.println("========== !!! Exception thorwn in getStudioPicAPI !!! ==========");
			e.printStackTrace();
		}
		
		HttpHeaders photoHeader=new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
	}
	
	
	@GetMapping("/studioPic/api/getPicIds")
	@ResponseBody
	public ArrayList<Integer> getStudioPicIDs(@RequestParam(name = "studioID") int studioID) {
		
		List<StudioPic> readAllByStudioID = spService.readAllPicturesByStudioPicID(studioID);
		if (readAllByStudioID.size()>0) { System.out.println("Ref Pics Found!"); }
		
		ArrayList<Integer> studioPicIDs = new ArrayList<>();
		for (StudioPic studioPic : readAllByStudioID) { studioPicIDs.add(studioPic.getStudioPicID()); }
		
		return studioPicIDs;
	}
	
	
	@PostMapping("/studioPic/delete")
	public void deleteStudioPic(@RequestParam("studioPicID") int studioPicID) {
	    spService.deleteStudioPic(studioPicID);
	}

}
