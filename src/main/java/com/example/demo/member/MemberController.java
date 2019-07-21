package com.example.demo.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.CommonClass;
import com.shop.common.ReqUtil;

@Controller
public class MemberController extends ReqUtil{
	@Autowired
	MemberDAO md;
	
	@Autowired
	MemberService ms;
	@GetMapping("/sign")
	public String sign() {
		return "sign";
	}
	@GetMapping("/user")
	public String user() {
		
		md.getUser();
		return "sign";
	}
	
	public void common(HttpServletRequest request, Model model) throws Exception {
		Map<String,Object> map = ReqUtil.reqToHashMap(request);
		model.addAttribute("reqMap",map);
	}
	
	@GetMapping("/test")
	public String test(HttpServletRequest request,Model model) throws Exception {
		common(request,model);
		Map<String,Object> map = (Map<String, Object>) model.asMap().get("reqMap");
		CommonClass.mapToString(map,"/test");
		ms.getSelect(model);
		return "sign";
	}
	@ResponseBody
	@PostMapping("/ajax/getNewReview")
	public List<Map<String,Object>> getNewRewview() {
		return ms.getNewReview();
	}
}
