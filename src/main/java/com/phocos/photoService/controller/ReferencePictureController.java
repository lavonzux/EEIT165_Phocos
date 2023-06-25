package com.phocos.photoService.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	
	@Autowired
	private PhotoServiceService psService;
	
	@Autowired
	private ReferencePictureService rpService;
	
	
	@GetMapping("/referencePicture/upload")
	public String getReferencePhoto() {	
		return "backstage/photoService/referencePicture/upload";
	}
	
	
	
	@PostMapping("/referencePicture/upload")
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
	
	
	@GetMapping("/referencePicture/getRefPic")
	public ResponseEntity<byte[]> getPSRefPic(@RequestParam(name = "serviceID") int serviceID) {
		
		ReferencePicture result = rpService.readByPhotoServiceID(serviceID);
		byte[] pictureFile = result.getPictureFile();
		
		HttpHeaders photoHeader = new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
	}
	
}
