package com.shop.www.reviewInsert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import com.shop.www.common.S3Util;
import com.shop.www.common.UploadFileUtils;

@Transactional
@Service
public class ReviewInsertService {
	@Autowired
	ReviewInsertDAO rd;
	
	public boolean newProductReviewInsert(HashMap<String, Object> map) {
		escapeMap(map);
		if(ObjectUtils.isEmpty(rd.companyDupleCheck(map))) {
			map.put("manCompany", rd.manCompanyInsertAndReturnSeq(map).get("LAST_INSERT_ID()").toString());
		}else {
			map.put("manCompany", rd.companyDupleCheck(map).get("SEQ").toString());
		}
	
		if(ObjectUtils.isEmpty(rd.productDupleCheck(map))) {
			map.put("productSeq", rd.productInsertAndReturnSeq(map).get("LAST_INSERT_ID()").toString());
		}else {
			map.put("productSeq", rd.productDupleCheck(map).get("SEQ").toString());
		}
		rd.reviewInsert(map);
		return true;
	}
	

	public String productFileUpload(MultipartFile file) throws Exception {
		String uploadpath = "product/coverImage";

		ResponseEntity<String> img_path = new ResponseEntity<>(
				UploadFileUtils.uploadFile(uploadpath, file.getOriginalFilename(), file.getBytes()),
				HttpStatus.CREATED);
		String coverImagePath = (String) img_path.getBody();
		S3Util s3=new S3Util();
		System.out.println(s3.getFileURL("gyutae-review", uploadpath+coverImagePath));
		return s3.getFileURL("gyutae-review", uploadpath+coverImagePath);
	}

	public boolean productReviewInsert(HashMap<String, Object> map) {
		escapeMap(map);
		rd.reviewInsert(map);
		return true;
	}

	// 특수문자 치환
	public HashMap<String,Object> escapeMap(HashMap<String,Object> map){
		for(String key:map.keySet()) {
			if(!key.equals("reviewContents")) {
				map.put(key,HtmlUtils.htmlEscape(map.get(key).toString()));
			}else {
				map.put(key,tagEscape(map.get(key).toString()));
			}
		}
		return map;
	}
	
	//스크립트 스타일태그 제거
	//추가 패턴 치환필요
	public String tagEscape(String str) {
		final Pattern SCRIPTS=Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);
		final Pattern STYLES=Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);
		final Pattern QUTERS=Pattern.compile("&#39;",Pattern.DOTALL);
		Matcher m;
		
		m=SCRIPTS.matcher(str);
		str=m.replaceAll("");
		
		m=STYLES.matcher(str);
		str=m.replaceAll("");
		
		return str;
	}
}
