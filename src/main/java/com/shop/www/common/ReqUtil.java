package com.shop.www.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

//import org.apache.log4j.Logger;

@Component
public class ReqUtil {
	//private static Logger log = Logger.getLogger(ReqUtil.class);
	
	/**
	 * HttpServletRequest를 파라미터를 HashMap으로 치환
	 * 
	 * @version
	 * @param Map
	 * @return HashMap
	 * @throws Exception
	 */
	public static HashMap<String, Object> reqToHashMap(HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String[]> requestMap = request.getParameterMap();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iterator<String> it = requestMap.keySet().iterator();
		
		String key = "";
		String[] value = null;

		String encodingType = "UTF-8";

		while (it.hasNext()) {
			key = it.next();
			value = requestMap.get(key);
			if (value.length > 1) {
				if (request.getMethod().equals("GET")) {
					for (String str : value) {
						if (encodingType == null) {
							String encodeStr = new String(str.getBytes("ISO8859-1"), "UTF-8");
							str = encodeStr;
						} else {
							String encodeStr = new String(str.getBytes("ISO8859-1"), encodingType);
							str = encodeStr;
						}
					}
				}
				resultMap.put(key, value);
			} else if (value.length == 1) {
				if (request.getMethod().equals("GET")) {
					if (encodingType == null) {
						String encodeStr = new String(value[0].getBytes("ISO8859-1"), "UTF-8");
						resultMap.put(key, encodeStr);
					} else {
						String encodeStr = new String(value[0].getBytes("ISO8859-1"), encodingType);
						resultMap.put(key, encodeStr);
					}
				} else {
					resultMap.put(key, value[0]);
				}
			} else {
				resultMap.put(key, "");
			}
		}

		HttpSession session = request.getSession(false);
		if(session != null) {
			if (null != session.getAttribute("MEM_ID")) {
				resultMap.put("MEM_ID", session.getAttribute("MEM_ID").toString());
				resultMap.put("MEM_IP", request.getRemoteAddr());
				
			}
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public static String reqToQueryString(HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String[]> requestMap = request.getParameterMap();
		Iterator<String> it = requestMap.keySet().iterator();
		String key = "";
		String[] value = null;

		String encodingType = "UTF-8";
		int idx = 1;
		while (it.hasNext()) {
			key = it.next();
			value = requestMap.get(key);
			if (value.length > 1) {
				if (request.getMethod().equals("GET")) {
					for (String str : value) {
						if (encodingType == null) {
							String encodeStr = new String(str.getBytes("ISO8859-1"), "UTF-8");
							str = encodeStr;
						} else {
							String encodeStr = new String(str.getBytes("ISO8859-1"), encodingType);
							str = encodeStr;
						}
					}
				}
				sb.append(key).append("=").append(value);
			} else if (value.length == 1) {
				if (request.getMethod().equals("GET")) {
					if (encodingType == null) {
						String encodeStr = new String(value[0].getBytes("ISO8859-1"), "UTF-8");
						sb.append(key).append("=").append(encodeStr);
					} else {
						String encodeStr = new String(value[0].getBytes("ISO8859-1"), encodingType);
						sb.append(key).append("=").append(encodeStr);
					}
				} else {
					sb.append(key).append("=").append(value[0]);
				}
			} else {
				sb.append(key).append("=");
			}
			
			if(requestMap.keySet().size() > idx)	sb.append("&");
			
			idx++;
		}
		return sb.toString();
	}
	
	public static String getRequestHeader(HttpServletRequest request) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		java.util.Enumeration names = request.getHeaderNames();
		while (names.hasMoreElements()) {
		    String name = (String)names.nextElement();
		    sb.append(name).append("=").append(request.getHeader(name)).append("\n");
		}
		return sb.toString();
	}
	
	public static String getRequestParameters(HttpServletRequest request) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		java.util.Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
		    String name = (String)names.nextElement();
		    sb.append(name).append("=").append(request.getParameter(name)).append("\n");
		}
		return sb.toString();
	}
	
	public static String getRequestAttributes(HttpServletRequest request) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		java.util.Enumeration names = request.getAttributeNames();
		while (names.hasMoreElements()) {
		    String name = (String)names.nextElement();
		    sb.append(name).append("=").append(request.getAttribute(name)).append("\n");
		}
		return sb.toString();
	}
		
	public static String getRequestSession(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();;
		StringBuffer sb = new StringBuffer();
		
		java.util.Enumeration names = session.getAttributeNames();
		while (names.hasMoreElements()) {
		    String name = (String)names.nextElement();
		    sb.append(name).append("=").append(session.getAttribute(name)).append("\n");
		}
		return sb.toString();
	}
	
	/** 
	 * 접속한 클라이언트의 IP가져오기.
	 * @param request
	 * @return
	 */
	public static String getRemoteIP(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
