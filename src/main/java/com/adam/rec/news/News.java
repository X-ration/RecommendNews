package com.adam.rec.news;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻POJO。
 */

public class News {

//    private int id;
    private String docno;
    private String title;
    private String content;
    private String url;
    private String category;

    public News(String docno, String title, String content, String url) {
        this.docno = docno;
        this.title = title;
        this.content = content;
        this.url = url;
        this.category = NewsCategories.identifyCategory(url);
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
