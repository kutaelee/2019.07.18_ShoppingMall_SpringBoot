package com.shop.www.review;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
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
	
	@PostMapping("/ajax/getSubCategoryTitle")
	public String getSubCategoryTitle(HttpServletRequest req) throws Exception {
		return rd.getSubCategoryTitle(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/getSubCategoryParentSeq")
	public String getSubCategoryParentSeq(HttpServletRequest req) throws Exception {
		return rd.getSubCategoryParentSeq(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/getReviewCount")
	public String getReviewCount(HttpServletRequest req) throws Exception {
			return rd.getReviewCount(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/getReviewList")
	public List<Map<String, Object>> getReviewList(HttpServletRequest req) throws Exception {
			return rd.getReviewList(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/getSubCategoryMatchParentSeq")
	public List<Map<String, Object>> getSubCategoryMatchParentSeq(HttpServletRequest req) throws Exception{
		return rd.getSubCategoryMatchParentSeq(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/getManCompanyTitle")
	public List<Map<String, Object>> getManCompanyTitle(HttpServletRequest req) throws Exception {
		return rd.getManCompanyTitle(requtil.reqToHashMap(req));
	}

}
