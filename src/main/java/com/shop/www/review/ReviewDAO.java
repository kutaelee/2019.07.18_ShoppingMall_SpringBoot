package com.shop.www.review;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO {
	@Autowired
	JdbcTemplate template;

	public String getSubCategory(HashMap<String, Object> map) {
		String SQL = "SELECT TITLE FROM SUB_CATEGORY" + " WHERE SEQ=?";
	
		try {
			return template.queryForMap(SQL, map.get("seq")).get("title").toString();
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String getReviewCount(HashMap<String, Object> map) {
		String SQL = "SELECT COUNT(*) FROM REVIEW" + " WHERE PARENT_SEQ=?";
		
		try {
			return template.queryForObject(SQL,new Object[] {map.get("seq")}, String.class);
		} catch (NullPointerException e) {
			return "0";
		}

	}

}
