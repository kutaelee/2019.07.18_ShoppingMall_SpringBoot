package com.shop.www.account;

import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
	@Autowired
	JdbcTemplate template;
	
	public void insertUser(HashMap<String, Object> map) {
		System.out.println(map.toString());
		String SQL = "INSERT INTO MEMBER(ID,PASS,EMAIL"
				+ ",FRST_REG_IP,LAST_MOD_IP,LAST_MOD_ID"
				+ ",NICK,FRST_REG_DT,LAST_MOD_DT)"
				+ " VALUES(?,?,?,?,?,?,?,now(),now())";
		template.update(SQL,map.get("id"),map.get("pw"),map.get("email")
				,map.get("ip"),map.get("ip"),map.get("id"),map.get("id"));
	}

	//회원가입 중복 체크
	public String doubleCheck(HashMap<String, Object> map) {
		String key=map.get("key").toString();
		String value=map.get("value").toString();
		
		String SQL="SELECT "+key+" FROM MEMBER"
				+" WHERE "+key+"=?";

		try {
			return template.queryForMap(SQL,value).toString();
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	
	}

}
