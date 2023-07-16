package com.phocos.forum.model;

public class ArticleDto {
    private Integer articleId;
    private String articleTitle;
    private String articleContent;
    private String articlePostTime;
    private String imageBase64;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticlePostTime() {
        return articlePostTime;
    }

    public void setArticlePostTime(String articlePostTime) {
        this.articlePostTime = articlePostTime;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
