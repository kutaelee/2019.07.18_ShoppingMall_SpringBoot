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
	
	@PostMapping("/ajax/getMainCategoryList")
	public List<Map<String,Object>> getMainCategoryList(){
		return hd.getMainCategoryList();
	}
	@PostMapping("/ajax/getSubCategoryList")
	public List<Map<String,Object>> getSubCategoryList(){
		return hd.getSubCategoryList();
	}
}
