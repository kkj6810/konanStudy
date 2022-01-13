package com.mystudy.konan.service;

import java.util.List;

import com.mystudy.konan.vo.NewsmlRecordVO;

public interface NewsmlRecordService {

    int insertKrNews(NewsmlRecordVO param);
    
    List<?> selectKrNews();
    
    int deleteKrNews(NewsmlRecordVO param);
    
    int updateKrNews(NewsmlRecordVO param);
    
    int updateAndInsertKrNews(NewsmlRecordVO param);
}
