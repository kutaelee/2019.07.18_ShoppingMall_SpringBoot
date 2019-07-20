package com.example.demo.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MemberService {
	@Autowired
	MemberDAO md;
	
	public void getSelect(Model model) {
		HashMap<String,Object> reqMap = (HashMap<String, Object>) model.asMap().get("reqMap");
		model.addAttribute("list",md.getSelect(reqMap));
		List<Map<String,Object>> test = md.getSelect(reqMap);
		System.out.println("Service");
		System.out.println("size:"+test.size());
		for(Map<String,Object> map : test) {
			Iterator it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next().toString();
				System.out.println("key:"+key +"\t value:"+map.get(key));
			}
		}
		
		
	}
}
