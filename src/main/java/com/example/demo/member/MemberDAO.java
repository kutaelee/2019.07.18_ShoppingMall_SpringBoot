package com.example.demo.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		List list=new ArrayList<HashMap<String,Object>>();
		list=template.queryForList(SQL);
		
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).toString());
		}
		
	
	}
	
}
