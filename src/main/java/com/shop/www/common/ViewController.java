package com.shop.www.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	@GetMapping("/")
	public String index(){
		return "index";
	}	
	@GetMapping("/account")
	public String loginpage() {
		return "account/account";
	}
	@GetMapping("/searchpage")
	public String searchpage() {
		return "search/searchpage";
	}
	@GetMapping("/review/*")
	public String review() {
		return "review/read";
	}
	
}
