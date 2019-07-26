package com.shop.www.reviewPage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewPageDAO {
	@Autowired
	JdbcTemplate template;
	
	public Map<String, Object> getReviewInfo(HashMap<String,Object> map) {
		String SQL = "SELECT R.TITLE , P.TITLE FROM REVIEW AS R JOIN PRODUCT AS P ON R.PRODUCT_SEQ=P.SEQ WHERE R.SEQ=?";
		
		return template.queryForMap(SQL,map.get("seq"));
	}

}
