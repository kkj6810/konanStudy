package com.mystudy.konan.vo;

import java.io.Serializable;

public class NewsmlRecordVO implements Serializable {

    private String newsItemId;

    private String title;

    private String linkPage;

    private String content;

    public String getNewsItemId() {
        return newsItemId;
    }

    public void setNewsItemId(String newsItemId) {
        this.newsItemId = newsItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkPage() {
        return linkPage;
    }

    public void setLinkPage(String linkPage) {
        this.linkPage = linkPage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
