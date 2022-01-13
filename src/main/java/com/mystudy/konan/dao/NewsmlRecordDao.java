package com.mystudy.konan.dao;

import com.mystudy.konan.vo.NewsmlRecordVO;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

@Repository
public class NewsmlRecordDao {

    protected static final String nameSpace = "com.mystudy.konan.dao.NewsmlRecordDao.";

    @Autowired
    private SqlSession sqlSession;

    public int insertKrNews(NewsmlRecordVO param){
        return sqlSession.insert(nameSpace + "insertKrNews", param);
    }
    
    public List<?> selectRestaurant(String page){
    	NewsmlRecordVO newsRecordVO = new NewsmlRecordVO();
    	//newsRecordVO.setPage(Integer.parseInt(page));
        return sqlSession.selectList(nameSpace + "selectRestaurant", newsRecordVO);
    }
    public HashMap selectRestaurantCount(){
        return sqlSession.selectOne(nameSpace + "selectRestaurantCount");
    }
    
    public int deleteKrNews(NewsmlRecordVO param){
        return sqlSession.delete(nameSpace + "deleteKrNews", param);
    }
    
    public int updateKrNews(NewsmlRecordVO param){
        return sqlSession.update(nameSpace + "updateKrNews", param);
    }
    
    public int updateAndInsertKrNews(NewsmlRecordVO param){
        return sqlSession.update(nameSpace + "updateAndInsertKrNews", param);
    }

}
