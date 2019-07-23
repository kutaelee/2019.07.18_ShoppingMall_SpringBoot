package com.shop.www.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<Map<String, Object>> getReviewList(HashMap<String, Object> map) {
		String SQL = "SELECT SEQ,TITLE,CONTENTS,"
				+ "THUM_IMG_PATH,PARENT_SEQ,LIKE_CNT,"
				+ "RATING,FRST_REG_DT,FRST_REG_ID"
				+ " FROM REVIEW"
				+ " WHERE PARENT_SEQ=? AND DEL_YN='N'"
				+ " ORDER BY FRST_REG_DT DESC";
		
		try {
			return template.queryForList(SQL, map.get("seq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
