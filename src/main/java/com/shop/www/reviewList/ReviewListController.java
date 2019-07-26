package com.shop.www.reviewList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.www.common.ReqUtil;

@RestController
public class ReviewListController {
	@Autowired
	ReqUtil requtil;
	@Autowired
	ReviewListDAO rd;
	@Autowired
	ReviewListService rs;
	
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
		HashMap<String, Object> map=requtil.reqToHashMap(req);
		if(map.size()>1 && map.toString().contains("checkedCompanySeqList")) {
			List<Map<String, Object>> companySeq=rd.getProductSeqListForCompany(map);
			map=rs.AdvencedSearchproduct(map,companySeq,"companySeq");
		}
		
		if(map.size()>1 && map.toString().contains("checkedPrice")) {
			List<Map<String, Object>> priceSeq=rd.getProductSeqListForPrice(map);
			map=rs.AdvencedSearchproduct(map,priceSeq,"priceSeq");
		}
			return rd.getReviewCount(map);
	}

	@PostMapping("/ajax/getReviewList")
	public List<Map<String, Object>> getReviewList(HttpServletRequest req) throws Exception {
		HashMap<String, Object> map=requtil.reqToHashMap(req);
		if(map.size()>2 && map.toString().contains("checkedCompanySeq")) {
			List<Map<String, Object>> companySeq=rd.getProductSeqListForCompany(map);
			map=rs.AdvencedSearchproduct(map,companySeq,"companySeq");
		}
		if(map.size()>2 && map.toString().contains("checkedPrice")) {
			List<Map<String, Object>> priceSeq=rd.getProductSeqListForPrice(map);
			map=rs.AdvencedSearchproduct(map,priceSeq,"priceSeq");
		}
			return rd.getReviewList(map);
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
