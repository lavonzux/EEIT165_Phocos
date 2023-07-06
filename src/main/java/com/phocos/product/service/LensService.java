package com.phocos.product.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.phocos.product.model.Lens;
import com.phocos.product.model.LensRepository;

@Service

public class LensService {

	@Autowired
	private LensRepository lensRepo;
	
	// 新增單筆資料
	public void insert(Lens lens) {
		lensRepo.save(lens);
	}
	
	
	// 用ID查詢
	public Lens getphotoById(Integer productID) {
		Optional<Lens> optional = lensRepo.findById(productID);
		if ( optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// 查詢全部
	public List<Lens> findAll() {
		return lensRepo.findAll();
	}
	
	//刪除
	public void deleteById(Integer productID) {
		lensRepo.deleteById(productID);
	}
	
	//取得修改資料
	@GetMapping("/product/{id}")
	public Lens getById(@PathVariable Integer productID) {
		Optional<Lens> optional = lensRepo.findById(productID);
		
		if(optional.isPresent()) {
			Lens result = optional.get();
			return result;
		}
		
		Lens lens = new Lens();
		lens.setLensBrand("沒有這筆資料");
		
		return lens;
	}
	
	
	@Transactional
	// 修改
	public Lens updateLensById(@RequestParam Integer productID,
			@RequestParam String lensModel, @RequestParam String lensBrand,
			@RequestParam Integer lensPrice, @RequestParam String lensMount,
			@RequestParam String lensFocalLength, @RequestParam String lensGroup,
			@RequestParam String lensOIS, @RequestParam String lensMagnification,
			@RequestParam String lensMinFocusDist, @RequestParam Float lensApertureMin,
			@RequestParam Integer lensApertureMax, @RequestParam String lensBlades,
			@RequestParam String lensFilterSize, @RequestParam String lensDims,
			@RequestParam Integer lensWeight, @RequestParam String lensFOV,
			@RequestParam String lensDrive, @RequestParam byte[] lensPhoto,
			@RequestParam int lensStocks
) throws IOException{
		Optional<Lens> optional = lensRepo.findById(productID);
		if(optional.isPresent()) {
			Lens lens = optional.get();
			
			lens.setLensModel(lensModel);
			lens.setLensBrand(lensBrand);
			lens.setLensPrice(lensPrice);
			lens.setLensMount(lensMount);
			lens.setLensFocalLength(lensFocalLength);
			lens.setLensGroup(lensGroup);
			lens.setLensOIS(lensOIS);
			lens.setLensMagnification(lensMagnification);
			lens.setLensMinFocusDist(lensMinFocusDist);
			lens.setLensApertureMin(lensApertureMin);
			lens.setLensApertureMax(lensApertureMax);
			lens.setLensBlades(lensBlades);
			lens.setLensFilterSize(lensFilterSize);
			lens.setLensDims(lensDims);
			lens.setLensWeight(lensWeight);
			lens.setLensFOV(lensFOV);
			lens.setLensDrive(lensDrive);
			lens.setLensPhoto(lensPhoto);
			lens.setLensStocks(lensStocks);
			
			return lensRepo.save(lens);
		}
		return null;
	}
	
//////////前台//////////
public Page<Lens> findBylensPage(Integer pageNumber) {
	Pageable pgb = PageRequest.of(pageNumber - 1, 12, Sort.Direction.DESC, "lensBrand");

	Page<Lens> page = lensRepo.findAll(pgb);
	return page;
}



}