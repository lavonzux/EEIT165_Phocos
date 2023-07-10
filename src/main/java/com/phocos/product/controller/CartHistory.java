package com.phocos.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.phocos.product.model.ShoppingCartItem;
import com.phocos.product.service.ShoppingCartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartHistory {
	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping("/carthistory")
	public String getByMemberId(Model model, HttpSession session) {
		Integer memberID = (Integer) session.getAttribute("memberID");
		if (memberID == null) {
			return "redirect:/login"; // 如果会话中没有memberId，则重定向到其他页面
		}
		List<ShoppingCartItem> shoppingCartItems = shoppingCartService.getByMemberId(memberID);
		model.addAttribute("shoppingCartItems", shoppingCartItems);
		return "forestage/towakawaii/shoppinghistory";

	}
}
