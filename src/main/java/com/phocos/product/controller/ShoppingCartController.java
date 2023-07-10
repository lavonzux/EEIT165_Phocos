package com.phocos.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.product.model.ShoppingCartItem;
import com.phocos.product.service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
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
}
