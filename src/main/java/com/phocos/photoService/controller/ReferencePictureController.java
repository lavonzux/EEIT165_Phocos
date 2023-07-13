package com.phocos.photoService.controller;

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

import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.photoService.service.PhotoServiceService;
import com.phocos.photoService.service.ReferencePictureService;

@Controller
public class ReferencePictureController {

	private static final String NOT_FOUND_IMAGE_PATH = "static/backstage/photoService/Notfound.jpg";
	
	@Autowired
	private PhotoServiceService psService;
	
	@Autowired
	private ReferencePictureService rpService;
	
	
	@GetMapping("/referencePicture/upload")
	public String getReferencePhoto() {	
		return "backstage/photoService/referencePicture/upload";
	}
	
	
	
//	@PostMapping("/referencePicture/upload")
	@ResponseBody
	public String uploadReferencePicture(
			@RequestParam(name = "serviceID") Integer serviceID,
			@RequestParam(name = "photoName") String photoName,
			@RequestParam(name = "photoFile") MultipartFile photoFile) throws IOException {
		
		PhotoService queryPhotoService = psService.readEntry(serviceID);
		
		
		ReferencePicture newRefPic = new ReferencePicture();
		newRefPic.setPhotoService(queryPhotoService);
		newRefPic.setPictureFile(photoFile.getBytes());
		newRefPic.setPictureName(photoName);
		
		rpService.createEntry(newRefPic);
		
		return "ReferencePicture uploaded to DB successfully!";
	}
	
	
	@GetMapping("/referencePicture/readOneWRefPic")
	public String readPSwithRefPic(@RequestParam(name = "serviceID") int serviceID, Model model) {
		
		PhotoService resultBean = psService.readEntry(serviceID);
		model.addAttribute("resultBean", resultBean);
		
		return "backstage/photoService/referencePicture/readPhotoServiceWithRefPic";
	}
	
	
	@GetMapping("/referencePicture/getRefPic/{serviceID}")
	public ResponseEntity<byte[]> getPSRefPic(@PathVariable(name = "serviceID") int serviceID) throws IOException {
		
		FileInputStream defaultPic = new FileInputStream("/Users/lavonzux/Documents/examplePicture/DSCF4967.jpg");
		
		ReferencePicture result = rpService.readFirstByPhotoServiceID(serviceID);
		byte[] pictureFile;
		
		if (result != null) {
			pictureFile = result.getPictureFile();
		}else {
			pictureFile = defaultPic.readAllBytes();
		}
		defaultPic.close();

		HttpHeaders photoHeader = new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
	}
	
	
//	@GetMapping("/referencePicture/api/getPicById")
	@ResponseBody
	public ResponseEntity<byte[]> getRefPicById(@RequestParam(name = "pictureID") int pictureID) {
		
		ReferencePicture result = rpService.readOneEntry(pictureID);
		byte[] pictureFile=null;
		
		if (result != null) pictureFile=result.getPictureFile();
		
		try {
			File notFoundImg=new ClassPathResource(NOT_FOUND_IMAGE_PATH).getFile();
			FileInputStream notFoundImgFis=new FileInputStream(notFoundImg);
			pictureFile=notFoundImgFis.readAllBytes();
			notFoundImgFis.close();
		} catch (IOException e) {
			System.out.println("========== !!! Exception thorwn in getRefPicAPI !!! ==========");
			e.printStackTrace();
		}
		
		HttpHeaders photoHeader=new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
	}
	
	
//	@GetMapping("/referencePicture/api/getPicIds")
	@ResponseBody
	public ArrayList<Integer> getRefPicIDs(@RequestParam(name = "serviceID") int serviceID) {
		
		List<ReferencePicture> readAllByPhotoServiceID = rpService.readAllPicturesByServiceID(serviceID);
		if (readAllByPhotoServiceID.size()>0) { System.out.println("Ref Pics Found!"); }
		
		ArrayList<Integer> refPicIds = new ArrayList<>();
		for (ReferencePicture referencePicture : readAllByPhotoServiceID) { refPicIds.add(referencePicture.getPictureID()); }
		
		return refPicIds;
	}
	
	
	@PostMapping("/referencePicture/delete")
	public void deleteReferencePicture(@RequestParam("pictureID") int pictureID) {
		rpService.deleteReferencePicture(pictureID);
	}

}
