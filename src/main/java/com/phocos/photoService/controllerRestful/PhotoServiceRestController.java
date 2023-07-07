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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.model.PhotoServiceDto;
import com.phocos.photoService.service.PhotoServiceService;

@RestController
public class PhotoServiceRestController {


	@Autowired
	private PhotoServiceService psService;


	
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


	
	
//	@RequestMapping(path = "/photoService/UpdatePhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processUpdatePhotoServiceAction(@RequestParam(value="confirmed", required=false) boolean confirmed, @RequestParam("serviceID") int serviceID, @ModelAttribute("queryPhotoServiceBean") PhotoService queryPhotoServiceBean,BindingResult result , Model model) {

		// If there's no confirmed parameter, QPSB is null, or binding have error, means the first time requesting data for updating
		// Then get the desired entry defined in get method's query string, and store it to attribute.
		if (!confirmed || queryPhotoServiceBean==null || result.hasErrors()) {
			System.out.println("========== Reading data for updating... ==========");
			System.out.printf("========== Querying PhotoServiceID: %d ==========%n",serviceID);
			queryPhotoServiceBean = psService.readEntry(serviceID);
			model.addAttribute("queryPhotoServiceBean", queryPhotoServiceBean);
			model.addAttribute("oldPhotoServiceBean", queryPhotoServiceBean);
			return "backstage/photoService/UpdatePhotoService";
		}

		// If confirmed parameter is valid, qPSB filled with updated data, binding have no error,
		// Then store the old data, and go update the data in DB.
		if (confirmed && queryPhotoServiceBean!=null && !result.hasErrors()) {

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
		return "ErrorPage";
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