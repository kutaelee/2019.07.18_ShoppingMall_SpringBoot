package com.shop.www.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainDAO {

	@Autowired
	private JdbcTemplate template;
	
	public List<Map<String, Object>> getNewReview() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM REVIEW"
					+ " ORDER BY FRST_REG_DT"
					+ " LIMIT 2";
		List<Map<String, Object>> list = template.queryForList(SQL);
		return list;
	}

	public List<Map<String, Object>> getBestReview() {
		String SQL = "SELECT * FROM REVIEW"
					+ " WHERE FRST_REG_DT>DATE_SUB(NOW() , INTERVAL 7 DAY)"
					+ " ORDER BY LIKE_CNT DESC"
					+ " LIMIT 2";
		return template.queryForList(SQL);
	}
}
