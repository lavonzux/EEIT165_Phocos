package com.phocos.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import com.phocos.product.model.ShoppingCartItem;
import com.phocos.product.service.OrderService;
import com.phocos.product.service.ShoppingCartService;

@Controller
public class HtmlGenerationController {

    @Autowired
    private TemplateEngine templateEngine;

	@Autowired
	private ShoppingCartService shoppingCartService;
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/generateHtml")
    public String generateHtml(Model model) {
        String form = orderService.ecpayCheckout2();
        model.addAttribute("form", form);
        return "forestage/towakawaii/htmlPage";
    }
	@GetMapping("/checkout")
	public String checkout(@RequestParam("totalPrice") int totalPrice, Model model) {

	    String form = orderService.ecpayCheckout(totalPrice);
	    model.addAttribute("form", form);

	    return "forestage/towakawaii/htmlPage";
	}
}