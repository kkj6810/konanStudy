package com.mystudy.konan.dao;

import com.mystudy.konan.vo.NewsmlRecordVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NewsmlRecordDao {

    protected static final String nameSpace = "com.mystudy.konan.dao.NewsmlRecordDao.";

    @Autowired
    private SqlSession sqlSession;

    public int insertKrNews(NewsmlRecordVO param){
        return sqlSession.insert(nameSpace + "insertKrNews", param);
    }

}
