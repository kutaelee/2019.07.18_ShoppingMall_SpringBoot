package com.shop.www.account;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.coyote.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
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
	
	/*
	 * @PostMapping("/ajax/insertUser") public void insertUser(HttpServletRequest
	 * req) throws Exception{ HashMap<String,Object> map=requtil.reqToHashMap(req);
	 * map.put("ip", requtil.getRemoteIP(req)); as.insertUser(map); }
	 */
	@PostMapping("/ajax/insertUser")
	public void insertUser(HttpServletRequest req) throws Exception{
		AccountDTO account=new AccountDTO();
		account.setEmail(req.getParameter("email"));
		account.setUserName(req.getParameter("id"));
		account.setFrstRegIp(requtil.getRemoteIP(req));
		account.setNick(req.getParameter("id"));
		account.setLastModId(req.getParameter("id"));
		account.setLastModIp(requtil.getRemoteIP(req));
		account.setPass(req.getParameter("pw"));
		as.save(account,"ROLE_USER");
	}
	/*
	 * @PostMapping("/ajax/login") public boolean login(HttpServletRequest
	 * req,HttpSession session) throws Exception { HashMap<String,Object>
	 * map=requtil.reqToHashMap(req); if(ObjectUtils.isEmpty(map.get("username")) ||
	 * ObjectUtils.isEmpty(map.get("password"))) { return false; }
	 * if(as.pwCheck(map)) { UserDetails
	 * userDetails=as.loadUserByUsername(req.getParameter("id"));
	 * System.out.println(userDetails.toString()); return true; }else { return
	 * false; }
	 * 
	 * }
	 */
	
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
