package com.shop.www.account;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.coyote.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.util.StringUtils;
import com.shop.www.common.ReqUtil;


@RestController
public class AccountController {
	@Autowired
	ReqUtil requtil;
	@Autowired
	AccountService as;
	@Autowired
	AccountDAO ad;
	
	@PostMapping("/ajax/insertUser")
	public void insertUser(HttpServletRequest req) throws Exception{
		HashMap<String,Object> map=requtil.reqToHashMap(req);
		map.put("ip", requtil.getRemoteIP(req));
		as.insertUser(map);
	}
	
	@PostMapping("/ajax/doubleCheck")
	public boolean doubleCheck(HttpServletRequest req) throws Exception {
		//이미 있는 값이라면 true 리턴
		if(!StringUtils.isEmptyOrWhitespaceOnly(ad.doubleCheck(requtil.reqToHashMap(req)))) {
			return true;
		}else {
			return false;
		}
	
	}
}
