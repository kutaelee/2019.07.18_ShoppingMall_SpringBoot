package com.shop.www.reviewInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewInsertDAO {
	@Autowired
	JdbcTemplate template;

	public List<Map<String, Object>> getProductTitle(HashMap<String, Object> map) {
		String SQL = "SELECT TITLE,SEQ FROM PRODUCT WHERE MAN_COMPANY=? AND PARENT_SEQ=?";
		try {
			return template.queryForList(SQL, map.get("manSeq"), map.get("parentSeq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
