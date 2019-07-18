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
		Map<String,Object> map=(Map<String, Object>) list.get(0);

		for(String key:map.keySet()) {
			System.out.println(map.get(key));
		}
		
	
	}
	
}
