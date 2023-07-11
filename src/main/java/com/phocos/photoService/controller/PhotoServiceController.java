package com.phocos.photoService.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.member.MemberService;
import com.phocos.photoService.Dto.PhotoServiceDto;
import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ReferencePicture;
import com.phocos.photoService.model.ServiceType;
import com.phocos.photoService.service.PhotoServiceService;
import com.phocos.photoService.service.ServiceTypeService;

@Controller
public class PhotoServiceController {


	@Autowired
	private PhotoServiceService psService;
	
	@Autowired
	private ServiceTypeService stService;
	
	
	@Autowired
	private MemberService mService;


	
	@GetMapping(path = "/photoService/CreatePhotoService.controller")
	public String goToCreatePage() {
//			System.out.println("==================== GETTING create request... goto CreatePhotoService page ====================");
			return "backstage/photoService/CreatePhotoService";
	}
	
	
	@GetMapping("/photoService/Create")
	public String goToForeCreatePage() {
//			System.out.println("==================== GETTING create request... goto CreatePhotoService page ====================");
			return "forestage/photoService/CreatePhotoService";
	}
	
	
	@PostMapping(path = "/photoService/CreatePhotoService.controller")
	public String processCreatePhotoServiceAction(
			@RequestParam("serviceName") String serviceName,
			@RequestParam("serviceType") String serviceType,
			@RequestParam("servicePrice") String servicePrice,
			@RequestParam("serviceDuration") String serviceDuration,
			@RequestParam("serviceLocation") String serviceLocation,
			@RequestParam("serviceCreator") int serviceCreator,
			@RequestParam("inputRefPics") MultipartFile[] inputReferencePictures,
			Model model) throws IOException {
		System.out.println("==================== CONFIRMED a create request... goto persist... ====================");
	
		PhotoService createPSBean = new PhotoService();
		
		createPSBean.setServiceName(serviceName);
		createPSBean.setServiceType(stService.readEntry(serviceType));
		createPSBean.setServicePrice(servicePrice);
		createPSBean.setServiceDuration(serviceDuration);
		createPSBean.setServiceLocation(serviceLocation);
		createPSBean.setServiceCreator(mService.findById(serviceCreator));
		
		if (inputReferencePictures != null && inputReferencePictures.length > 0) {
			ArrayList<ReferencePicture> referencePictures = new ArrayList<ReferencePicture>();
			for(MultipartFile oneInputPic : inputReferencePictures) {
				ReferencePicture oneRefPic = new ReferencePicture();
				oneRefPic.setPhotoService(createPSBean);
				oneRefPic.setPictureFile(oneInputPic.getBytes());
				oneRefPic.setPictureName(oneInputPic.getOriginalFilename());
				referencePictures.add(oneRefPic);
			}
			createPSBean.setReferencePictures(referencePictures);
		}
		
		
		PhotoService resultBean = psService.createEntry(createPSBean);

		model.addAttribute("resultBean", resultBean);
		System.out.printf("PhotoService ID %d has been added to Database successfully",resultBean.getServiceID());
		return "backstage/photoService/ConfirmCreatedPhotoService";

	}
	
	
	
	@PostMapping(path = "/photoService/Create")
	public String processForestageCreatePhotoServiceAction(
			@RequestParam("serviceName") String serviceName,
			@RequestParam("serviceType") String serviceType,
			@RequestParam("servicePrice") String servicePrice,
			@RequestParam("serviceDuration") String serviceDuration,
			@RequestParam("serviceLocation") String serviceLocation,
			@RequestParam("serviceCreatorID") int serviceCreatorID,
			@RequestParam("inputRefPics") MultipartFile[] inputReferencePictures,
			Model model) throws IOException {
		System.out.println("==================== CONFIRMED a create request... goto persist... ====================");
	
		PhotoService createPSBean = new PhotoService();
		
		createPSBean.setServiceName(serviceName);
		createPSBean.setServiceType(stService.readEntry(serviceType));
		createPSBean.setServicePrice(servicePrice);
		createPSBean.setServiceDuration(serviceDuration);
		createPSBean.setServiceLocation(serviceLocation);
		createPSBean.setServiceCreator(mService.findById(serviceCreatorID));
		
		if (!inputReferencePictures[0].isEmpty()) {
			ArrayList<ReferencePicture> referencePictures = new ArrayList<ReferencePicture>();
			for(MultipartFile oneInputPic : inputReferencePictures) {
				ReferencePicture oneRefPic = new ReferencePicture();
				oneRefPic.setPhotoService(createPSBean);
				oneRefPic.setPictureFile(oneInputPic.getBytes());
				oneRefPic.setPictureName(oneInputPic.getOriginalFilename());
				referencePictures.add(oneRefPic);
			}
			createPSBean.setReferencePictures(referencePictures);
		}
		
		
		PhotoService resultBean = psService.createEntry(createPSBean);

		model.addAttribute("resultBean", resultBean);
		model.addAttribute("message", "新增成功");
		System.out.printf("PhotoService ID %d has been added to Database successfully",resultBean.getServiceID());
		return "redirect:forestage/photoService";
	}
	
	
	


