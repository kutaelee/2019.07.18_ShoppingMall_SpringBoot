package com.shop.www.review;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.www.common.ReqUtil;

@RestController
public class ReviewController {
	@Autowired
	ReqUtil requtil;
	@Autowired
	ReviewDAO rd;
	
	@PostMapping("/ajax/getSubCategory")
	public String getSubCategory(HttpServletRequest req) throws Exception {
		return rd.getSubCategory(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/getReviewCount")
	public String getReviewCount(HttpServletRequest req) throws Exception {
			return rd.getReviewCount(requtil.reqToHashMap(req));
	}
}
