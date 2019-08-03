package com.shop.www.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.util.StringUtils;

@Service
@Transactional
public class AccountService implements UserDetailsService {
	@Autowired
	AccountDAO ad;
	@Autowired
	BCryptPasswordEncoder bCryptencoder;

	// 비밀번호 암호화
	public String passwordEncoding(String pw) {
		return bCryptencoder.encode(pw);
	}

	// 비밀번호 매칭
	public boolean compareToPassword(String encodedPw, String inputPw) {
		return bCryptencoder.matches(inputPw, encodedPw);
	}

	// sha256 암호화
	public Map<String, PasswordEncoder> sha256Encoding(HashMap<String, Object> map) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		for (String key : map.keySet()) {
			encoders.put(key, new MessageDigestPasswordEncoder(map.get(key).toString()));
		}
		return encoders;
	}

	// 특수문자 치환
	public String regularText(String str) {
		if (Pattern.matches("^[a-zA-Z0-9]*$", str)) {
			return StringEscapeUtils.escapeHtml4(str);
		} else {
			return str;
		}
	}

	// 유저 정보 반환
	public Map<String, Object> selectUser(String id) {
		return ad.selectUser(id);
	}

	// 관리자 체크 시큐리티에선 사용안함
	/*
	 * public boolean isIsadmin(Map<String, Object> user) { Map<String, Object>
	 * map=ad.getGrade(user.get("id").toString());
	 * if(map.get("grade").toString().equals("5")) { return true; }else { return
	 * false; } }
	 */

	// 시큐리티 로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountDTO account = findById(username);
		account.setAuthorities(getAuthorities(username));
		if (ObjectUtils.isEmpty(account.getUsername())) {
			throw new UsernameNotFoundException("유저를 찾을 수 없습니다. : " + username);
		}
		UserDetails userDetails = new UserDetails() {
			@Override
			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			@Override
			public String getUsername() {
				return account.getUsername();
			}

			@Override
			public String getPassword() {
				return account.getPassword();
			}

			@Override
			public Collection getAuthorities() {
				return account.getAuthorities();
			}

		};
		return userDetails;
	}

	public AccountDTO findById(String username) {
		try {
			return ad.findById(username);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public List<String> findAuthoritiesById(String username) {
		return ad.findAuthoritiesById(username);
	}

	public Collection<GrantedAuthority> getAuthorities(String username) {
		List<String> stringAuthorities = findAuthoritiesById(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String authority : stringAuthorities) {
			authorities.add(new SimpleGrantedAuthority(authority));
		}
		return authorities;
	}

	/* 회원가입 */
	public AccountDTO save(AccountDTO account, String role) {
		HashMap<String, Object> idMap = new HashMap<String, Object>();
		HashMap<String, Object> emailMap = new HashMap<String, Object>();
		idMap.put("key", "ID");
		idMap.put("value", account.getUsername());
		emailMap.put("key", "EMAIL");
		emailMap.put("value", account.getEmail());

		if (StringUtils.isEmptyOrWhitespaceOnly(ad.doubleCheck(idMap))
				&& StringUtils.isEmptyOrWhitespaceOnly(ad.doubleCheck(emailMap))) {
			account.setPass(bCryptencoder.encode(account.getPassword()));
			account.setAccountNonExpired(true);
			account.setAccountNonLocked(true);
			account.setCredentialsNonExpired(true);
			account.setEnabled(true);
			ad.insertUser(account);
			ad.insertUserAutority(account.getUsername(), role);
			return account;
		} else {
			return null;
		}

	}
}
