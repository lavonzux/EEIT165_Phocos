package com.phocos.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PageController {
	@GetMapping("/test")
	public String test() {
		return "/forestage/index";
	}
}
