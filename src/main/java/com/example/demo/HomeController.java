package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {

	@GetMapping("/")
	public String index(){
		return "index";
	}	
	@GetMapping("/account")
	public String loginpage() {
		return "account";
	}
	@GetMapping("/searchpage")
	public String searchpage() {
		return "searchpage";
	}
}