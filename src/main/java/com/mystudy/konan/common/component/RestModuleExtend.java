package com.mystudy.konan.common.component;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.konantech.knsf.common.vo.ResultListVO;

/**
 * @Class Name : RestModuleExtend.java (HTTPCLIENT 호출방식)
 * @Description : Search,Ksf RestFul API (확장형)
 * @Modification Information
 * @
 * @  수정일     			 수정자              				수정내용
 * @ ---------   	---------   			-------------------------------
 * @ 2018.06.11 	코난테크놀로지 기술서비스팀 안호빈  	HTTPCLIENT 추가(RESTUtils 참조 (향후  connect pool 설정 추가할 예정)) 
 * @ 2018.06.11 	코난테크놀로지 기술서비스팀 안호빈  	HTTPCLIENT 라이브러리(httpclient-4.4.jar,httpcore-4.4.jar)를 추가해야 하기때문에 
 * 											기존(urlconnection)소스 삭제하지 않음.
 * @version 1.2
 *
 *  Copyright (C) by KONANTECH All right reserved.
 */

@Component("restModuleExtend")
public class RestModuleExtend extends RestModule{
	
	private static final Logger logger = LoggerFactory.getLogger(RestModuleExtend.class);
	
	@Autowired
	private RESTUtil restUtil;
	
