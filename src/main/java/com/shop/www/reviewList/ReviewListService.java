package com.shop.www.reviewList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ReviewListService {
	
	/* 상세검색 상품 seq 매핑 */
	public HashMap<String, Object> AdvencedSearchproduct(HashMap<String,Object> map,List<Map<String,Object>> seqList,String keyName) {
		if(!ObjectUtils.isEmpty(seqList)){
			for(int i=0;i<seqList.size();i++) {
				map.put(keyName+i, seqList.get(i).get("SEQ").toString());
			}
		}else {
			map.put(keyName,0);
		}
		return map;
	}
	
}
