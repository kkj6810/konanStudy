package com.mystudy.konan.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;

import com.mystudy.konan.vo.NewsmlRecordVO;

public interface NewsmlRecordService {

    int insertKrNews(NewsmlRecordVO param);
    
    List<?> selectRestaurant(String page);
    HashMap selectRestaurantCount();
    
    int deleteKrNews(NewsmlRecordVO param);
    
    int updateKrNews(NewsmlRecordVO param);
    
    int updateAndInsertKrNews(NewsmlRecordVO param);
}
