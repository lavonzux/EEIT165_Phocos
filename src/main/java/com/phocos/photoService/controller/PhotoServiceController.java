package com.phocos.photoService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.photoService.model.PhotoService;
import com.phocos.photoService.service.PhotoServiceService;

@Controller
public class PhotoServiceController {


	@Autowired
	private PhotoServiceService psService;


//	@GetMapping({"/","/dashboard","/manage"})
//	public String goToHomePage() {
//		return "backstage/shared_layout/dashboard";
//	}

	@RequestMapping(path = "/photoService/CreatePhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processCreatePhotoServiceAction(@ModelAttribute("createPhotoService") PhotoService createPhotoServiceBean, BindingResult result, Model model) {

		System.out.println(createPhotoServiceBean);

		if (!createPhotoServiceBean.dataIsValid()) {

			System.out.println("==================== GETTING create request... goto CreatePhotoService page ====================");

			createPhotoServiceBean = new PhotoService();
//			createPhotoServiceBean.setServiceName("請輸入服務名");
			model.addAttribute("createPhotoService", createPhotoServiceBean);
			return "backstage/photoService/CreatePhotoService";
		}



		if (createPhotoServiceBean!=null && createPhotoServiceBean.dataIsValid() && !result.hasErrors()) {

			System.out.println("==================== CONFIRMED a create request... goto persist... ====================");

			PhotoService resultBean = psService.createEntry(createPhotoServiceBean);

			model.addAttribute("resultBean", resultBean);
			System.out.printf("PhotoService ID %d has been added to Database successfully",resultBean.getServiceID());
			return "backstage/photoService/ConfirmCreatedPhotoService";
		}

		// how to extract error message when psService.createEntry throw exception
		model.addAttribute("errorMsg", "Something went wrong when inserting entry! Please check your data again!");
		return "backstage/photoService/CreatePhotoService";
	}



	@RequestMapping(path = "/photoService/ReadAllPhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processReadAllPhotoServiceAction(Model model) {
		List<PhotoService> resultList = psService.readAllEntries();

		model.addAttribute("resultList",resultList);

		return "backstage/photoService/ReadAllPhotoService";

	}



	@RequestMapping(path = "/photoService/ReadOnePhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processReadOnePhotoServiceAction(@RequestParam("serviceID") int serviceID, Model model) {

		PhotoService resultBean = psService.readEntry(serviceID);

		model.addAttribute("resultBean", resultBean);

		return "backstage/photoService/ReadOnePhotoService";
	}



	@RequestMapping(path = "/photoService/UpdatePhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processUpdatePhotoServiceAction(@RequestParam(value="confirmed", required=false) boolean confirmed, @RequestParam("serviceID") int serviceID, @ModelAttribute("queryPhotoServiceBean") PhotoService queryPhotoServiceBean,BindingResult result , Model model) {

		// If there's no confirmed parameter, QPSB is null, or binding have error, means the first time requesting data for updating
		// Then get the desired entry defined in get method's query string, and store it to attribute.
		if (!confirmed || queryPhotoServiceBean==null || result.hasErrors()) {
			System.out.println("========== Reading data for updating... ==========");
			System.out.printf("========== Querying PhotoServiceID: %d ==========%n",serviceID);
			queryPhotoServiceBean = psService.readEntry(serviceID);
			model.addAttribute("queryPhotoServiceBean", queryPhotoServiceBean);
			return "backstage/photoService/UpdatePhotoService";
		}

		// If confirmed parameter is valid, qPSB filled with updated data, binding have no error,
		// Then store the old data, and go update the data in DB.
		if (confirmed && queryPhotoServiceBean!=null && !result.hasErrors()) {

		System.out.println("========== Confirmed to update... ==========");
		System.out.printf("========== Updating PhotoServiceID: %d ==========%n",serviceID);

		PhotoService oldPhotoServiceBean = psService.readEntry(queryPhotoServiceBean.getServiceID());
		model.addAttribute("oldPhotoServiceBean", oldPhotoServiceBean);

		PhotoService newPhotoServiceBean = psService.updateEntry(queryPhotoServiceBean.getServiceID(), queryPhotoServiceBean);
		model.addAttribute("newPhotoServiceBean", newPhotoServiceBean);
		return "backstage/photoService/ConfirmUpdatedPhotoService";
		}

		// There should only be two situation defined above, other situation go to error page.
		return "ErrorPage";
	}



	@RequestMapping(path = "/photoService/DeletePhotoService.controller", method = {RequestMethod.GET, RequestMethod.POST})
	public String processDeletePhotoServiceAction(@RequestParam(value="confirmed", required=false) boolean confirmed, @RequestParam("serviceID") int serviceID, @ModelAttribute("queryPhotoServiceBean") PhotoService queryPhotoServiceBean,BindingResult result , Model model) {
		System.out.println("==================== Incoming deletion request... ====================");

		if (!confirmed || !queryPhotoServiceBean.dataIsValid() || result.hasErrors()) {
			System.out.println("========== Reading data for updating... ==========");
			System.out.printf("========== Querying PhotoServiceID: %d ==========%n",serviceID);
			queryPhotoServiceBean = psService.readEntry(serviceID);
			model.addAttribute("queryPhotoServiceBean", queryPhotoServiceBean);
			return "backstage/photoService/DeletePhotoService";
		}

		// If confirmed parameter is valid, qPSB filled with updated data, binding have no error,
		// Then store the old data, and go delete the data in DB.
		if (confirmed && queryPhotoServiceBean!=null && !result.hasErrors()) {

		System.out.println("========== Confirmed to delete... ==========");
		System.out.printf("========== Deleting PhotoServiceID: %d ==========%n",serviceID);

		PhotoService deletedPhotoServiceBean = psService.readEntry(serviceID);
		model.addAttribute("deletedPhotoServiceBean", deletedPhotoServiceBean);
		psService.deleteEntry(queryPhotoServiceBean.getServiceID());


		return "backstage/photoService/ConfirmDeletedPhotoService";
		}

		// There should only be two situation defined above, other situation go to error page.
		return "ErrorPage";
	}

}
