package com.mystudy.konan.common.component;

import com.konantech.knsf.common.vo.ParameterVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 검색 파라미터 세팅
 *
 * @author 장진후(Jinhoo.Jang)
 * @team 기술지원팀(Technical Support)
 * @since 2013.06.20
 * @version 1.0
 */
@Component
public class ParameterUtil {

    public ParameterVO setParameter(@RequestParam Map<String, String> map) {
        ParameterVO rtnParam = new ParameterVO();

        // 사이트
        rtnParam.setSiteNm(getValue(map, "site", "site"));

        // 키워드 (기본적으로 콤마는 삭제한다. XSS(크로스사이트스크립팅 체크))
        rtnParam.setKwd(CommonUtil.getCheckXSS(getValue(map, "kwd", "").replaceAll("\\'", "")));

        // 하이라이트 키워드를 세팅한다.
        rtnParam.setHiliteKwd(CommonUtil.extractHiliteKwd(rtnParam.getKwd()));

        // 카테고리
        rtnParam.setCategory(getValue(map, "category", "TOTAL"));

        // 페이지 번호
        rtnParam.setPageNum(getValue(map, "pageNum", 1));

        // 페이지 사이즈
        if("TOTAL".equals(rtnParam.getCategory())) {
            rtnParam.setPageSize(3);
        } else {
            rtnParam.setPageSize(10);
        }
        // 서브카테고리
        rtnParam.setSubCategory(getValue(map, "subCategory", "all"));

        // 검색대상
        rtnParam.setSrchFd(getValue(map, "srchFd", "all"));

        // 정렬코드
        rtnParam.setSort(getValue(map, "sort", "r"));

        // 정렬명
        if("r".equals(rtnParam.getSort())) {
            rtnParam.setSortNm("정확도");
        } else {
            rtnParam.setSortNm("최신순");
        }

        // 상세검색 여부
        rtnParam.setDetailSearch(getValue(map, "detailSearch"));

        // 파일 확장자
        rtnParam.setFileExt(getValue(map, "fileExt", "all"));

        /** 날짜 검색 - 선택*/
        rtnParam.setDate(getValue(map, "date", "all"));

        /** 기간 - 슬라이더*/
        //srchParam.setSliderValue(CommonUtil.null2Str(request.getParameter("sliderValue"), "5"));

        // 시작일
        rtnParam.setStartDate(getValue(map, "startDate", ""));

        // 종료일
        rtnParam.setEndDate(getValue(map, "endDate", ""));

        if("all".equals(rtnParam.getDate())){
            rtnParam.setStartDate("");
            rtnParam.setEndDate("");
        }else if("1".equals(rtnParam.getDate())){
            rtnParam.setStartDate(CommonUtil.getTargetDate(-1));
            rtnParam.setEndDate(CommonUtil.getTargetDate(0));
        }else if("7".equals(rtnParam.getDate())){
            rtnParam.setStartDate(CommonUtil.getTargetDate(-7));
            rtnParam.setEndDate(CommonUtil.getTargetDate(0));
        }else if("30".equals(rtnParam.getDate())){
            rtnParam.setStartDate(CommonUtil.getTargetDate(-30));
            rtnParam.setEndDate(CommonUtil.getTargetDate(0));
        }else if("365".equals(rtnParam.getDate())){
            rtnParam.setStartDate(CommonUtil.getTargetDate(-365));
            rtnParam.setEndDate(CommonUtil.getTargetDate(0));
        }

        // 년도
        rtnParam.setYear(getValue(map, "year", ""));

        // 작성자
        rtnParam.setWriter(getValue(map, "writer", ""));

        // 제외어
        rtnParam.setExclusiveKwd(getValue(map, "notWord", ""));

        // 검색영역
        rtnParam.setFields(getValue(map, "fields", ""));

        // 재검색여부
        rtnParam.setReSrchFlag( "true".equals(getValue(map, "resrch", ""))?true:false );

        return rtnParam;
    }


    /**
     * 문자열 리턴 메소드
     *
     * @param map
     * @param key
     * @param rtnValue
     *
     * @return string
     */
    public String getValue(Map<String, String> map, String key, String rtnValue) {
        if(StringUtils.isEmpty(map.get(key)))
            return rtnValue;

        return map.get(key).toString();
    }

    /**
     * 정수 리턴 메소드
     *
     * @param map
     * @param key
     * @param rtnValue
     *
     * @return integer
     */
    public int getValue(Map<String, String> map, String key, int rtnValue) {
        if(StringUtils.isEmpty(map.get(key)) || !NumberUtils.isNumber(map.get(key)))
            return rtnValue;

        return Integer.parseInt(map.get(key));
    }

    /**
     * boolean 리턴 메소드
     *
     * @param map
     * @param key
     * @param rtnValue
     *
     * @return boolean
     */
    public boolean getValue(Map<String, String> map, String key) {
        if(StringUtils.isEmpty(map.get(key)))
            return false;

        if("true".equals(map.get(key)) || "on".equals(map.get(key)))
            return true;

        return false;
    }
}