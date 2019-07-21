package com.shop.www.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
	@Autowired
	MainDAO md;
	
	public List<Map<String, Object>> getNewReview() {
		// TODO Auto-generated method stub
		return md.getNewReview();
	}
}
