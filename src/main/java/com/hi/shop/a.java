package com.hi.shop;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class a {
	@GetMapping("/aa")
	public String aa() {
		return "index";
	}
}
