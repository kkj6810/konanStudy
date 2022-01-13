package com.mystudy.konan.common.component;

import com.konantech.crx.CrxClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * AddOn API (검색엔진에서 직접 사용하는 모듈 인기검색어,자동완성,오타교정,추천어  ks5에서는 제거될 예정임)
 *
 * @author Jinhoo.Jang
 * @version 1.0
 * Copyright ⓒ Konan Technology. All Right Reserved
 * ==================================================
 * @since 2013.08.09
 */
@Component
public class AddOnModule {
    private static final Logger logger = LoggerFactory.getLogger(AddOnModule.class);
    private final String id = "konan";
    private final String pw = "konan";

    // 공통
    @Value("${addon.addr}")
    private String addr;
    @Value("${addon.etc}")
    private String etc;
    @Value("${addon.language}")
    private int language;
    @Value("${addon.charset}")
    private int charset;

    /**
     * 인기검색어 메소드 (getPopularKwd).
     *
     * @param maxResultCount 최대 결과 수
     * @param domainNo       모듈 도메인 번호
     * @return 결과값(String[][])
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public String[][] getPopularKwd(int maxResultCount, int domainNo)
            throws IOException, Exception, KonanException {
        String requestName = "POPULAR_KEYWORD";
        String requestFamily = "PPK";

        String[][] arrResult = null;
        int keywordCount = 0;

        CrxClient crx = new CrxClient();          //CrxClient 객체 생성
        long hd = crx.connect(addr, id, pw, etc); //핸들 생성

        try {
            if (hd < 0) throw new Exception(crx.getErrorMessage(hd));

            crx.clearRequest(hd);

            if (charset == 4) {
                crx.setCharacterEncoding(hd, "utf8");
            } else {
                crx.setCharacterEncoding(hd, "euc-kr");
            }

            crx.putRequestName(hd, requestName);   //자동분류기 request 이름
            crx.putRequestFamily(hd, requestFamily); //자동분류기 request family
            crx.putRequestParamInt(hd, "DOMAIN_NO", "INT32", domainNo);
            crx.putRequestParamInt(hd, "MAX_RESULT_COUNT", "INT32", maxResultCount);
            crx.putRequestParamInt(hd, "LANGUAGE", "INT32", language);
            crx.putRequestParamInt(hd, "CHARSET", "INT32", charset);

            //핸들, 파라미터명, 파라미터타입, 파라미터값
            if (crx.submitRequest(hd) < 0) {  //request  생성
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }
            if (crx.receiveResponse(hd) < 0) {  //response 얻음
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }

            //comment
            for (int i = 0; i < crx.getResponseParamCount(hd); i++) {
                String crxName = crx.getResponseParamName(hd, i);

                if (crxName.equalsIgnoreCase("KEYWORD_COUNT")) { //KEYWORD_COUNT
                    keywordCount = crx.getResponseParamValueInt(hd, i);

                    if (keywordCount > 0) {
                        arrResult = new String[2][keywordCount];
                    }
                } else if (crxName.equalsIgnoreCase("KEYWORD")) { //KEYWORD
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[0], 0, keywordCount);
                } else if (crxName.equalsIgnoreCase("TAG")) { //TAG
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[1], 0, keywordCount);
                }
            }
        } catch (Exception e) {

        } finally {
            if (crx != null) crx.disconnect(hd);    //핸들 제거
        }


        return arrResult;
    }

    /**
     * 인기검색어 메소드 (getPopularKwd).
     *
     * @param maxResultCount 최대 결과 수
     * @param domainNo       모듈 도메인 번호
     * @param add            인기검색어 주소(ip:port)
     * @param language       (1: Euckr, 4: Utf8)
     * @return 결과값(String[])
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public String[][] getPopularKwd(int maxResultCount, int domainNo, String addr, int language)
            throws IOException, Exception, KonanException {

        String requestName = "POPULAR_KEYWORD";
        String requestFamily = "PPK";

        String[][] arrResult = null;
        int keywordCount = 0;
        CrxClient crx = new CrxClient();          //CrxClient 객체 생성
        long hd = crx.connect(addr, id, pw, etc); //핸들 생성

        try {
            if (hd < 0) throw new Exception(crx.getErrorMessage(hd));

            crx.clearRequest(hd);

            if (charset == 4) {
                crx.setCharacterEncoding(hd, "utf8");
            } else {
                crx.setCharacterEncoding(hd, "euc-kr");
            }

            crx.putRequestName(hd, requestName);   //자동분류기 request 이름
            crx.putRequestFamily(hd, requestFamily); //자동분류기 request family
            crx.putRequestParamInt(hd, "DOMAIN_NO", "INT32", domainNo);
            crx.putRequestParamInt(hd, "MAX_RESULT_COUNT", "INT32", maxResultCount);
            crx.putRequestParamInt(hd, "LANGUAGE", "INT32", language);
            crx.putRequestParamInt(hd, "CHARSET", "INT32", charset);

            //핸들, 파라미터명, 파라미터타입, 파라미터값
            if (crx.submitRequest(hd) < 0) {  //request  생성
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }
            if (crx.receiveResponse(hd) < 0) {  //response 얻음
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }

            //comment
            for (int i = 0; i < crx.getResponseParamCount(hd); i++) {
                String crxName = crx.getResponseParamName(hd, i);

                if (crxName.equalsIgnoreCase("KEYWORD_COUNT")) { //KEYWORD_COUNT
                    keywordCount = crx.getResponseParamValueInt(hd, i);
                    if (keywordCount > 0) {
                        arrResult = new String[2][keywordCount];
                    }
                } else if (crxName.equalsIgnoreCase("KEYWORD")) { //KEYWORD
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[0], 0, keywordCount);
                } else if (crxName.equalsIgnoreCase("TAG")) { //TAG
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[1], 0, keywordCount);
                }
            }
        } catch (Exception e) {

        } finally {
            if (crx != null) crx.disconnect(hd);    //핸들 제거
        }

        return arrResult;
    }

    /**
     * 오타교정 메소드 (getSpellCheck).
     *
     * @param inputWord      키워드
     * @param maxResultCount 최대 결과 수
     * @param language       언어
     * @param charset        문자셋
     * @return 결과값(String[])
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public String[] getSpellCheck(String inputWord, int maxResultCount)
            throws IOException, Exception, KonanException {
        String requestName = "SPELL_CHECK";
        String requestFamily = "SPC";

        String[] arrResult = null;
        int keywordCount = 0;

        CrxClient crx = new CrxClient();          //CrxClient 객체 생성
        long hd = crx.connect(addr, id, pw, etc); //핸들 생성

        try {
            if (hd < 0) throw new Exception(crx.getErrorMessage(hd));

            //request param 설정 -  rc 체크는 별도 수행 안함
            crx.clearRequest(hd);

            if (charset == 4) {
                crx.setCharacterEncoding(hd, "utf8");
            } else {
                crx.setCharacterEncoding(hd, "euc-kr");
            }

            crx.putRequestName(hd, requestName);   //자동분류기 request 이름
            crx.putRequestFamily(hd, requestFamily); //자동분류기 request family
            crx.putRequestParamString(hd, "INPUT_WORD", "CHAR", inputWord);
            crx.putRequestParamInt(hd, "MAX_RESULT_COUNT", "INT32", maxResultCount);
            crx.putRequestParamInt(hd, "LANGUAGE", "INT32", language);
            crx.putRequestParamInt(hd, "CHARSET", "INT32", charset);

            //핸들, 파라미터명, 파라미터타입, 파라미터값
            if (crx.submitRequest(hd) < 0) {  //request  생성
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }
            if (crx.receiveResponse(hd) < 0) {  //response 얻음
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }

            //comment
            for (int i = 0; i < crx.getResponseParamCount(hd); i++) {
                String crxName = crx.getResponseParamName(hd, i);

                if (crxName.equalsIgnoreCase("KEYWORD_COUNT")) { //KEYWORD_COUNT
                    keywordCount = crx.getResponseParamValueInt(hd, i);
                    if (keywordCount > 0) arrResult = new String[keywordCount];
                } else if (crxName.equalsIgnoreCase("KEYWORD")) { //KEYWORD
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult, 0, keywordCount);
                }
            }
        } catch (Exception e) {

        } finally {
            if (crx != null) crx.disconnect(hd);    //핸들 제거
        }

        return arrResult;
    }

    /**
     * 추천어 메소드 (getRecommandKwd).
     *
     * @param keyword        키워드
     * @param maxResultCount 최대 결과 수
     * @param language       언어
     * @param charset        문자셋
     * @param domainNo       모듈 도메인 번호
     * @return 결과값(String[])
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public String[] getRecommandKwd(String inputWord, int maxResultCount, int domainNo)
            throws IOException, Exception, KonanException {
        String requestName = "RECOMMEND_KEYWORD";
        String requestFamily = "KRE";

        int keywordCount = 0;

        String[] arrResult = null;

        if (inputWord == null) return null;
        if (inputWord.equalsIgnoreCase("")) return null;

        CrxClient crx = new CrxClient();          //CrxClient 객체 생성
        long hd = crx.connect(addr, id, pw, etc); //핸들 생성

        try {
            if (hd < 0) throw new Exception(crx.getErrorMessage(hd));

            //request param 설정 -  rc 체크는 별도 수행 안함
            crx.clearRequest(hd);

            if (charset == 4) {
                crx.setCharacterEncoding(hd, "utf8");
            } else {
                crx.setCharacterEncoding(hd, "euc-kr");
            }

            crx.putRequestName(hd, requestName);   //자동분류기 request 이름
            crx.putRequestFamily(hd, requestFamily); //자동분류기 request family
            crx.putRequestParamInt(hd, "DOMAIN_NO", "INT32", domainNo);
            crx.putRequestParamInt(hd, "MAX_RESULT_COUNT", "INT32", maxResultCount);
            crx.putRequestParamString(hd, "INPUT_WORD", "CHAR", inputWord);
            crx.putRequestParamInt(hd, "LANGUAGE", "INT32", language);
            crx.putRequestParamInt(hd, "CHARSET", "INT32", charset);

            //핸들, 파라미터명, 파라미터타입, 파라미터값
            if (crx.submitRequest(hd) < 0) {  //request  생성
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }
            if (crx.receiveResponse(hd) < 0) {  //response 얻음
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }

            //comment
            for (int i = 0; i < crx.getResponseParamCount(hd); i++) {
                String crxName = crx.getResponseParamName(hd, i);

                if (crxName.equalsIgnoreCase("KEYWORD_COUNT")) { //KEYWORD_COUNT
                    keywordCount = crx.getResponseParamValueInt(hd, i);
                    if (keywordCount > 0) arrResult = new String[keywordCount];
                } else if (crxName.equalsIgnoreCase("KEYWORD")) { //KEYWORD
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult, 0, keywordCount);
                }
            }
        } catch (Exception e) {

        } finally {
            if (crx != null) crx.disconnect(hd);    //핸들 제거
        }

        return arrResult;
    }

    /**
     * 검색어 자동완성 메소드 (getCompleteKwd).
     *
     * @param keyword        키워드
     * @param maxResultCount 최대 결과 수
     * @param flag           결과 형식 플래그 (앞, 뒤 단어 일치 여부)
     * @param language       언어
     * @param charset        문자셋
     * @param domainNo       모듈 도메인 번호
     * @return 결과값(String[])
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public String[][] getCompleteKwd(String seed, int maxResultCount, int flag, int domainNo)
            throws IOException, Exception, KonanException {
        String requestName = "COMPLETE_KEYWORD";
        String requestFamily = "AKC";

        int keywordCount = 0;

        String[][] arrResult = null;

        if (seed == null) return null;
        if (seed.equalsIgnoreCase("")) return null;

        CrxClient crx = new CrxClient();          //CrxClient 객체 생성
        long hd = crx.connect(addr, id, pw, etc); //핸들 생성

        try {
            if (hd < 0) throw new Exception(crx.getErrorMessage(hd));

            //request param 설정 -  rc 체크는 별도 수행 안함
            crx.clearRequest(hd);

            if (charset == 4) {
                crx.setCharacterEncoding(hd, "utf8");
            } else {
                crx.setCharacterEncoding(hd, "euc-kr");
            }

            crx.putRequestName(hd, requestName);   //자동분류기 request 이름
            crx.putRequestFamily(hd, requestFamily); //자동분류기 request family
            crx.putRequestParamInt(hd, "DOMAIN_NO", "INT32", domainNo);
            crx.putRequestParamString(hd, "SEED", "CHAR", seed);
            crx.putRequestParamInt(hd, "FLAG", "INT32", flag);
            crx.putRequestParamInt(hd, "MAX_RESULT_COUNT", "INT32", maxResultCount);
            crx.putRequestParamInt(hd, "LANGUAGE", "INT32", language);
            crx.putRequestParamInt(hd, "CHARSET", "INT32", charset);

            //핸들, 파라미터명, 파라미터타입, 파라미터값
            if (crx.submitRequest(hd) < 0) {  //request  생성
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }
            if (crx.receiveResponse(hd) < 0) {  //response 얻음
                logger.error(crx.getErrorMessage(hd));
                throw new Exception(crx.getErrorMessage(hd));
            }

            //comment
            for (int i = 0; i < crx.getResponseParamCount(hd); i++) {
                String crxName = crx.getResponseParamName(hd, i);
                if (crxName.equalsIgnoreCase("KEYWORD_COUNT")) { //KEYWORD_COUNT
                    keywordCount = crx.getResponseParamValueInt(hd, i);
                    if (keywordCount > 0) arrResult = new String[4][keywordCount];
                } else if (crxName.equalsIgnoreCase("KEYWORD")) { //KEYWORD
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[0], 0, keywordCount);
                } else if (crxName.equalsIgnoreCase("TAG")) { //TAG
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[1], 0, keywordCount);
                } else if (crxName.equalsIgnoreCase("NUM")) { //NUM
                    System.arraycopy(crx.getResponseParamValueStringArray(hd, i), 0, arrResult[2], 0, keywordCount);
                } else if (crxName.equalsIgnoreCase("CONVERTED_KEYWORD")) { //CONVERTED_KEYWORD
                    arrResult[3][0] = crx.getResponseParamValueString(hd, i);
                }
            }
        } catch (Exception e) {

        } finally {
            if (crx != null) crx.disconnect(hd);    //핸들 제거
        }

        return arrResult;
    }
}