package com.shop.www.reviewPage;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReviewPageService {
	@Autowired
	ReviewPageDAO rd;
	
	public String likeUpdate(HashMap<String, Object> map) {
		rd.likeUpdate(map);
		return rd.getLikeCnt(map);
	}

}
