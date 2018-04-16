package com.adam.rec.user_news;

/**
 * @author adam
 * 创建于 2018-04-16 15:17.
 */
public class Evaluation {

    private int userId;
    private int newsId;
    private Boolean isLike;
    private Boolean isDislike;
    private double score;

    @Override
    public String toString() {
        return "[Evaluation]userId="+userId+",newsId="+newsId+",isLike="+isLike+
                ",isDislike="+isDislike+",score="+score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public Boolean getDislike() {
        return isDislike;
    }

    public void setDislike(Boolean dislike) {
        isDislike = dislike;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
