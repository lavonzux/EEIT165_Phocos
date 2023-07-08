package com.phocos.photoService.controllerRestful;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.member.Member;
import com.phocos.member.MemberService;
import com.phocos.photoService.Dto.PhotoServiceDto;
import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.ServiceType;
import com.phocos.photoService.service.PhotoServiceService;
import com.phocos.photoService.service.ServiceTypeService;

import jakarta.transaction.Transactional;

@RestController
public class PhotoServiceRestController {


	@Autowired
	private PhotoServiceService psService;

	@Autowired
	private MemberService mService;
	
	@Autowired
	private ServiceTypeService stService;

	
//	@PostMapping(path = "/photoService/CreatePhotoService.controller")
	public String processCreatePhotoServiceAction(@ModelAttribute("createPhotoService") PhotoServiceDto createPhotoServiceBean, BindingResult result, Model model) throws IOException {
		System.out.println("==================== CONFIRMED a create request... goto persist... ====================");
		
		if (result.hasErrors()) { System.out.println(result.toString()); }
		if (createPhotoServiceBean.toPhotoService().dataIsValid() ) {
			
			PhotoService resultBean = psService.createEntry(createPhotoServiceBean.toPhotoService());

			model.addAttribute("resultBean", resultBean);
			System.out.printf("PhotoService ID %d has been added to Database successfully",resultBean.getServiceID());
			return "backstage/photoService/ConfirmCreatedPhotoService";
		}

		model.addAttribute("errorMsg", "Something went wrong when inserting entry! Please check your data again!");
		return "backstage/photoService/CreatePhotoService";
	}


	@GetMapping(path = "/photoService/api/ReadAll")
	public List<PhotoService> processReadAllPhotoServiceAction(Model model) {
		List<PhotoService> resultList = psService.readAllEntries();
		return resultList;
	}

	@GetMapping(path = "/photoService/api/ReadAllPage")
	public Page<PhotoService> gotoReadAllPhotoService(Model model) {
		Page<PhotoService> resultPage = psService.readAllByPage();
		return resultPage;
	}

	@GetMapping(path = "/photoService/api/ReadOne")
	public PhotoService processReadOnePhotoServiceAction(@RequestParam("serviceID") int serviceID, Model model) {
		PhotoService resultBean = psService.readEntry(serviceID);
		return resultBean;
	}


	
	
	@Transactional
	@PutMapping("/photoService/api/Update")
	public List<PhotoServiceDto> processUpdatePhotoServiceAction(@ModelAttribute() PhotoServiceDto updatedDTO, BindingResult result) {

		
		if (result.hasErrors()) { 
			System.out.println(result.toString()); 
			return null;
		}
		if (updatedDTO != null) {
			System.out.printf("========== Updating PhotoServiceID: %d ==========%n",updatedDTO.getServiceID());

			PhotoService oldPhotoServiceBean = psService.readEntry(updatedDTO.getServiceID());
			PhotoServiceDto oldPSBDto = oldPhotoServiceBean.toDto();
			
			PhotoService newPhotoServiceBean = psService.updateEntry(updatedDTO.getServiceID(), updatedDTO);
			
			ArrayList<PhotoServiceDto> returnList = new ArrayList<>();
			returnList.add(oldPSBDto);
			returnList.add(newPhotoServiceBean.toDto());
			
			return returnList;
		}
		return null;
	}



	@DeleteMapping("/photoService/api/Delete")
	public PhotoService processDeletePhotoServiceAction(@RequestParam("serviceID") int serviceID, @ModelAttribute("queryPhotoServiceBean") PhotoService queryPhotoServiceBean,BindingResult result) {
		System.out.println("==================== Incoming deletion request... ====================");


		// qPSB can be found, binding have no error,
		// Then store the old data, and go delete the data in DB.
		if (queryPhotoServiceBean!=null && !result.hasErrors()) {

		System.out.println("========== Confirmed to delete... ==========");
		System.out.printf("========== Deleting PhotoServiceID: %d ==========%n",serviceID);

		PhotoService deletedPhotoServiceBean = psService.readEntry(serviceID);
//		psService.deleteEntry(queryPhotoServiceBean.getServiceID());


		return deletedPhotoServiceBean;
		}
		return null;
	}

}
