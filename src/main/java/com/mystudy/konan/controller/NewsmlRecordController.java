package com.mystudy.konan.controller;

import com.mystudy.konan.service.NewsmlRecordService;
import com.mystudy.konan.vo.NewsmlRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewsmlRecordController {

    @Autowired
    NewsmlRecordService newsSvc;
    // insert requestmapping
    @RequestMapping(value = "/insert")
    public String insertDB() {
        NewsmlRecordVO newsRecordVO = new NewsmlRecordVO();
        String id = "6969696969";
        String title = "누구세요11515151515151";
        String linkPage = "dddddddddddd979797979";
        String content = "네ㅐ영이무니다.75757575757";
        newsRecordVO.setNewsItemId(id);
        newsRecordVO.setTitle(title);
        newsRecordVO.setLinkPage(linkPage);
        newsRecordVO.setContent(content);

        newsSvc.insertKrNews(newsRecordVO);
        return "insert";
    }

    ;
}
