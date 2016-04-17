package com.kindlepocket.web.pojo;

import java.util.List;

public class PicTextMessage extends BaseMessage {

    private int ArticleCount;

    private List<PicText> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<PicText> getArticles() {
        return Articles;
    }

    public void setArticles(List<PicText> articles) {
        Articles = articles;
    }

}
