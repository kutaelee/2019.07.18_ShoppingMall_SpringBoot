package com.shop.www.reviewPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewPageDAO {
	@Autowired
	JdbcTemplate template;
	
	public List<Map<String, Object>> getReviewInfo(HashMap<String,Object> map) {
		String SQL = "SELECT P.SEQ AS P_SEQ,P.TITLE AS P_TITLE,P.THUM_IMG_PATH AS P_THUM_IMG_PATH,"
				+ "P.SIZE,P.RATING_AVG,P.REL_REG_DT,"
				+ "P.MAN_COMPANY,P.LAST_REG_DT,P.PRICE,"
				+ "R.TITLE AS R_TITLE,R.SEQ AS R_SEQ,R.THUM_IMG_PATH AS R_THUM_IMG_PATH,"
				+ "R.LAST_MOD_DT AS R_LAST_MOD_DT,R.CONTENTS,R.CNT,"
				+ "R.LIKE_CNT,R.RATING,R.FRST_REG_ID,R.FRST_REG_DT,"
				+ "R.LAST_MOD_ID AS R_LAST_MOD_ID,P.LAST_MOD_ID AS P_LAST_MOD_ID,R.PARENT_SEQ"
				+ " FROM REVIEW AS R JOIN PRODUCT AS P ON R.PRODUCT_SEQ=P.SEQ WHERE R.SEQ=?"
				+ " AND DEL_YN='N'";

		try {
			return template.queryForList(SQL,map.get("seq"));
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void likeUpdate(HashMap<String, Object> map) {
		String SQL = "UPDATE REVIEW SET LIKE_CNT=LIKE_CNT+? WHERE SEQ=?";
		template.update(SQL,map.get("cnt"),map.get("seq"));
	}

	public String getLikeCnt(HashMap<String, Object> map) {
		String SQL = "SELECT LIKE_CNT FROM REVIEW WHERE SEQ=?";
		return template.queryForMap(SQL,map.get("seq")).get("LIKE_CNT").toString();
	}

}
