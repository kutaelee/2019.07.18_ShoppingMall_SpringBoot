package com.shop.www.header;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HeaderDAO {
	@Autowired
	private JdbcTemplate template;
	
	public List<Map<String, Object>> getMainCategory() {
		String SQL = "SELECT * FROM MAIN_CATEGORY"
				+ " ORDER BY DISP_ORDER";
		return template.queryForList(SQL);
	}

	public List<Map<String, Object>> getSubCategory() {
		String SQL = "SELECT * FROM SUB_CATEGORY"
				+ " ORDER BY DISP_ORDER";
		return template.queryForList(SQL);
	}

}
