package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {

	@GetMapping("/")
	public String index(){
		return "index";
	}	
	@GetMapping("/loginpage")
	public String loginpage() {
		return "loginpage";
	}
	@GetMapping("/searchpage")
	public String searchpage() {
		return "searchpage";
	}
}