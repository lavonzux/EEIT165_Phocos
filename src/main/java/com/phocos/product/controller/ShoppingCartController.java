package com.phocos.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.product.model.ShoppingCartItem;
import com.phocos.product.service.ShoppingCartService;

@RestController
@RequestMapping("/api")
public class ShoppingCartController   {
	@Autowired
	private ShoppingCartService shoppingCartService;
 
	@PostMapping("/store")
	public String storeShoppingCart(@RequestBody List<ShoppingCartItem> shoppingCartItems) {
		shoppingCartService.storeShoppingCartItems(shoppingCartItems);
		return "Data stored successfully.";
	}

}
