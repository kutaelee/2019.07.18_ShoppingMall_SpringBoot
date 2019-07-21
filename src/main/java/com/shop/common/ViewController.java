package com.shop.common;

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
		return "account";
	}
	@GetMapping("/searchpage")
	public String searchpage() {
		return "searchpage";
	}
	
	@GetMapping("/sign")
	public String sign() {
		return "sign";
	}
}
