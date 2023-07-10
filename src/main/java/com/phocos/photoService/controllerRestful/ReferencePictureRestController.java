package com.phocos.photoService.controllerRestful;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.photoService.service.PhotoServiceService;
import com.phocos.photoService.service.ReferencePictureService;

@RestController
public class ReferencePictureRestController {

	private static final String NOT_FOUND_IMAGE_PATH = "static/backstage/photoService/Notfound.jpg";
	
	@Autowired
	private PhotoServiceService psService;
	
	@Autowired
	private ReferencePictureService rpService;
	
	
	
	@PostMapping("/referencePicture/upload")
	public String uploadReferencePicture(
			@RequestParam(name = "serviceID") Integer serviceID,
			@RequestParam(name = "photoFile") MultipartFile photoFile) throws IOException {
		
		PhotoService queryPhotoService = psService.readEntry(serviceID);
		
		ReferencePicture newRefPic = new ReferencePicture();
		newRefPic.setPhotoService(queryPhotoService);
		newRefPic.setPictureFile(photoFile.getBytes());
		newRefPic.setPictureName(photoFile.getOriginalFilename());
		
		rpService.createEntry(newRefPic);
		
		return "ReferencePicture uploaded to DB successfully!";
	}
	
	
	@GetMapping("/referencePicture/api/getPicById")
	public ResponseEntity<byte[]> getRefPicById(@RequestParam(name = "pictureID") int pictureID) {
		
		ReferencePicture result = rpService.readOneEntry(pictureID);
		byte[] pictureFile=null;
		
		if (result != null) {
			pictureFile=result.getPictureFile();
		}else {
			FileInputStream notFoundImgFis = null;
			try {
				File notFoundImg=new ClassPathResource(NOT_FOUND_IMAGE_PATH).getFile();
				notFoundImgFis=new FileInputStream(notFoundImg);
				pictureFile=notFoundImgFis.readAllBytes();
			} catch (IOException e) {
				System.out.println("========== !!! Exception thorwn in getRefPicAPI !!! ==========");
				e.printStackTrace();					
			}finally {
				try {
					notFoundImgFis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}
		
		HttpHeaders photoHeader=new HttpHeaders();
		photoHeader.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(pictureFile, photoHeader, HttpStatus.OK);
	}
	
	
	@GetMapping("/referencePicture/api/getPicIds")
	public List<Integer> getRefPicIDs(@RequestParam(name = "serviceID") int serviceID) {
		
		List<ReferencePicture> readAllByPhotoServiceID = rpService.readAllPicturesByServiceID(serviceID);
		if (readAllByPhotoServiceID == null) {
				System.out.println("No Ref Pic found for "+serviceID+"......"); 
				return null;
			}
		if (readAllByPhotoServiceID.size()>0) System.out.println("Ref Pics Found!"); 
		
		ArrayList<Integer> refPicIds = new ArrayList<>();
		for (ReferencePicture referencePicture : readAllByPhotoServiceID) { refPicIds.add(referencePicture.getPictureID()); }
		
		return refPicIds;
	}
	
	
	@DeleteMapping("/referencePicture/api/delete")
	public int deleteReferencePicture(@RequestParam("pictureID") int pictureID) {
		rpService.deleteReferencePicture(pictureID);
		return pictureID;
	}
	
	
	
	@DeleteMapping("/referencePicture/api/deleteMultiple")
	public String deletePictures(@RequestBody Set<Integer> pictureIDs) {
		try {
			rpService.deleteReferencePictures(pictureIDs);
		} catch (Exception e) {
			e.printStackTrace();
			return "delete fail";
		}
		return "delete successful";
	}
	

}
