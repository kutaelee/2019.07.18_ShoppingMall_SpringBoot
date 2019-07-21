package com.shop.common;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public class CommonClass {
	public static void mapToString(Map<String,Object> map, String msg) {
		Iterator it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next().toString();
			System.out.println(msg+" [key :" + key + "\t value :" +map.get(key)+"]");
		}
		if(!it.hasNext()) {
			System.out.println(msg+" [mapToString >No Data]");
		}
	}
	
	public static void common(HttpServletRequest request, Model model) throws Exception {
		Map<String,Object> map = ReqUtil.reqToHashMap(request);
		model.addAttribute("reqMap",map);
	}
}
