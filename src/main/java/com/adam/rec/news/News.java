package com.adam.rec.news;

import java.time.LocalDateTime;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻POJO。
 */

public class News {

    private int id;
    private String title;
    private String content;
    private String url;
    private String category;
    private LocalDateTime publish_time;
    private int likes;
    private int dislikes;
    private double score;

    public News(int id, String title, String content, String url, String category, LocalDateTime publish_time, int likes, int dislikes, double score) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.url = url;
        this.category = category;
        this.publish_time = publish_time;
        this.likes = likes;
        this.dislikes = dislikes;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(LocalDateTime publish_time) {
        this.publish_time = publish_time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
