package com.example.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	@Autowired
	MemberDAO md;
	@GetMapping("/sign")
	public String sign() {
		return "sign";
	}
	@GetMapping("/user")
	public String user() {
		md.getUser();
		return "sign";
	}
}
