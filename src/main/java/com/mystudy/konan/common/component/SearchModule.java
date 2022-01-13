package com.mystudy.konan.common.component;

import com.konantech.klbrk.KlbrkModBrkSearch;
import com.konantech.knsf.common.vo.ParameterVO;
import com.konantech.knsf.common.vo.ResultListVO;
import com.konantech.knsf.common.vo.SearchVO;
import com.konantech.konansearch.KSEARCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 검색엔진 API.
 *
 * @author Jinhoo.Jang
 * @version 1.0
 * Copyright ⓒ Konan Technology. All Right Reserved
 * ==================================================
 * @since 2013.08.09
 */
@Component
public class SearchModule {
    private static final Logger logger = LoggerFactory.getLogger(SearchModule.class);

    @Autowired
    private CommonUtil commonUtil;


    @Value("${engine.ip}")
    private String engineIp;
    @Value("${engine.port}")
    private int enginePort;
    @Value("${engine.language}")
    private int language;
    @Value("${engine.charset}")
    private int charset;

    @Value("${broker.ip}")
    private String brokerIp;
    @Value("${broker.port}")
    private int brokerPort;
    @Value("${broker.language}")
    private int brokerLanguage;
    @Value("${broker.charset}")
    private int brokerCharset;
    @Value("${broker.backend_config}")
    private String brokerBackendConfig;

    @Value("${useWarning}")
    private boolean useWarning;

