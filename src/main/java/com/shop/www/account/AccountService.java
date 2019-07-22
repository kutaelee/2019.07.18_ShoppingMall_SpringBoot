package com.shop.www.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
	@Autowired
	AccountDAO ad;
	
	public void insertUser(HashMap<String, Object> map) {
		ad.insertUser(map);
	}

}
