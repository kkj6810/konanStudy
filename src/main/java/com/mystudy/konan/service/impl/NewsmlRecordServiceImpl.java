package com.mystudy.konan.service.impl;

import com.mystudy.konan.dao.NewsmlRecordDao;
import com.mystudy.konan.service.NewsmlRecordService;
import com.mystudy.konan.vo.NewsmlRecordVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsmlRecordServiceImpl implements NewsmlRecordService {

    @Autowired
    NewsmlRecordDao nrDao;

    @Override
    public int insertKrNews(NewsmlRecordVO param) {
        return nrDao.insertKrNews(param);
    }
    @Override
    public List<?> selectKrNews(){
    	return nrDao.selectKrNews();
    }
    @Override
    public int deleteKrNews(NewsmlRecordVO param) {
    	return nrDao.deleteKrNews(param);
    }
    @Override
    public int updateKrNews(NewsmlRecordVO param) {
    	return nrDao.updateKrNews(param);
    }
    @Override
    public int updateAndInsertKrNews(NewsmlRecordVO param) {
    	return nrDao.updateAndInsertKrNews(param);
    }
}