	/**
	 * REST 방식으로 엔진 검색 호출 (HTTPCLIENT 호출방식)
	 * @version 1.1
	 * @modify 2018.06.07
	 * @modifier 안호빈(hobin.ahn)
	 * @return ResultListVO
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public boolean restSearch_hc(String restUrl, ResultListVO restVO) {
		
		try {
			String resultJson = restUtil.request(restUrl);
			if(resultJson==null) {
				return false;
			}
			JSONParser parser = new JSONParser();
			// JSON 파싱하는 부분 
			
			Object obj = parser.parse(resultJson);
			
			JSONObject jsonObj = (JSONObject) obj;
			JSONObject resultObj = (JSONObject) jsonObj.get("result");
			JSONArray arr = (JSONArray) resultObj.get("rows");
			int arrCnt = arr.size();
			
			restVO.setStatus((String)jsonObj.get("status"));
			restVO.setTotal((Long)resultObj.get("total_count"));
			restVO.setRows(arrCnt);
			
			if(arr != null && arrCnt > 0) {
				HashMap<String, String> map;
				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>> (); 
						
				JSONObject result;
				JSONObject record;
				for(int i=0; i<arrCnt; i++) {
					map = new HashMap<String, String> ();
					result = (JSONObject) arr.get(i);
					record = (JSONObject) result.get("fields");
					//검색  대상 필드명 추출
					Iterator<String> fields_iter = record.keySet().iterator();
					
					while (fields_iter.hasNext()) {
						String key = (String) fields_iter.next();						
						String value = record.get(key).toString();
						//그룹바이 카운트필드명  버전별 상이 할 수 있어 'count'로 통일
						if("count(*)".equals(key) || "count()".equals(key) ) {
							key = "count";
						}
						//개발라이선스 워닝 메세지 옵션에 따라 삭제
						if(!useWarning) {
							map.put(key, value.replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", ""));
						} else {
							map.put(key, value);
						}
					}
					list.add(map);
					map = null;
				}
				
				restVO.setResult(list);
			}
			// 파싱 끝			
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" restSearch Exception");
			e.printStackTrace();
			return false;
		}finally{
		}		
		return true;
	}

	
	/**
	 * REST 방식으로 엔진 검색 호출(파라미터 전달 방식)(HTTPCLIENT 호출방식)
	 * @version 1.1
	 * @modify 2018.06.07
	 * @modifier 안호빈(hobin.ahn)
	 * @return JSON
	 * @throws Exception 
	 */
	public String getSearchApi_hc(String param) {
		
		try {			
			
			StringBuffer engineRestUrl = new StringBuffer();
			engineRestUrl.append(engineUrl);
			engineRestUrl.append(param);
			
			logger.debug("getSearchApi url : " + engineRestUrl.toString());
			
			String resultJson = restUtil.request(engineRestUrl.toString());
			if(resultJson==null) {
				return "page error!";
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONObject jsonObj = (JSONObject) obj;
			String returnStr="";
			if(!useWarning) {
				returnStr = jsonObj.toJSONString().replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", "");
			} else {
				returnStr = jsonObj.toJSONString();
			}
			return returnStr;
			// 파싱 끝			
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getSearchApi Exception");
			return null;
		}
	}
	
	/**
	 * REST 방식으로 엔진 검색 호출 POST방식 (파라미터 전달 방식)(HTTPCLIENT 호출방식)
	 * @version 1.1
	 * @modify 2018.06.07
	 * @modifier 안호빈(hobin.ahn)
	 * @return JSON
	 * @throws Exception 
	 */
	public String getSearchApi_hc_post(String param) {
		try {			
			
			StringBuffer engineRestUrl = new StringBuffer();
			engineRestUrl.append(engineUrl);
			//engineRestUrl.append(param);
			
			logger.debug("getSearchApi url : " + engineRestUrl.toString());
			
			String resultJson = restUtil.request(engineRestUrl.toString(),param,"POST");
			if(resultJson==null) {
				return "page error!";
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONObject jsonObj = (JSONObject) obj;
			String returnStr="";
			if(!useWarning) {
				returnStr = jsonObj.toJSONString().replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", "");
			} else {
				returnStr = jsonObj.toJSONString();
			}
			return returnStr;
			// 파싱 끝			
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getSearchApi Exception");
			return null;
		}	
	}
	
	
	/**
	 * KSF 방식으로 인기검색어를 가져온다.(리턴값 2차배열)(HTTPCLIENT 호출방식)
	 *  
	 * @return String[][]
	 */
	public List<HashMap<String, String>> getPopularKwd_hc(int domainNo, int maxResult) {
		
		
		try {
			
			StringBuffer ksfRestUrl = new StringBuffer();
			ksfRestUrl.append(ksfUrl);
			ksfRestUrl.append("rankings");
			ksfRestUrl.append("?domain_no=" + domainNo);
			ksfRestUrl.append("&max_count=" + maxResult);				 
			
			logger.debug("getPopularKwd url : " + ksfRestUrl.toString());
			
			List<HashMap<String, String>> ppkResult = null;
			
			String resultJson = restUtil.request(ksfRestUrl.toString());
			if(resultJson==null) {
				return null;
			}
			
			JSONParser parser = new JSONParser();
			// JSON 파싱하는 부분 
			
			Object obj = parser.parse(resultJson);
			
			JSONArray jsonArr = (JSONArray) obj;
			JSONArray ppk = null;
			if(jsonArr != null && jsonArr.size() > 0) {
				ppkResult = new ArrayList<HashMap<String, String>> ();
				HashMap<String, String> ppkMap = null;
				
				for(int i=0; i<jsonArr.size(); i++) {
					ppk = (JSONArray) jsonArr.get(i);
					ppkMap = new HashMap<String, String>();
					
					ppkMap.put("keyword", (String) ppk.get(0));
					
					String tag="";
					if(ppk.size()>1) {
						tag = (String) ppk.get(1);
					}else {
						tag = "new";
					}
					
    	        	String ladderMark="";
    	        	String ladderCssClass="";
    	        	
		        	// 진입, 유지 로직을 적는다.
		        	if(tag.equalsIgnoreCase("new")) {
						ladderMark = "진입";
						ladderCssClass = "new";				
					}
					else if(tag.equalsIgnoreCase("0")) {
						ladderMark = "유지";
						ladderCssClass = "df";
					}
					else {
						//태그값 데이터 타입 체크 (정수형)
					    try {
					        Integer.parseInt(tag);
					    } catch (NumberFormatException e) {
					    	tag = "0";
					    }
						
						if(Integer.parseInt(tag)>0) {
							ladderMark = "상승";
							ladderCssClass = "up";
						}else{
							ladderMark = "하락";
							ladderCssClass = "down";
						}
					}
		        	
		        	ppkMap.put("ladderMark", ladderMark);
		        	ppkMap.put("ladderCssClass", ladderCssClass);
		        	ppkMap.put("tag", tag);
					
					
					ppkResult.add(ppkMap);
					ppkMap = null;
				}
			}
			return ppkResult;
			// 파싱 끝			
		} catch (Exception e) {
			logger.error("getPopularKwd ERROR:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * KSF 방식으로 인기검색어를 가져온다.(리턴값  JSON)(HTTPCLIENT 호출방식)
	 *  
	 * @return json
	 */
	public String getPopularKwdApi_hc(String param) {
		
		
		try {		
			
			StringBuffer ksfRestUrl = new StringBuffer();
			ksfRestUrl.append(ksfUrl);
			ksfRestUrl.append("rankings");
			ksfRestUrl.append(param);
			
			logger.debug("getPopularKwdApi url : " + ksfRestUrl.toString());
			
			String resultJson = restUtil.request(ksfRestUrl.toString());
			if(resultJson==null) {
				return null;
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONArray jsonArr = (JSONArray) obj;
			return jsonArr.toJSONString();
			// 파싱 끝			
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getPopularKwdApi Exception");
			return null;
		}
	}
	
	
	/**
	 * KSF 방식으로 자동완성검색어 (리턴값 배열)(HTTPCLIENT 호출방식)
	 * suggest?target=complete
	 *  
	 * @return String[]
	 */
	@SuppressWarnings("unchecked")
	public String[]  getAutocomplete_hc(int domainNo, int maxResult, String kwd, String mode) {
		
		try {
			
			StringBuffer ksfRestUrl = new StringBuffer();
			String[] comleteResult = null;
			ksfRestUrl.append(ksfUrl);
			ksfRestUrl.append("suggest");
			ksfRestUrl.append("?target=complete");
			ksfRestUrl.append("&term=" + URLEncoder.encode(kwd,ksfCharsetType));
			ksfRestUrl.append("&domain_no=" + domainNo);
			if(mode!=null && !"".equals(mode)) {
				ksfRestUrl.append("&mode=" + mode);
			}else {
				ksfRestUrl.append("&mode=s");
			}
			ksfRestUrl.append("&max_count=" + maxResult);
			logger.debug("getAutocomplete url : " + ksfRestUrl.toString());
			
			String resultJson = restUtil.request(ksfRestUrl.toString());
			if(resultJson==null) {
				return null;
			}
			
			JSONParser parser = new JSONParser();
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			
			JSONObject jsonData  = (JSONObject) obj;
			JSONObject rsObj = new JSONObject();
			rsObj.put("seed", jsonData.get("seed"));
			rsObj.put("suggestions", jsonData.get("suggestions"));
			
			comleteResult = rsObj.get("suggestions").toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
			return comleteResult;
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getAutocomplete Exception");
			return null;
		}
	}
	
	/**
	 * KSF 방식으로 추천어 (리턴값 배열)(HTTPCLIENT 호출방식)
	 * suggest?target=related
	 *  
	 * @return String[]
	 */
	public String[]  getKre_hc(int domainNo, int maxResult, String kwd) {
		StringBuffer ksfRestUrl = new StringBuffer();
		String[] relatedResult = null;
		HttpURLConnection conn = null;
		try {	
			ksfRestUrl.append(ksfUrl);
			ksfRestUrl.append("suggest");
			ksfRestUrl.append("?target=related");
			ksfRestUrl.append("&term=" + URLEncoder.encode(kwd,ksfCharsetType));
			ksfRestUrl.append("&domain_no=" + domainNo);
			ksfRestUrl.append("&max_count=" + maxResult);
			logger.debug("getKre url : " + ksfRestUrl.toString());
			String resultJson = restUtil.request(ksfRestUrl.toString());
			
			JSONParser parser = new JSONParser();
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			
			JSONArray jsonArr = (JSONArray) obj;
			if(jsonArr.size()>0) {
				relatedResult = jsonArr.toJSONString().replace("[", "").replace("]", "").replace("\"", "").split(",");	
			}

			return relatedResult;
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getKre Exception");
			return null;
		}finally{
			try{
				if(conn != null) conn.disconnect();
			}catch(Exception e){}
		}
	}
	
	/**
	 * KSF 방식으로 자동완성, 추천어, 오타교정 (리턴값  JSON)(HTTPCLIENT 호출방식)
	 * suggest?target=complete
	 *  
	 * @return json
	 */
	public String getSuggestApi_hc(String param) {
		
		try {
			
			StringBuffer ksfRestUrl = new StringBuffer();
			ksfRestUrl.append(ksfUrl);
			ksfRestUrl.append("suggest");
			ksfRestUrl.append(param);
			
			logger.debug("getSuggestApi url : " + ksfRestUrl.toString());
			
			String resultJson = restUtil.request(ksfRestUrl.toString());
			
			if(resultJson==null) {
				return "page error!";
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONObject jsonObj=null;
			JSONArray jsonArr=null;
			
			if(param.contains("target=complete")) {
				jsonObj = (JSONObject) obj;
				return jsonObj.toJSONString();
			}else {
				jsonArr = (JSONArray) obj;
				return jsonArr.toJSONString();
			}
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getSuggestApi Exception");
			return null;
		}
	}
	
	/**
	 * KSF 방식으로 금칙어를 가져온다.(리턴값  JSON)(HTTPCLIENT 호출방식)
	 *  
	 * @return json
	 */
	public String getCensoredApi_hc(String param) {
		
		
		try {
			
			StringBuffer ksfRestUrl = new StringBuffer();
			ksfRestUrl.append(ksfUrl);
			ksfRestUrl.append("censored");
			ksfRestUrl.append(param);
			
			logger.debug("getCensoredApi url : " + ksfRestUrl.toString());
			
			String resultJson = restUtil.request(ksfRestUrl.toString());
			
			if(resultJson==null) {
				return "page error!";
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONArray jsonArr = (JSONArray) obj;
			return jsonArr.toJSONString();
			// 파싱 끝			
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getCensoredApi Exception");
			return null;
		}
	}
	
	/**
	 * KA 주요 이슈어(HTTPCLIENT 호출방식)
	 * /ksm/tma/issueword?from=news.seoul&issuefd=issue_kwd&where=created_ymd>%3D20161001+and+created_ymd<%3d20161031
	 *  
	 * @return json
	 */
	public String getIssuewordApi_hc(String param) {
		
		
		try {	
			
			StringBuffer kaRestUrl = new StringBuffer();
			kaRestUrl.append(engineUrl);
			kaRestUrl.append("/ksm/tma/issueword");
			kaRestUrl.append(param);
			logger.debug("getIssuewordApi url : "+kaRestUrl.toString());
			
			String resultJson = restUtil.request(kaRestUrl.toString());
			
			if(resultJson==null) {
				return "page error!";
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONObject jsonObj = (JSONObject) obj;
			
			return jsonObj.toJSONString();
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getIssuewordApi Exception");
			return null;
		}
	}
	
	/**
	 * KA 주요 연관어(HTTPCLIENT 호출방식)
	 * /ksm/tma/relatedword?from=news.seoul&issuefd=issue_kwd&issuewd=서울
	 *  
	 * @return json
	 */
	public String getRelatedwordApi_hc(String param) {
		
		
		try {
			
			StringBuffer kaRestUrl = new StringBuffer();
			kaRestUrl.append(engineUrl);
			kaRestUrl.append("/ksm/tma/relatedword");
			kaRestUrl.append(param);
			logger.debug("getRelatedwordApi url : "+kaRestUrl.toString());
			
			String resultJson = restUtil.request(kaRestUrl.toString());
			
			if(resultJson==null) {
				return "page error!";
			}
			
			JSONParser parser = new JSONParser();
			
			// JSON 파싱하는 부분 
			Object obj = parser.parse(resultJson);
			JSONObject jsonObj = (JSONObject) obj;
			return jsonObj.toJSONString();
		} catch (Exception e) {
			logger.error("ERROR: "+logger.getName() +" getRelatedwordApi Exception");
			return null;
		}
	}

		
}