    /**
     * 일반 검색용 모듈
     *
     * @param params
     * @param search
     * @return ResultVO
     * @throws IOException
     * @throws Exception
     */
    public ResultListVO dcSubmitQuery(ParameterVO params, SearchVO search)
            throws IOException, Exception {
        long hc;                            // 검색 핸들
        int rc;                                // SubmitQuery 결과 값
        int total;                            // 검색 결과  건수
        int rows;                            // 페이지 사이즈에 해당하는 레코드 개수
        int cols;                            // 시나리오에 설정된 레코드
        int startNum;                        // 검색 결과 시작 오프셋
        int i;                                // for 문용 인스턴스 변수
        int j;                                // for 문용 인스턴스 변수
        String msg = "";                    // 에러 메시지
        int[] rowIds = null;                // rowid 배열
        int[] scores = null;                // score 배열
        String[] tmpFdata = null;            // 임시 결과 배열
        String[] colNames = null;        // 시나리오에 설정된 필드명
        String status = null;

        KSEARCH crz = new KSEARCH();        // DOCRUZER 객체 생성
        hc = crz.CreateHandle();            // 핸들 생성
        startNum = (params.getPageNum() - 1) * params.getPageSize();    // 검색 결과 시작 오프셋 계산
        ResultListVO rsb = new ResultListVO();
        HashMap<String, String> map;
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        /*
         * -----------------------------------------------------------------
         * 쿼리 인코딩 설정 옵션
         * rc = crz.SetCharacterEncoding(hc, DCConfig.getProperty("SEARCH_CHARACTER_ENCODING") );
         * -----------------------------------------------------------------
         * connection timeout 시간. 초단위(default : 30초)
         * rc = crz.SetOption(hc, crz.OPTION_SOCKET_TIMEOUT_REQUEST, 30);
         * -----------------------------------------------------------------
         * 병렬 쿼리 수행 방식 (defaul : 0, 1일경우 병렬 쿼리 수행)
         * rc = crz.SetOption(hc, DOCRUZER.OPTION_SOCKET_ASYNC_REQUEST, 1);
         * -----------------------------------------------------------------
         */

        if (charset == 4)
            rc = crz.SetCharacterEncoding(hc, "UTF-8");
        else if (charset == 1)
            rc = crz.SetCharacterEncoding(hc, "EUC-KR");

        // 검색 API - 사용자의 질의어를 서버로 보냄
        rc = crz.SubmitQuery(
                hc,
                engineIp,
                enginePort,
                "",
                search.getLogInfo(),
                search.getScenario(),
                search.getQuery(),
                search.getOrderBy(),
                search.getHiliteTxt(),
                startNum,
                params.getPageSize(),
                language,
                charset
        );
        //For Debug --> System.out.println("rc : " + rc);

        if (rc != 0) {
            status = "fail";
            msg = "rc : " + rc + " scn : " + search.getScenario() + "\n query : " + search.getScenario() + "\n orderBy : " + search.getOrderBy();
            throw new Exception(crz.GetErrorMessage(hc) + msg);
        } else {
            status = "OK";
            // SubmitQuery 메소드로 실행한 검색 결과의 총 검색 결과 수
            total = crz.GetResult_TotalCount(hc);
            // SubmitQuery 메소드로 실행한 검색 결과 중 실제로 가져온 검색 결과의 개수
            rows = crz.GetResult_RowSize(hc);
            // SubmitQuery 메소드로 실행한 검색 결과의 필드 개수
            cols = crz.GetResult_ColumnSize(hc);

            colNames = new String[cols];
            rc = crz.GetResult_ColumnName(hc, colNames, cols);  // 시나리오 필드명 설정

            if (search.isFlag()) {
                rowIds = new int[rows];
                scores = new int[rows];

                crz.GetResult_ROWID(hc, rowIds, scores);
            }

            tmpFdata = new String[cols];

            for (i = 0; i < rows; i++) {
                crz.GetResult_Row(hc, tmpFdata, i);
                map = new HashMap<String, String>();

                for (j = 0; j < cols; j++) {
                    if (!useWarning) {
                        map.put(colNames[j], tmpFdata[j].replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", ""));
                    } else {
                        map.put(colNames[j], tmpFdata[j]);
                    }
                    logger.debug(colNames[j] + " : " + tmpFdata[j]);
                }

                list.add(map);
                map = null;
            }

            // ResultListVO에 결과를 담음
            rsb.setRows(rows);
            rsb.setTotal(total);
            rsb.setResult(list);
            rsb.setStatus(status);
        }

        // 핸들 삭제
        crz.DestroyHandle(hc);

        return rsb;
    }


    /**
     * 실제 검색을 처리하는 메소드 (Search-분산볼륨, String List GroupBy).
     *
     * @param ParameterVO 파라미터 VO
     * @param SearchVO    searchvo
     * @param flag        rowid 사용 유무 플래그
     * @return 결과값(int)
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public ResultListVO dcSearch(ParameterVO params, SearchVO search, boolean flag)
            throws IOException, Exception, KonanException {

        long hc;                            // 검색 핸들
        int rc;                                // Search 결과 값
        int total;                            // 검색 결과  건수
        int rows;                            // 페이지 사이즈에 해당하는 레코드 개수
        int cols;                            // 시나리오에 설정된 레코드
        int startNum;                        // 검색 결과 시작 오프셋
        int i;                                // for 문용 인스턴스 변수
        int j;                                // for 문용 인스턴스 변수
        String msg = "";                    // 에러 메시지
        int nGroupCount;                    // 그룹별 건수
        int nGroupKeyCount;                    // 그룹 건수
        int[] groupSize = null;                // 그룹건수 배열
        int[] rowIds = null;                // rowid 배열
        int[] scores = null;                // score 배열
        String[][] groupKeyVal = null;        // group by 대표값 배열
        String[][] fData = null;            // 결과셋
        String[] tmpFdata = null;            // 결과셋(임시)
        String[] colNames = null;        // 시나리오에 설정된 필드명
        String status = null;

        KSEARCH crz = new KSEARCH();        // DOCRUZER 객체 생성
        hc = crz.CreateHandle();            // 핸들 생성
        startNum = (params.getPageNum() - 1) * params.getPageSize();    // 검색 결과 시작 오프셋 계산
        ResultListVO rsb = new ResultListVO();
        HashMap<String, String> map;
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        /*
         * -----------------------------------------------------------------
         * 쿼리 인코딩 설정 옵션
         * rc = crz.SetCharacterEncoding(hc, "EUC_KR");
         * -----------------------------------------------------------------
         * connection timeout 시간. 초단위(default : 30초)
         * rc = crz.SetOption(hc, crz.OPTION_SOCKET_TIMEOUT_REQUEST, 30);
         * -----------------------------------------------------------------
         * 병렬 쿼리 수행 방식 (defaul : 0, 1일경우 병렬 쿼리 수행)
         * rc = crz.SetOption(hc, DOCRUZER.OPTION_SOCKET_ASYNC_REQUEST, 1);
         * -----------------------------------------------------------------
         */
        if (charset == 4)
            rc = crz.SetCharacterEncoding(hc, "UTF-8");
        else if (charset == 1)
            rc = crz.SetCharacterEncoding(hc, "EUC-KR");

        /* 검색 API - 사용자의 질의어를 서버로 보냄 */
        rc = crz.Search(
                hc,
                engineIp + ":" + enginePort,
                search.getScenario(),
                search.getQuery(),
                search.getOrderBy(),
                search.getHiliteTxt(),
                search.getLogInfo(),
                startNum,
                params.getPageSize(),
                language,
                charset
        );

        // For Debug
        // System.out.println("rc : " + rc);

        if (rc != 0) {
            status = "fail";
            msg = "rc : " + rc + " scn : " + search.getScenario() + "\n query : " + search.getScenario() + "\n orderBy : " + search.getOrderBy();
            throw new KonanException(crz.GetErrorMessage(hc) + msg);
        }

        // STRING LIST 타입에 대한 GROUP BY 결과를 받아오기 위한 메소드
        nGroupCount = crz.GetResult_GroupBy_GroupCount(hc);
        // GROUP BY 를 수행한 키(필드)의 개수
        nGroupKeyCount = crz.GetResult_GroupBy_KeyCount(hc);

        groupKeyVal = new String[nGroupCount][nGroupKeyCount];
        groupSize = new int[nGroupCount];
        fData = new String[nGroupCount][2];

        if (flag) {
            // string list group by 일 경우
            total = 0;
            rows = crz.GetResult_RowSize(hc);
            cols = crz.GetResult_ColumnSize(hc);

            rowIds = new int[rows];
            scores = new int[rows];

            // 그룹의 대표값과 그룹에 속한 문서의 개수를 받아오기 위한 메소드
            rc = crz.GetResult_GroupBy(hc, groupKeyVal, groupSize, nGroupCount);

            if (rc != 0) {
                status = "fail";
                msg = "rc : " + rc + " scn : " + search.getScenario() + "\n query : " + search.getScenario() + "\n orderBy : " + search.getOrderBy();
                throw new KonanException(crz.GetErrorMessage(hc) + msg);
            }
            status = "OK";

            for (i = 0; i < nGroupCount; i++) {
                map = new HashMap<String, String>();
                fData[i][0] = groupKeyVal[i][0];
                fData[i][1] = Integer.toString(groupSize[i]);
                total += groupSize[i];
                if (!useWarning) {
                    map.put(fData[i][0], fData[i][1].replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", ""));
                } else {
                    map.put(fData[i][0], fData[i][1]);
                }
                list.add(map);
                map = null;
            }

            // ResultVO에 결과를 담음
            rsb.setRows(rows);
            rsb.setTotal(total);
            rsb.setResult(list);
            rsb.setStatus(status);
        } else {
            // 분산 볼륨 검색일 경우
            status = "OK";
            // SubmitQuery 메소드로 실행한 검색 결과의 총 검색 결과 수
            total = crz.GetResult_TotalCount(hc);
            // SubmitQuery 메소드로 실행한 검색 결과 중 실제로 가져온 검색 결과의 개수
            rows = crz.GetResult_RowSize(hc);
            // SubmitQuery 메소드로 실행한 검색 결과의 필드 개수
            cols = crz.GetResult_ColumnSize(hc);
            colNames = new String[cols];
            rc = crz.GetResult_ColumnName(hc, colNames, cols);  // 시나리오 필드명 설정
            rowIds = new int[rows];
            scores = new int[rows];

            // SubmitQuery 메소드로 실행한 검색 결과 중 nStartOffset 에서부터
            // GetResult_RowSize 메소드로 얻어온 값만큼의 레코드 번호
            crz.GetResult_ROWID(hc, rowIds, scores);

            tmpFdata = new String[cols];
            fData = new String[rows][cols];

            for (i = 0; i < rows; i++) {
                crz.GetResult_Row(hc, tmpFdata, i);
                map = new HashMap<String, String>();

                for (j = 0; j < cols; j++) {
                    map.put(colNames[j], tmpFdata[j]);
                    if (!useWarning) {
                        map.put(colNames[j], tmpFdata[j].replaceAll("\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)", ""));
                    } else {
                        map.put(colNames[j], tmpFdata[j]);
                    }
                    logger.debug(colNames[j] + " : " + tmpFdata[j]);
                }

                list.add(map);
                map = null;
            }

            // ResultListVO에 결과를 담음
            rsb.setRows(rows);
            rsb.setTotal(total);
            rsb.setResult(list);
            rsb.setStatus(status);
        }

        // 핸들 삭제
        crz.DestroyHandle(hc);

        return rsb;
    }


    /**
     * 브로커 검색 모듈
     *
     * @param params
     * @param search
     * @return KlbrkModBrkSearch
     * @throws IOException
     * @throws Exception
     */
    public ResultListVO dcBrokerQuery(ParameterVO params, SearchVO search)
            throws IOException, Exception {
        KlbrkModBrkSearch brk_search = new KlbrkModBrkSearch();
        int rc = 0;
        String msg = "";
        int startNum = (params.getPageNum() - 1) * params.getPageSize();    // 검색 결과 시작 오프셋 계산

        set:
        {
            brk_search.clearRequest();

            if (brokerCharset == 4)
                brk_search.setEncoding("UTF-8");
            else if (brokerCharset == 1)
                brk_search.setEncoding("EUC-KR");

            brk_search.setModuleName("crzbrk.mod");
            brk_search.setFunctionName("search");
            brk_search.setConfigID(brokerBackendConfig);
            brk_search.setScenario(search.getScenario());
            brk_search.setWhereClause(search.getQuery());
            brk_search.setSortClause(search.getOrderBy());
            brk_search.setRankClause("");
            brk_search.setSearchWords(search.getHiliteTxt());
            brk_search.setExtraLog(search.getLogInfo());
            brk_search.setNeedCTScore(0);
            brk_search.setNeedWDScore(0);
            brk_search.setStartOffset(startNum);
            brk_search.setFetchCount(params.getPageSize());
            brk_search.setLanguage(brokerLanguage);
            brk_search.setCharset(brokerCharset);
            brk_search.setUserLog("");

            rc = brk_search.executeQuery(brokerIp, brokerPort);

            if (rc < 0) {
                msg = brk_search.getErrorMessage();
                logger.error("msg : " + msg);
                break set;
            }

            rc = 0;
        }

        if (rc != 0)
            logger.error("ERROR : " + msg);

        ResultListVO rsb = commonUtil.parseResultListData(brk_search);
        return rsb;
    }
}