package com.shop.www.account;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.coyote.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	
	/* 회원가입 */
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

	/* email,id 중복체크 */
	@PostMapping("/ajax/doubleCheck")
	public boolean doubleCheck(HttpServletRequest req) throws Exception {
		//이미 있는 값이라면 true 리턴
		if(!StringUtils.isEmptyOrWhitespaceOnly(ad.doubleCheck(requtil.reqToHashMap(req)))) {
			return true;
		}else {
			return false;
		}
	}
	
	/* 세션 체크 */
	@PostMapping("/ajax/sessionCheck")
	public boolean sessionCheck(HttpServletRequest req) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		if(principal !=null && principal instanceof UserDetails) {
			return true;
		}else {
			return false;
		}
	}
}
