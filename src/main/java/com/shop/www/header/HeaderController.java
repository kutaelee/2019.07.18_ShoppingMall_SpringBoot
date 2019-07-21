package com.shop.www.header;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderController {
	@Autowired
	HeaderService hs;
	@Autowired
	HeaderDAO hd;
	
	@PostMapping("/ajax/getMainCategory")
	public List<Map<String,Object>> getMainCategory(){
		return hd.getMainCategory();
	}
	@PostMapping("/ajax/getSubCategory")
	public List<Map<String,Object>> getSubCategory(){
		return hd.getSubCategory();
	}
}
