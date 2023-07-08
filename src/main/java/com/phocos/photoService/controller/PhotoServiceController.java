package com.phocos.photoService.controller;

import java.io.IOException;
import java.lang.reflect.Field;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.phocos.member.MemberService;
import com.phocos.photoService.Dto.PhotoServiceDto;
import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ReferencePicture;
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
	public String gotoCreatePage(Model model) {
			System.out.println("==================== GETTING create request... goto CreatePhotoService page ====================");

			PhotoService createPhotoServiceBean = new PhotoService();
			model.addAttribute("createPhotoService", createPhotoServiceBean);
			return "backstage/photoService/CreatePhotoService";
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


	@GetMapping(path = "/photoService/ReadAllPhotoService.controller")
	public String processReadAllPhotoServiceAction(Model model) {
		List<PhotoService> resultList = psService.readAllEntries();
		model.addAttribute("resultList",resultList);
		return "backstage/photoService/ReadAllPhotoService";
	}
	
	
	
//	@GetMapping(path = "/photoService/ReadAll")
	/*
	public String gotoReadAllPhotoService(Model model) {
		List<PhotoService> resultList = psService.readAllEntries();
		model.addAttribute("resultList",resultList);
		
		Page<PhotoService> resultPage = psService.readAllByPage();
		model.addAttribute("resultPage", resultPage);
		return "forestage/photoService/ReadAllPhotoService";
	}
	*/

	
	@GetMapping(path = {"/photoService", "/photoService/ReadAll"})
	public String gotoReadAllPhotoServicePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		if (page == null) { page = 1; }
		Page<PhotoService> resultPage = psService.readAllByPage(page-1, 5);
		model.addAttribute("resultPage", resultPage);
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
		PhotoService resultBean = psService.readEntry(serviceID);
		model.addAttribute("resultBean", resultBean);
		
		Field[] fields = resultBean.getClass().getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getName());
		}
		
		String typeName = resultBean.getServiceType().getTypeName();
		System.out.println(typeName);
		
		return "forestage/photoService/ReadOnePhotoService";
	}
	
	
	@RequestMapping(path = "/photoService/UpdatePhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processUpdatePhotoServiceAction(@RequestParam(value="confirmed", required=false) boolean confirmed, @RequestParam(value = "serviceID", required = false) int serviceID, @ModelAttribute("queryPhotoServiceBean") PhotoServiceDto queryPhotoServiceBean,BindingResult result , Model model) {

		// If there's no confirmed parameter, QPSB is null, or binding have error, means the first time requesting data for updating
		// Then get the desired entry defined in get method's query string, and store it to attribute.

		if (result.hasErrors()) {
			System.out.println(result.toString());
		}
		
		if (!confirmed) {
			System.out.println("========== Reading data for updating... ==========");
			System.out.printf("========== Querying PhotoServiceID: %d ==========%n",serviceID);
			queryPhotoServiceBean = psService.readEntry(serviceID).toDto();
			model.addAttribute("queryPhotoServiceBean", queryPhotoServiceBean);
			model.addAttribute("oldPhotoServiceBean", queryPhotoServiceBean);
			return "backstage/photoService/UpdatePhotoService";
		}

		// If confirmed parameter is valid, qPSB filled with updated data, binding have no error,
		// Then store the old data, and go update the data in DB.
		if (queryPhotoServiceBean!=null) {

		System.out.println("========== Confirmed to update... ==========");
		System.out.printf("========== Updating PhotoServiceID: %d ==========%n",serviceID);

		PhotoService oldPhotoServiceBean = psService.readEntry(queryPhotoServiceBean.getServiceID());
		PhotoServiceDto oldPSBDto = oldPhotoServiceBean.toDto();
		model.addAttribute("oldPhotoServiceBean", oldPSBDto);
		
		PhotoService newPhotoServiceBean = psService.updateEntry(queryPhotoServiceBean.getServiceID(), queryPhotoServiceBean);
		model.addAttribute("newPhotoServiceBean", newPhotoServiceBean);
		
		return "backstage/photoService/ConfirmUpdatedPhotoService";
		}

		// There should only be two situation defined above, other situation go to error page.
		System.out.println("uncatched condition in processUpdatePSAction!!!!!");
		return "ErrorPage";
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
