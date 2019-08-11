package com.shop.www.reviewInsert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class ReviewInsertService {
	@Autowired
	ReviewInsertDAO rd;
	
	public boolean newProductReviewInsert(HashMap<String, Object> map) {
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
	
	public String productFileUpload(MultipartFile file,String path) throws IOException {
		UUID randomeUUID = UUID.randomUUID();
		
		File realUploadDir = new File(path);
        if (!realUploadDir.exists()) {
            realUploadDir.mkdirs();
        }
        if (file.getSize()>0) {
        	String organizedfilePath =path+ "/" + randomeUUID + "_" + file.getOriginalFilename();
        	OutputStream outputStream = new FileOutputStream(organizedfilePath);
        	InputStream inputStream = inputStream = file.getInputStream();
            int readByte = 0;
            byte[] buffer = new byte[8192];

            while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
                outputStream.write(buffer, 0, readByte); 
                
            }
        }
        return "../img/product/"+randomeUUID + "_" + file.getOriginalFilename();
	}

	public boolean productReviewInsert(HashMap<String, Object> map) {
		rd.reviewInsert(map);
		return true;
	}
	
}
