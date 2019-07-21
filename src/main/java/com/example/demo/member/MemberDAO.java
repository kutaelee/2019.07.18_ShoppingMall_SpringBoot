package com.example.demo.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	@Autowired
	MemberVO mv;
	@Autowired
	private JdbcTemplate template;
	
	public void getUser() {
		String SQL = "select * from member"; 
		List list=new ArrayList<Map<String,Object>>();
		list=template.queryForList(SQL);
		
		System.out.println(list.toString());
	
	}
	
	public List<Map<String,Object>> getSelect(HashMap<String,Object> reqMap){
		String SQL = "select * from member"; 
		List<Map<String, Object>> list = template.queryForList(SQL);
		
		
		return list;
	}

	public List<Map<String, Object>> getNewReview() {
		// TODO Auto-generated method stub
		String SQL = " SELECT * FROM REVIEW"
					+ " ORDER BY FRST_REG_DT"
					+ " LIMIT 2";
		List<Map<String, Object>> list = template.queryForList(SQL);
		return list;
	}
	
}
