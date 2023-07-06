package com.phocos.product.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.phocos.product.model.Lens;
import com.phocos.product.service.LensService;

@Controller
public class LensController {

	@Autowired
	private LensService lensService;

	@GetMapping("/lenss")
	public String processMainAction(Model m) {
		List<Lens> lens = lensService.findAll();
		m.addAttribute("lens", lens);
		return "backstage/towakawaii/LensHome";
	}

	@GetMapping("/product/insertlenspage")
	public String insertPage() {
		return "backstage/towakawaii/LensAdd";
	}

	@GetMapping("/product/lensdetail")
	public String detailPage(@RequestParam("productID") Integer productID, Model model) {
		Lens lens = lensService.getById(productID);
		model.addAttribute("lens", lens);
		return "backstage/towakawaii/LensShow";
	}

	@PostMapping("/product/insertlens")
	public String insertLens(@RequestParam("lensModel") String lensModel, @RequestParam("lensBrand") String lensBrand,
			@RequestParam("lensPrice") Integer lensPrice, @RequestParam("lensMount") String lensMount,
			@RequestParam("lensFocalLength") String lensFocalLength, @RequestParam("lensGroup") String lensGroup,
			@RequestParam("lensOIS") String lensOIS, @RequestParam("lensMagnification") String lensMagnification,
			@RequestParam("lensMinFocusDist") String lensMinFocusDist,
			@RequestParam("lensApertureMin") Float lensApertureMin,
			@RequestParam("lensApertureMax") Integer lensApertureMax, @RequestParam("lensBlades") String lensBlades,
			@RequestParam("lensFilterSize") String lensFilterSize, @RequestParam("lensDims") String lensDims,
			@RequestParam("lensWeight") Integer lensWeight, @RequestParam("lensFOV") String lensFOV,
			@RequestParam("lensDrive") String lensDrive, @RequestParam("lensPhoto") MultipartFile lensPhoto,
			@RequestParam("lensStocks") int lensStocks) {
		try {
			Lens lens = new Lens();
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
			lens.setLensPhoto(lensPhoto.getBytes());
			lens.setLensStocks(lensStocks);
			lensService.insert(lens);
			return "redirect:/lenss";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/lenss";
	}

	@DeleteMapping("/product/deletelens")
	public String deleteCamera(@RequestParam("productID") Integer productID) {
		lensService.deleteById(productID);
		return "redirect:/lenss";
	}

	@GetMapping("/product/editlens")
	public String editPage(@RequestParam("productID") Integer productID, Model model) {
		Lens lens = lensService.getById(productID);
		model.addAttribute("lens", lens);
		return "backstage/towakawaii/LensUpdate";
	}

	@GetMapping("/downloadlensImage/{id}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id) {
		Lens lens1 = lensService.getphotoById(id);
		byte[] photoFile = lens1.getLensPhoto();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		// 檔案, header, HttpStatus
		return new ResponseEntity<byte[]>(photoFile, headers, HttpStatus.OK);
	}

	@PostMapping("/product/editlensData")
	public String editPost(@RequestParam("productID") Integer productID, @RequestParam("lensModel") String lensModel,
			@RequestParam("lensBrand") String lensBrand, @RequestParam("lensPrice") Integer lensPrice,
			@RequestParam("lensMount") String lensMount, @RequestParam("lensFocalLength") String lensFocalLength,
			@RequestParam("lensGroup") String lensGroup, @RequestParam("lensOIS") String lensOIS,
			@RequestParam("lensMagnification") String lensMagnification,
			@RequestParam("lensMinFocusDist") String lensMinFocusDist,
			@RequestParam("lensApertureMin") Float lensApertureMin,
			@RequestParam("lensApertureMax") Integer lensApertureMax, @RequestParam("lensBlades") String lensBlades,
			@RequestParam("lensFilterSize") String lensFilterSize, @RequestParam("lensDims") String lensDims,
			@RequestParam("lensWeight") Integer lensWeight, @RequestParam("lensFOV") String lensFOV,
			@RequestParam("lensDrive") String lensDrive, @RequestParam("lensPhoto") MultipartFile lensPhoto,
			@RequestParam("lensStocks") int lensStocks,
			Model model1) throws IOException {
		byte[] lensbyte = null;
		lensbyte = lensPhoto.getBytes();
		Lens updatelens = lensService.updateLensById(productID, lensModel, lensBrand, lensPrice, lensMount,
				lensFocalLength, lensGroup, lensOIS, lensMagnification, lensMinFocusDist, lensApertureMin,
				lensApertureMax, lensBlades, lensFilterSize, lensDims, lensWeight, lensFOV, lensDrive, lensbyte,lensStocks);
		ResponseEntity<byte[]> imagEntity = downloadImage(productID);
		byte[] lensphoto = imagEntity.getBody();
		model1.addAttribute("lensphoto", lensphoto);
		return "redirect:/lenss";
	}
	
	////////////////// 前台////////////////////
	
	@GetMapping("/products/lensshop")
	public String getlensPage(@RequestParam(name="p", defaultValue = "1") Integer pageNumber, Model model) {

		Page<Lens> page = lensService.findBylensPage(pageNumber);
		model.addAttribute("page", page);
		return "forestage/towakawaii/LensShop"; // 返回對應的視圖模板
	}
}
