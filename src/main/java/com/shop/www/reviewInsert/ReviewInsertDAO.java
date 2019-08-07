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

	public Map<String, Object> getProductInfo(HashMap<String, Object> map) {
		String SQL = "SELECT TITLE,SEQ,PRICE,REL_REG_DT,"
				+ "THUM_IMG_PATH,LAST_REG_DT,LAST_MOD_ID"
				+ " FROM PRODUCT WHERE SEQ=? AND PARENT_SEQ=?";
		try {
			return template.queryForMap(SQL, map.get("seq"), map.get("parentSeq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Map<String,Object> productInsertAndReturnSeq(HashMap<String, Object> map) {
		String SQL= "INSERT INTO PRODUCT(TITLE,RATING_AVG,REL_REG_DT,"
				+ "MAN_COMPANY,THUM_IMG_PATH,PARENT_SEQ,"
				+ "LAST_REG_DT,LAST_REG_IP,PRICE,LAST_MOD_ID)"
				+ " VALUES(?,?,?,?,?,?,NOW(),?,?,?)";
		
		template.update(SQL,map.get("proTitle"),map.get("rating"),map.get("proRegdate"),
				map.get("manCompany"),map.get("proThumnailImg"),map.get("subCategorySeq"),
				map.get("ip"),map.get("proPrice"),map.get("nick"));
		
		SQL="SELECT LAST_INSERT_ID()";
		return template.queryForMap(SQL);
	}
	
	public Map<String,Object> manCompanyInsertAndReturnSeq(HashMap<String, Object> map) {
		String SQL= "INSERT INTO MAN_COMPANY(TITLE,PARENT_SEQ) VALUES(?,?)";
		template.update(SQL,map.get("proCompany"),map.get("subCategorySeq"));
		
		SQL="SELECT LAST_INSERT_ID()";
		return template.queryForMap(SQL);
	}
	
	// 리뷰 이미지 섬네일 컨텐츠 서비스단에서 추가필요
	public void reviewInsert(HashMap<String, Object> map) {
		String SQL="INSERT INTO REVIEW(TITLE,CONTENTS,THUM_IMG_PATH,"
				+ "RATING,PARENT_SEQ,THUM_CONTENT,FRST_REG_ID,"
				+ "FRST_REG_IP,FRST_REG_DT,LAST_MOD_ID,"
				+ "LAST_MOD_IP,LAST_MOD_DT,PRODUCT_SEQ,MEM_SEQ)"
				+ " VALUES(?,?,?,?,?,?,?,?,NOW(),?,?,NOW(),?,?)";
		System.out.println(map.get("subCategorySeq"));
		template.update(SQL,map.get("reviewTitle"),map.get("reviewContents"),map.get("reviewImg"),
				map.get("rating"),map.get("subCategorySeq"),map.get("reviewThumContent"),
				map.get("nick"),map.get("ip"),map.get("nick"),map.get("ip"),
				map.get("productSeq"),map.get("memberSeq"));
	}


}
