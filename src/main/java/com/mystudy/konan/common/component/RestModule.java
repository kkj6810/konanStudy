package com.mystudy.konan.common.component;

import com.konantech.knsf.common.vo.ResultListVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @version 1.1
 * <p>
 * Copyright (C) by KONANTECH All right reserved.
 * @Class Name : RestModule.java
 * @Description : Search,Ksf RestFul API
 * @Modification Information
 * @
 * @ 수정일     			 수정자              				수정내용
 * @ ---------   	---------   			-------------------------------
 * @ 2018.06.08 	코난테크놀로지 기술서비스팀 안호빈  	output json 수정
 */

@Component("restModule")
public class RestModule {

    private static final Logger logger = LoggerFactory.getLogger(RestModule.class);

    @Value("${engine.url}")
    protected String engineUrl;

    @Value("${engine.charsetType}")
    protected String charsetType;

    @Value("${ksf.charsetType}")
    protected String ksfCharsetType;

    @Value("${useWarning}")
    protected boolean useWarning;

    @Value("${ksf.url}")
    protected String ksfUrl;

    @Value("${HTTP_TIMEOUT}")
    protected int timeout;

    /**
     * REST 방식으로 엔진 검색 호출
     *
     * @return ResultListVO
     * @throws Exception
     * @version 1.1
     * @modify 2018.06.07
     * @modifier 안호빈(hobin.ahn)
     */
    @SuppressWarnings("unchecked")
    public boolean restSearch(String restUrl, ResultListVO restVO) {
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(restUrl);
            conn = (HttpURLConnection) url.openConnection();
            //타임아웃시간 검색 엔진 응답속도에 맞게 유연하게 수정할 것
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);

