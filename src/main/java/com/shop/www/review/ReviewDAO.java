package com.shop.www.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO {
	@Autowired
	JdbcTemplate template;

	public String getSubCategoryTitle(HashMap<String, Object> map) {
		String SQL = "SELECT TITLE FROM SUB_CATEGORY" + " WHERE SEQ=?";
	
		try {
			return template.queryForMap(SQL, map.get("seq")).get("title").toString();
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public String getSubCategoryParentSeq(HashMap<String, Object> map) {
		String SQL = "SELECT PARENT_SEQ FROM SUB_CATEGORY" + " WHERE SEQ=?";
	
		try {
			return template.queryForMap(SQL, map.get("parentSeq")).get("PARENT_SEQ").toString();
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/* 상세검색 조건문 쿼리 추가 */
	public String AdvencedSearchAddSQL(HashMap<String, Object> map,String keyName) {
		int idx=0;
		StringBuffer SQL=new StringBuffer();
		for(String key:map.keySet()) {
			if(key.contains(keyName)) {
			if(idx==0) {
				SQL.append(" AND (");
			}else {
				SQL.append(" OR");
			}
			SQL.append(" PRODUCT_SEQ="+map.get(key));
			idx++;
			}
		}
		if(idx>0) {
			SQL.append(")");
		}
		return SQL.toString();
	}

	public String getReviewCount(HashMap<String, Object> map) {
		String SQL = "SELECT COUNT(*) FROM REVIEW" + " WHERE PARENT_SEQ=? AND DEL_YN='N'";
		SQL+=AdvencedSearchAddSQL(map,"companySeq");
		SQL+=AdvencedSearchAddSQL(map,"priceSeq");

		try {
			return template.queryForObject(SQL,new Object[] {map.get("parentSeq")}, String.class);
		} catch (NullPointerException e) {
			return "0";
		}

	}
	
	public List<Map<String, Object>> getProductSeqListForCompany(HashMap<String, Object> map) {
		String SQL = "SELECT SEQ FROM PRODUCT WHERE PARENT_SEQ=?";
		int idx=0;
		for(String key:map.keySet()) {
			if(key.contains("checkedCompany")) {
				if(idx==0) {
					SQL +=" AND";
				}else {
					SQL +=" OR";
				}
				SQL+=" MAN_COMPANY="+map.get(key);
				idx++;
			}
		}
		try {
			return template.queryForList(SQL, map.get("parentSeq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}
	
	public List<Map<String, Object>> getProductSeqListForPrice(HashMap<String, Object> map) {
		String SQL = "SELECT SEQ FROM PRODUCT WHERE PARENT_SEQ=?";
		int idx=0;
		for(String key:map.keySet()) {
			if(key.contains("checkedPrice")) {
			if(idx==0) {
				SQL +=" AND";
			}else {
				SQL +=" OR";
			}
			if(key.contains("checkedPriceL")) {
				SQL+=" PRICE>"+map.get(key);
				idx++;
			}else if(key.contains("checkedPriceF")) {
				SQL+=" PRICE<"+map.get(key);
				idx++;
			}else if(key.contains("checkedPrice")) {
				SQL+=" PRICE BETWEEN "+map.get(key);
				idx++;
			}
			}
		}
		try {
			return template.queryForList(SQL,map.get("parentSeq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}
	
	public List<Map<String, Object>> getReviewList(HashMap<String, Object> map) {
		String SQL = "SELECT SEQ,TITLE,CONTENTS,"
				+ "THUM_IMG_PATH,PARENT_SEQ,LIKE_CNT,"
				+ "RATING,FRST_REG_DT,FRST_REG_ID"
				+ " FROM REVIEW"
				+ " WHERE PARENT_SEQ=? AND DEL_YN='N'";
		
		SQL+=AdvencedSearchAddSQL(map,"companySeq");
		SQL+=AdvencedSearchAddSQL(map,"priceSeq");
		
		SQL+=" ORDER BY FRST_REG_DT DESC"
				+ " LIMIT ?,5";
		try {
			return template.queryForList(SQL, map.get("parentSeq"),Integer.parseInt(map.get("index").toString()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Map<String, Object>> getSubCategoryMatchParentSeq(HashMap<String, Object> map) {
		String SQL = "SELECT SEQ,TITLE,PARENT_SEQ FROM SUB_CATEGORY"
				+ " WHERE PARENT_SEQ=?"
				+ " ORDER BY DISP_ORDER";
		try {
			return template.queryForList(SQL,map.get("parentSeq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public List<Map<String, Object>> getManCompanyTitle(HashMap<String, Object> map) {
		String SQL = "SELECT SEQ,TITLE FROM MAN_COMPANY"
				+ " WHERE PARENT_SEQ=?";
		try {
			return template.queryForList(SQL,map.get("parentSeq"));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
