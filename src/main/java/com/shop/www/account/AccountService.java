package com.shop.www.account;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
	@Autowired
	AccountDAO ad;
	@Autowired
	BCryptPasswordEncoder bCryptencoder;
	
	public void insertUser(HashMap<String, Object> map) {
		map.put("id", regularText(map.get("id").toString()));
		map.put("pw", passwordEncoding(map.get("pw").toString()));
		ad.insertUser(map);
	}

	//비밀번호 암호화
	public String passwordEncoding(String pw) {
		return bCryptencoder.encode(pw);
	}
	//비밀번호 매칭
	public boolean compareToPassword(String encodedPw,String inputPw) {
		return bCryptencoder.matches(inputPw, encodedPw);
	}
	
	//sha256 암호화
	public Map<String, PasswordEncoder> sha256Encoding(HashMap<String,Object> map) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		
		for(String key:map.keySet()) {
			encoders.put(key, new MessageDigestPasswordEncoder(map.get(key).toString()));
		}
		return encoders;
	}

	//특수문자 치환
	public String regularText(String str) {
		if (Pattern.matches("^[a-zA-Z0-9]*$", str)) {
			return StringEscapeUtils.escapeHtml4(str);
		} else {
			return str;
		}
	}

	//로그인 관련 서비스
	public boolean pwCheck(HashMap<String, Object> map) {
		Map<String, Object> pw=ad.getPw(map);
		if(ObjectUtils.isEmpty(pw)) {
			return false;
		}
		return compareToPassword(pw.get("PASS").toString(),map.get("pw").toString());
	}
}
