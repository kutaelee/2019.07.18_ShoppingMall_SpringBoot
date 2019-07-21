package com.shop.www.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.www.common.CommonClass;


@RestController
public class MainController {

	@Autowired
	MainService ms;
	
	@PostMapping("/ajax/getNewReview")
	public List<Map<String,Object>> getNewRewview(HttpServletRequest request, Model model) {
		System.out.println("getNewReview");
		try {
			CommonClass.common(request, model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ms.getNewReview();
	}
}