            logger.debug("restSearch url : " + restUrl.toString());

            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), charsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("restSearch ERROR:" + jsonObj.toJSONString());
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return false;
            } else if (responseCode != 200) {
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return false;
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱

            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetType)));

            JSONObject jsonObj = (JSONObject) obj;
            JSONObject resultObj = (JSONObject) jsonObj.get("result");
            JSONArray arr = (JSONArray) resultObj.get("rows");
            int arrCnt = arr.size();

            restVO.setStatus((String) jsonObj.get("status"));
            restVO.setTotal((Long) resultObj.get("total_count"));
            restVO.setRows(arrCnt);

            if (arr != null && arrCnt > 0) {
                HashMap<String, String> map;
                List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

                JSONObject result;
                JSONObject record;
                for (int i = 0; i < arrCnt; i++) {
                    map = new HashMap<String, String>();
                    result = (JSONObject) arr.get(i);
                    record = (JSONObject) result.get("fields");
                    //검색  대상 필드명 추출
                    Iterator<String> fields_iter = record.keySet().iterator();

                    while (fields_iter.hasNext()) {
                        String key = (String) fields_iter.next();
                        String value = record.get(key).toString();
                        //그룹바이 카운트필드명  버전별 상이 할 수 있어 'count'로 통일
                        if ("count(*)".equals(key) || "count()".equals(key)) {
                            key = "count";
                        }
                        //개발라이선스 워닝 메세지 옵션에 따라 삭제
                        if (!useWarning) {
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
            logger.error("ERROR: " + logger.getName() + " restSearch Exception", e);
            return false;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
            }
        }
        return true;
    }


    /**
     * REST 방식으로 엔진 검색 호출(파라미터 전달 방식)
     *
     * @return JSON
     * @throws Exception
     * @version 1.1
     * @modify 2018.06.07
     * @modifier 안호빈(hobin.ahn)
     */
    public String getSearchApi(String param) {
        StringBuffer engineRestUrl = new StringBuffer();
        engineRestUrl.append(engineUrl);
        engineRestUrl.append(param);

        logger.debug("getSearchApi url : " + engineRestUrl.toString());
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(engineRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), charsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                return jsonObj.toJSONString();
            } else if (responseCode != 200) {
                logger.error("getSearchApi ERROR responseCode = " + responseCode);
                return "parameter error";
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetType)));
            JSONObject jsonObj = (JSONObject) obj;
            String returnStr = "";
            if (!useWarning) {
                returnStr = jsonObj.toJSONString().replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", "");
            } else {
                returnStr = jsonObj.toJSONString();
            }
            return returnStr;
            // 파싱 끝
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getSearchApi Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                logger.error("ERROR: " + logger.getName() + " getSearchApi Exception");
            }
        }
    }


    /**
     * KSF 방식으로 인기검색어를 가져온다.(리턴값 2차배열)
     *
     * @return String[][]
     */
    public List<HashMap<String, String>> getPopularKwd(int domainNo, int maxResult) {
        StringBuffer ksfRestUrl = new StringBuffer();
        ksfRestUrl.append(ksfUrl);
        ksfRestUrl.append("rankings");
        ksfRestUrl.append("?domain_no=" + domainNo);
        ksfRestUrl.append("&max_count=" + maxResult);

        logger.debug("getPopularKwd url : " + ksfRestUrl.toString());

        URL url;
        List<HashMap<String, String>> ppkResult = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(ksfRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            //타임아웃시간 ksf에 맞게 유연하게 수정할 것
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), ksfCharsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("getPopularKwd ERROR:" + jsonObj.toJSONString());
                return null;
            } else if (responseCode != 200) {
                logger.error("getPopularKwd ERROR HTTP ResponseCode = :" + responseCode);
                return null;
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), ksfCharsetType)));

            JSONArray jsonArr = (JSONArray) obj;
            JSONArray ppk = null;
            if (jsonArr != null && jsonArr.size() > 0) {
                ppkResult = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> ppkMap = null;

                for (int i = 0; i < jsonArr.size(); i++) {
                    ppk = (JSONArray) jsonArr.get(i);
                    ppkMap = new HashMap<String, String>();

                    ppkMap.put("keyword", (String) ppk.get(0));

                    String tag = "";
                    if (ppk.size() > 1) {
                        tag = (String) ppk.get(1);
                    } else {
                        tag = "new";
                    }
                    String ladderMark = "";
                    String ladderCssClass = "";

                    // 진입, 유지 로직을 적는다.
                    if (tag.equalsIgnoreCase("new")) {
                        ladderMark = "진입";
                        ladderCssClass = "new";
                    } else if (tag.equalsIgnoreCase("0")) {
                        ladderMark = "유지";
                        ladderCssClass = "df";
                    } else {
                        //태그값 데이터 타입 체크 (정수형)
                        try {
                            Integer.parseInt(tag);
                        } catch (NumberFormatException e) {
                            tag = "0";
                        }

                        if (Integer.parseInt(tag) > 0) {
                            ladderMark = "상승";
                            ladderCssClass = "up";
                        } else {
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
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
            }
        }
    }

    /**
     * KSF 방식으로 인기검색어를 가져온다.(리턴값  JSON)
     *
     * @return json
     */
    public String getPopularKwdApi(String param) {
        StringBuffer ksfRestUrl = new StringBuffer();
        ksfRestUrl.append(ksfUrl);
        ksfRestUrl.append("rankings");
        ksfRestUrl.append(param);

        logger.debug("getPopularKwdApi url : " + ksfRestUrl.toString());
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(ksfRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), ksfCharsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                return jsonObj.toJSONString();
            } else if (responseCode != 200) {
                logger.error("getPopularKwdApi ERROR HTTP ResponseCode = :" + responseCode);
                return "parameter error";
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), ksfCharsetType)));
            JSONArray jsonArr = (JSONArray) obj;
            return jsonArr.toJSONString();
            // 파싱 끝
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getPopularKwdApi Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                logger.error("ERROR: " + logger.getName() + " getPopularKwdApi Exception");
            }
        }
    }


    /**
     * KSF 방식으로 자동완성검색어 (리턴값 배열)
     * suggest?target=complete
     *
     * @return String[]
     */
    @SuppressWarnings("unchecked")
    public String[] getAutocomplete(int domainNo, int maxResult, String kwd, String mode) {
        StringBuffer ksfRestUrl = new StringBuffer();
        URL url;
        String[] comleteResult = null;
        HttpURLConnection conn = null;
        try {
            ksfRestUrl.append(ksfUrl);
            ksfRestUrl.append("suggest");
            ksfRestUrl.append("?target=complete");
            ksfRestUrl.append("&term=" + URLEncoder.encode(kwd, ksfCharsetType));
            ksfRestUrl.append("&domain_no=" + domainNo);
            ksfRestUrl.append("&max_count=" + maxResult);
            if (mode != null && !"".equals(mode)) {
                ksfRestUrl.append("&mode=" + mode);
            } else {
                ksfRestUrl.append("&mode=s");
            }
            logger.debug("getAutocomplete url : " + ksfRestUrl.toString());
            url = new URL(ksfRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            //타임아웃시간 ksf에 맞게 유연하게 수정할 것
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), ksfCharsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("getAutocomplete ERROR:" + jsonObj.toJSONString());
                return null;
            } else if (responseCode != 200) {
                logger.error("getAutocomplete ERROR HTTP ResponseCode = :" + responseCode);
                return null;
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), ksfCharsetType)));

            JSONObject jsonData = (JSONObject) obj;
            JSONObject rsObj = new JSONObject();
            rsObj.put("seed", jsonData.get("seed"));
            rsObj.put("suggestions", jsonData.get("suggestions"));

            comleteResult = rsObj.get("suggestions").toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
            return comleteResult;
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getAutocomplete Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
            }
        }
    }


    /**
     * KSF 방식으로 추천어 (리턴값 배열)
     * suggest?target=related
     *
     * @return String[]
     */
    public String[] getKre(int domainNo, int maxResult, String kwd) {
        StringBuffer ksfRestUrl = new StringBuffer();
        URL url;
        String[] relatedResult = null;
        HttpURLConnection conn = null;
        try {
            ksfRestUrl.append(ksfUrl);
            ksfRestUrl.append("suggest");
            ksfRestUrl.append("?target=related");
            ksfRestUrl.append("&term=" + URLEncoder.encode(kwd, ksfCharsetType));
            ksfRestUrl.append("&domain_no=" + domainNo);
            ksfRestUrl.append("&max_count=" + maxResult);
            logger.debug("getKre url : " + ksfRestUrl.toString());
            url = new URL(ksfRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            //타임아웃시간 ksf에 맞게 유연하게 수정할 것
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), ksfCharsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("getKre ERROR:" + jsonObj.toJSONString());
                return null;
            } else if (responseCode != 200) {
                logger.error("getKre ERROR HTTP ResponseCode = :" + responseCode);
                return null;
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), ksfCharsetType)));

            JSONArray jsonArr = (JSONArray) obj;
            relatedResult = jsonArr.toJSONString().replace("[", "").replace("]", "").replace("\"", "").split(",");
            return relatedResult;
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getKre Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
            }
        }
    }

    /**
     * KSF 방식으로 자동완성, 추천어, 오타교정 (리턴값  JSON)
     * suggest?target=complete
     *
     * @return json
     */
    public String getSuggestApi(String param) {
        StringBuffer ksfRestUrl = new StringBuffer();
        URL url;
        HttpURLConnection conn = null;

        try {
            ksfRestUrl.append(ksfUrl);
            ksfRestUrl.append("suggest");
            ksfRestUrl.append(param);
            logger.debug("getSuggestApi url : " + ksfRestUrl.toString());
            url = new URL(ksfRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), ksfCharsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                return jsonObj.toJSONString();
            } else if (responseCode != 200) {
                logger.error("getSuggestApi ERROR HTTP ResponseCode = :" + responseCode);
                return "parameter error";
            }
            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), ksfCharsetType)));
            // JSON 파싱하는 부분
            JSONObject jsonObj = null;
            JSONArray jsonArr = null;

            if (param.contains("target=complete")) {
                jsonObj = (JSONObject) obj;
                return jsonObj.toJSONString();
            } else {
                jsonArr = (JSONArray) obj;
                return jsonArr.toJSONString();
            }
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getSuggestApi Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                logger.error("ERROR: " + logger.getName() + " getSuggestApi Exception");
            }
        }
    }

    /**
     * KSF 방식으로 금칙어를 가져온다.(리턴값  JSON)
     *
     * @return json
     */
    public String getCensoredApi(String param) {
        StringBuffer ksfRestUrl = new StringBuffer();
        ksfRestUrl.append(ksfUrl);
        ksfRestUrl.append("censored");
        ksfRestUrl.append(param);

        logger.debug("getCensoredApi url : " + ksfRestUrl.toString());
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(ksfRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), ksfCharsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("restSearch ERROR:" + jsonObj.toJSONString());
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return jsonObj.toJSONString();
            } else if (responseCode != 200) {
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return "parameter error";
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), ksfCharsetType)));
            JSONArray jsonArr = (JSONArray) obj;
            return jsonArr.toJSONString();
            // 파싱 끝
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getCensoredApi Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                logger.error("ERROR: " + logger.getName() + " getCensoredApi Exception");
            }
        }
    }

    /**
     * KA 주요 이슈어
     * /ksm/tma/issueword?from=news.seoul&issuefd=issue_kwd&where=created_ymd>%3D20161001+and+created_ymd<%3d20161031
     *
     * @return json
     */
    public String getIssuewordApi(String param) {
        StringBuffer kaRestUrl = new StringBuffer();
        URL url;
        HttpURLConnection conn = null;

        try {
            kaRestUrl.append(engineUrl);
            kaRestUrl.append("/ksm/tma/issueword");
            kaRestUrl.append(param);
            logger.debug("getIssuewordApi url : " + kaRestUrl.toString());
            url = new URL(kaRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), charsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("restSearch ERROR:" + jsonObj.toJSONString());
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return jsonObj.toJSONString();
            } else if (responseCode != 200) {
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return "parameter error";
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetType)));
            JSONObject jsonObj = (JSONObject) obj;

            return jsonObj.toJSONString();
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getIssuewordApi Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                logger.error("ERROR: " + logger.getName() + " getIssuewordApi Exception");
            }
        }
    }

    /**
     * KA 주요 연관어
     * /ksm/tma/relatedword?from=news.seoul&issuefd=issue_kwd&issuewd=서울
     *
     * @return json
     */
    public String getRelatedwordApi(String param) {
        StringBuffer kaRestUrl = new StringBuffer();
        URL url;
        HttpURLConnection conn = null;

        try {
            kaRestUrl.append(engineUrl);
            kaRestUrl.append("/ksm/tma/relatedword");
            kaRestUrl.append(param);
            logger.debug("getRelatedwordApi url : " + kaRestUrl.toString());
            url = new URL(kaRestUrl.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();

            if (responseCode == 400) {
                Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getErrorStream(), charsetType)));
                JSONObject jsonObj = (JSONObject) obj;
                logger.error("restSearch ERROR:" + jsonObj.toJSONString());
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return jsonObj.toJSONString();
            } else if (responseCode != 200) {
                logger.error("restSearch ERROR responseCode = " + responseCode);
                return "parameter error";
            }

            // JSON 파싱하는 부분
            //conn.getInputStream() 을 직접 파싱
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetType)));
            JSONObject jsonObj = (JSONObject) obj;
            return jsonObj.toJSONString();
        } catch (Exception e) {
            logger.error("ERROR: " + logger.getName() + " getRelatedwordApi Exception");
            return null;
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
                logger.error("ERROR: " + logger.getName() + " getRelatedwordApi Exception");
            }
        }
    }

}