package com.phocos.product.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.phocos.product.model.Camera;
import com.phocos.product.service.CameraService;

@Controller
public class CameraController {

	@Autowired
	private CameraService cameraService;

	@GetMapping("/cameras")
	public String processMainAction(Model m) {
		List<Camera> cameras = cameraService.findAll();
		m.addAttribute("cameras", cameras);
		return "backstage/towakawaii/CameraHome";
	}

	@GetMapping("/product/insertcamerapage")
	public String insertPage() {
		return "backstage/towakawaii/CameraAdd";
	}

	@GetMapping("/product/cameradetail")
	public String detailPage(@RequestParam("productID") Integer productID, Model model) {
		Camera camera = cameraService.getById(productID);
		model.addAttribute("camera", camera);
		return "backstage/towakawaii/CameraShow";
	}

	@PostMapping("/product/insertcamera")
	public String insertCamera(@RequestParam("cameraModel") String model, @RequestParam("cameraBrand") String brand,
			@RequestParam("cameraPrice") Integer price, @RequestParam("cameraSensor") String sensor,
			@RequestParam("cameraPx") Integer px, @RequestParam("cameraRecPx") String recpx,
			@RequestParam("cameraMount") String mount, @RequestParam("cameraIBIS") String ibis,
			@RequestParam("cameraEVF") String evf, @RequestParam("cameraLCD") String lcd,
			@RequestParam("cameraFocusSys") String focussys, @RequestParam("cameraPhotometry") String photometry,
			@RequestParam("cameraISOMin") Float isomin, @RequestParam("cameraISOMax") Integer isomax,
			@RequestParam("cameraShutter") String shutter, @RequestParam("cameraBurst") String burst,
			@RequestParam("cameraMemCard") String memcard, @RequestParam("cameraBattery") String battery,
			@RequestParam("cameraDims") String dims, @RequestParam("cameraWeight") Integer weight,
			@RequestParam("cameraPhoto") MultipartFile cameraphoto,@RequestParam("cameraStocks") int stocks) {
		try {
			Camera camera = new Camera();
			camera.setCameraModel(model);
			camera.setCameraBrand(brand);
			camera.setCameraPrice(price);
			camera.setCameraSensor(brand);
			camera.setCameraPx(px);
			camera.setCameraRecPx(recpx);
			camera.setCameraMount(brand);
			camera.setCameraIBIS(ibis);
			camera.setCameraEVF(evf);
			camera.setCameraLCD(lcd);
			camera.setCameraFocusSys(focussys);
			camera.setCameraPhotometry(photometry);
			camera.setCameraISOMin(isomin);
			camera.setCameraISOMax(isomax);
			camera.setCameraShutter(shutter);
			camera.setCameraBurst(burst);
			camera.setCameraMemCard(memcard);
			camera.setCameraBattery(battery);
			camera.setCameraDims(dims);
			camera.setCameraWeight(weight);
			camera.setCameraPhoto(cameraphoto.getBytes());
			camera.setCmaeraStocks(stocks);
			cameraService.insert(camera);
			return "redirect:/cameras";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/cameras";
	}

	@DeleteMapping("/product/deleteCamera")
	public String deleteCamera(@RequestParam("productID") Integer productID) {
		cameraService.deleteById(productID);
		return "redirect:/cameras";
	}

	@GetMapping("/product/editcamera")
	public String editPage(@RequestParam("productID") Integer productID, Model model) {
		Camera camera = cameraService.getById(productID);
		model.addAttribute("camera", camera);
		return "backstage/towakawaii/CameraUpdate";
	}

	@GetMapping("/downloadcameraImage/{id}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id) {
		Camera camera1 = cameraService.getphotoById(id);
		byte[] photoFile = camera1.getCameraPhoto();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		// 檔案, header, HttpStatus
		return new ResponseEntity<byte[]>(photoFile, headers, HttpStatus.OK);
	}

	@PostMapping("/product/editcameraData")
	public String editPost(@RequestParam("productID") Integer productID, @RequestParam("cameraModel") String model,
			@RequestParam("cameraBrand") String brand, @RequestParam("cameraPrice") Integer price,
			@RequestParam("cameraSensor") String sensor, @RequestParam("cameraPx") Integer px,
			@RequestParam("cameraRecPx") String recpx, @RequestParam("cameraMount") String mount,
			@RequestParam("cameraIBIS") String ibis, @RequestParam("cameraEVF") String evf,
			@RequestParam("cameraLCD") String lcd, @RequestParam("cameraFocusSys") String focussys,
			@RequestParam("cameraPhotometry") String photometry, @RequestParam("cameraISOMin") Float isomin,
			@RequestParam("cameraISOMax") Integer isomax, @RequestParam("cameraShutter") String shutter,
			@RequestParam("cameraBurst") String burst, @RequestParam("cameraMemCard") String memcard,
			@RequestParam("cameraBattery") String battery, @RequestParam("cameraDims") String dims,
			@RequestParam("cameraWeight") Integer weight, @RequestParam("cameraPhoto") MultipartFile cameraPhoto,
			@RequestParam("cameraStocks") int stocks,
			Model model1) throws IOException {
		byte[] camerabyte = null;
		camerabyte = cameraPhoto.getBytes();
		Camera updateCamera = cameraService.updateCameraById(productID, model, brand, price, sensor, px, recpx, mount,
				ibis, evf, lcd, focussys, photometry, isomin, isomax, shutter, burst, memcard, battery, dims, weight,
				camerabyte,stocks);
		ResponseEntity<byte[]> imagEntity = downloadImage(productID);
		byte[] cameraphoto = imagEntity.getBody();
		model1.addAttribute("cameraphoto", cameraphoto);
		return "redirect:/cameras";
	}
}
