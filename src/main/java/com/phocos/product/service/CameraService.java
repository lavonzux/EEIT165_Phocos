package com.phocos.product.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.product.model.Camera;
import com.phocos.product.model.CameraRepository;

@Service

public class CameraService {

	@Autowired
	private CameraRepository cameraRepo;
	
	// 新增單筆資料
	public void insert(Camera camera) {
		cameraRepo.save(camera);
	}
	
	
	// 用ID查詢
	public Camera getphotoById(Integer productID) {
		Optional<Camera> optional = cameraRepo.findById(productID);
		if ( optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// 查詢全部
	public List<Camera> findAll() {
		return cameraRepo.findAll();
	}
	
	//刪除
	public void deleteById(Integer productID) {
		cameraRepo.deleteById(productID);
	}
	
	//取得修改資料
	@GetMapping("/product/{id}")
	public Camera getById(@PathVariable Integer productID) {
		Optional<Camera> optional = cameraRepo.findById(productID);
		
		if(optional.isPresent()) {
			Camera result = optional.get();
			return result;
		}
		
		Camera camera = new Camera();
		camera.setCameraBrand("沒有這筆資料");
		
		return camera;
	}
	
	
	@Transactional
	// 修改
	public Camera updateCameraById(@RequestParam Integer productID,
			@RequestParam String cameraModel, @RequestParam String cameraBrand,
			@RequestParam Integer cameraPrice, @RequestParam String cameraSensor,
			@RequestParam Integer cameraPx, @RequestParam String cameraRecPx,
			@RequestParam String cameraMount, @RequestParam String cameraIBIS,
			@RequestParam String cameraEVF, @RequestParam String cameraLCD,
			@RequestParam String cameraFocusSys, @RequestParam String cameraPhotometry,
			@RequestParam Float cameraISOMin, @RequestParam Integer cameraISOMax,
			@RequestParam String cameraShutter, @RequestParam String cameraBurst,
			@RequestParam String cameraMemCard, @RequestParam String cameraBattery,
			@RequestParam String cameraDims, @RequestParam Integer cameraWeight
			,@RequestParam byte[] cameraPhoto, @RequestParam int cmaeraStocks
) throws IOException{
		Optional<Camera> optional = cameraRepo.findById(productID);
		if(optional.isPresent()) {
			Camera camera = optional.get();
			camera.setCameraModel(cameraModel);
			camera.setCameraBrand(cameraBrand);
			camera.setCameraPrice(cameraPrice);
			camera.setCameraSensor(cameraSensor);
			camera.setCameraPx(cameraPx);;
			camera.setCameraRecPx(cameraRecPx);
			camera.setCameraMount(cameraMount);
			camera.setCameraIBIS(cameraIBIS);
			camera.setCameraEVF(cameraEVF);
			camera.setCameraLCD(cameraLCD);
			camera.setCameraFocusSys(cameraFocusSys);
			camera.setCameraPhotometry(cameraPhotometry);
			camera.setCameraISOMin(cameraISOMin);
			camera.setCameraISOMax(cameraISOMax);
			camera.setCameraShutter(cameraShutter);
			camera.setCameraBurst(cameraBurst);
			camera.setCameraMemCard(cameraMemCard);
			camera.setCameraBattery(cameraBattery);
			camera.setCameraDims(cameraDims);
			camera.setCameraWeight(cameraWeight);
			camera.setCameraPhoto(cameraPhoto);
			camera.setCmaeraStocks(cmaeraStocks);
			
			return cameraRepo.save(camera);
		}
		return null;
	}
	

}