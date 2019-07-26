package com.shop.www.reviewPage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.www.common.ReqUtil;

@RestController
public class ReviewPageController {
	@Autowired
	ReviewPageDAO rd;
	@Autowired
	ReqUtil requtil;
	
	@GetMapping("/ajax/getReviewInfo")
	public List<Map<String,Object>> getReviewInfo(HttpServletRequest req) throws Exception{
		return rd.getReviewInfo(requtil.reqToHashMap(req));
	}
}
