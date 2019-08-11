package com.shop.www.reviewInsert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.shop.www.account.AccountDTO;
import com.shop.www.account.AccountService;
import com.shop.www.common.ReqUtil;


@RestController
public class ReviewInsertController {
	@Autowired
	ReviewInsertDAO rd;
	@Autowired
	ReviewInsertService rs;
	@Autowired
	AccountService as;
	@Autowired
	ReqUtil requtil;
	
	@PostMapping("/ajax/reviewInsert")
	public boolean reviewInsert(HttpServletRequest req) {
		
		return true;
	}
	
	@PostMapping("/ajax/getProductTitle")
	public List<Map<String,Object>> getProductTitle(HttpServletRequest req) throws Exception{
		return rd.getProductTitle(requtil.reqToHashMap(req));
	}
	
	@PostMapping("/ajax/getProductInfo")
	public Map<String,Object> getProductInfo(HttpServletRequest req) throws Exception{
		return rd.getProductInfo(requtil.reqToHashMap(req));
	}
	@PostMapping("/ajax/productReviewInsert")
	public boolean productReviewInsert(HttpServletRequest req) throws Exception {
		HashMap<String,Object> map=requtil.reqToHashMap(req);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		if(principal !=null && principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails)principal;
			AccountDTO ad=as.findById(userDetails.getUsername());
			map.put("nick",ad.getNick());
			map.put("memberSeq",ad.getSeq());
			map.put("ip", requtil.getRemoteIP(req));
			return rs.productReviewInsert(map);
		}else {
			return false;
		}
	}
	@PostMapping("/ajax/newProductReviewInsert")
	public boolean newProductReviewInsert(MultipartHttpServletRequest req) throws Exception {
		HashMap<String,Object> map=requtil.reqToHashMap(req);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		if(principal !=null && principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails)principal;
			AccountDTO ad=as.findById(userDetails.getUsername());
			map.put("nick",ad.getNick());
			map.put("memberSeq",ad.getSeq());
			map.put("ip", requtil.getRemoteIP(req));
			MultipartFile file = req.getFile("proThumnailImg");
			if(file!=null) {
				String path=req.getServletContext().getRealPath("/img/product");
				System.out.println(path);
				map.put("proThumnailImg",rs.productFileUpload(file,path));
				if(!ObjectUtils.isEmpty(req.getSession().getAttribute("filePath"))) {
					map.put("reviewImg", req.getSession().getAttribute("filePath").toString());
				}
				return rs.newProductReviewInsert(map);
			}else {
				return false;
			}

		}else {
			return false;
		}
	}
	@PostMapping("/ajax/fileUpload")
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp, MultipartHttpServletRequest multiFile)
			throws Exception {
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = multiFile.getFile("upload");
		if (file != null) {
			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
				if (file.getContentType().toLowerCase().startsWith("image/")) {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath("/img/review");
						req.getSession().setAttribute("filePath", uploadPath);
						File uploadFile = new File(uploadPath);
						if (!uploadFile.exists()) {
							uploadFile.mkdirs();
						}
						fileName = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName;
						out = new FileOutputStream(new File(uploadPath));
						out.write(bytes);
						System.out.println(uploadPath);
						printWriter = resp.getWriter();
						resp.setContentType("text/html");
						String fileUrl = req.getContextPath() + "/img/" + fileName;
						// json 데이터로 등록
						// {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
						// 이런 형태로 리턴이 나가야함.
						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url",fileUrl);

						printWriter.println(json);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
						if (printWriter != null) {
							printWriter.close();
						}
					}
				}
			}
		}
		return null;
	}

}
