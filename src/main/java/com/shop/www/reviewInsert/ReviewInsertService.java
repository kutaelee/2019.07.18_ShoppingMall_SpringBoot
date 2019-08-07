package com.shop.www.reviewInsert;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReviewInsertService {
	@Autowired
	ReviewInsertDAO rd;
	
	public boolean newProductReviewInsert(HashMap<String, Object> map) {
		map.put("manCompany", rd.manCompanyInsertAndReturnSeq(map).get("LAST_INSERT_ID()").toString());
		map.put("productSeq", rd.productInsertAndReturnSeq(map).get("LAST_INSERT_ID()").toString());
		rd.reviewInsert(map);
		return true;
	}

	
}
