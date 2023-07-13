package com.phocos.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phocos.product.model.ShoppingCartItem;
import com.phocos.product.service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController   {
	@Autowired
	private ShoppingCartService shoppingCartService;
 
    @PostMapping("/store")
    public String storeShoppingCart(@RequestBody List<ShoppingCartItem> shoppingCartItems, HttpSession session) {
        Integer memberID = (Integer) session.getAttribute("memberID");
        
        for (ShoppingCartItem item : shoppingCartItems) {
            item.setMemberID(memberID);
        }
        
        shoppingCartService.storeShoppingCartItems(shoppingCartItems);
        
        return "Data stored successfully.";
    }

	@GetMapping("/products/shoppingcar")
	public String gotoshoppingcar(Model model, HttpSession session) {
	    Integer memberId = (Integer) session.getAttribute("memberID");

	    if (memberId != null) {
	        // 存在 memberId，执行跳转到 ShoppingCar
	        return "forestage/towakawaii/ShoppingCar";
	    } else {
	        // 不存在 memberId，跳回 login
	        return "redirect:/login";
	    }
	}

	@GetMapping("/products/shoppingcar2")
	public String gototheshoppingcar(Model m) {
		return "forestage/towakawaii/ShoppingCar";
	}
//	@PostMapping("/calculateTotalPrice")
//	public ModelAndView calculateTotalPrice2(@RequestBody List<ShoppingCartItem> shoppingCartItems) {
//	    int totalPrice = 0;
//	    for (ShoppingCartItem item : shoppingCartItems) {
//	        totalPrice += item.getPrice();
//	    }
//
//	    String form = orderService.ecpayCheckout(totalPrice);
//
//	    ModelAndView modelAndView = new ModelAndView("generateHtml");
//	    modelAndView.addObject("form", form);
//	    modelAndView.addObject("totalPrice", totalPrice);
//	    return modelAndView;
//	} 
	@GetMapping("/generateHtml")
    public String generateHtml() {

        return "forestage/towakawaii/htmlPage";
    }
}
