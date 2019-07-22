package com.shop.www.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
	@Autowired
	JdbcTemplate template;
	
	public void insertUser(Map<String, Object> info) {
		System.out.println(info.toString());
		String SQL = "INSERT INTO MEMBER(ID,PASS,EMAIL"
				+ ",FRST_REG_IP,LAST_MOD_IP,LAST_MOD_ID"
				+ ",NICK,FRST_REG_DT,LAST_MOD_DT)"
				+ " VALUES(?,?,?,?,?,?,?,now(),now())";
		template.update(SQL,info.get("id"),info.get("pw"),info.get("email")
				,info.get("ip"),info.get("ip"),info.get("id"),info.get("id"));
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

	public Map<String,Object> getPw(HashMap<String,Object> map) {
		String SQL="SELECT PASS FROM MEMBER"
				+" WHERE ID=?";
		try {
			return template.queryForMap(SQL,map.get("id"));
		}catch (EmptyResultDataAccessException e){
			return null;
		}
	}

}