	@GetMapping(path = "/photoService/ReadAllPhotoService.controller")
	public String processReadAllPhotoServiceAction(Model model) {
		List<PhotoService> resultList = psService.readAllEntries();
		model.addAttribute("resultList",resultList);
		return "backstage/photoService/ReadAllPhotoService";
	}
	
	
	
	
	@GetMapping(path = {"/photoService", "/photoService/ReadAll"})
	public String gotoReadAllPhotoServicePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		if (page == null) { page = 1; }
		Page<PhotoService> resultPage = psService.readAllByPage(page-1, 5);
		model.addAttribute("resultPage", resultPage);
		return "forestage/photoService/ReadAllPhotoService";
	}
	
	
	@GetMapping("/photoService/SearchByName")
	public String gotoSearchByName(@RequestParam("queryServiceName") String queryServiceName, @RequestParam(name = "page", required = false) Integer page, Model model) {
		if (page == null) {page = 1;}
		Page<PhotoService> resultPage = psService.readByName(queryServiceName);
		model.addAttribute("resultPage", resultPage);
		return "forestage/photoService/ReadAllPhotoService";
	}
	
	
	@GetMapping("/photoService/ReadByType")
	public String gotoReadAllPSByType(@RequestParam("serviceType") String serviceType, @RequestParam(name = "page", required = false) Integer page, Model model) {
		if (page == null) {page = 1;}
		if (stService.inDB(serviceType)) {
			return "forestage/photoServiceReadAllPhotoService";
		}
		ServiceType queryType = stService.readEntry(serviceType);
		Page<PhotoService> resultPage = psService.readAllByPageByType(queryType);
		model.addAttribute("resultBean", resultPage);
		return "forestage/photoService/ReadAllPhotoService";
	}
	
	
	
	

	@GetMapping(path = "/photoService/ReadOnePhotoService.controller")
	public String processReadOnePhotoServiceAction(@RequestParam("serviceID") int serviceID, Model model) {
		PhotoService resultBean = psService.readEntry(serviceID);
		model.addAttribute("resultBean", resultBean);

		return "backstage/photoService/ReadOnePhotoService";
	}
	
	
	


	@GetMapping(path = "/photoService/ReadOne")
	public String gotoReadOnePhotoService(@RequestParam("serviceID") int serviceID, Model model) {
		PhotoServiceDto resultBean = psService.readEntry(serviceID).toDto();
		model.addAttribute("resultBean", resultBean);
		return "forestage/photoService/ReadOnePhotoService";
	}
	
	
	@GetMapping("/photoService/UpdatePhotoService.controller")
	public String goToUpdatePhotoService(@RequestParam("serviceID") int serviceID, Model model) {
		System.out.printf("========== Querying PhotoServiceID: %d for updating ==========%n",serviceID);
		PhotoServiceDto queryPhotoServiceBean = psService.readEntry(serviceID).toDto();
		model.addAttribute("queryPhotoServiceBean", queryPhotoServiceBean);
		return "backstage/photoService/UpdatePhotoService";
	}
	
	
	@PostMapping("/photoService/UpdatePhotoService.controller")
	public String processUpdatePhotoServiceAction(@ModelAttribute("queryPhotoServiceBean") PhotoServiceDto queryPhotoServiceBean,BindingResult result , Model model) {

		if (result.hasErrors()) { System.out.println(result.toString()); }

		// If confirmed parameter is valid, qPSB filled with updated data, binding have no error,
		// Then store the old data, and go update the data in DB.
		if (queryPhotoServiceBean!=null) {

		System.out.println("========== Confirmed to update... ==========");
		System.out.printf("========== Updating PhotoServiceID: %d ==========%n",queryPhotoServiceBean.getServiceID());

		PhotoService oldPhotoServiceBean = psService.readEntry(queryPhotoServiceBean.getServiceID());
		PhotoServiceDto oldPSBDto = oldPhotoServiceBean.toDto();
		model.addAttribute("oldPhotoServiceBean", oldPSBDto);
		
		PhotoService newPhotoServiceBean = psService.updateEntry(queryPhotoServiceBean.getServiceID(), queryPhotoServiceBean);
		PhotoServiceDto newPSBDto = newPhotoServiceBean.toDto();
		model.addAttribute("newPhotoServiceBean", newPSBDto);
		
		return "backstage/photoService/ConfirmUpdatedPhotoService";
		}
		return null;
	}



	@GetMapping("/photoService/DeletePhotoService.controller")
	public String goToDeletePhotoServiceAction(@RequestParam("serviceID") int serviceID, Model model) {
		System.out.println("==================== Incoming deletion request... ====================");

			System.out.println("========== Reading data for deleting... ==========");
			System.out.printf("========== Querying PhotoServiceID: %d ==========%n",serviceID);
			PhotoService queryPhotoServiceBean = psService.readEntry(serviceID);
			model.addAttribute("queryPhotoServiceBean", queryPhotoServiceBean);
			return "backstage/photoService/DeletePhotoService";
		
	}
	
	
	@PostMapping("/photoService/DeletePhotoService.controller")
	public String processDeletePhotoServiceAction(@RequestParam("serviceID") int serviceID, Model model) {

		// Store the old data, and go delete the data in DB.
		System.out.println("========== Confirmed to delete... ==========");
		System.out.printf("========== Deleting PhotoServiceID: %d ==========%n",serviceID);

		PhotoService deletedPhotoServiceBean = psService.readEntry(serviceID);
		model.addAttribute("deletedPhotoServiceBean", deletedPhotoServiceBean);
		psService.deleteEntry(serviceID);

		return "backstage/photoService/ConfirmDeletedPhotoService";
	}

}
