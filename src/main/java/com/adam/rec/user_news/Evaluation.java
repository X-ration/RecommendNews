package com.adam.rec.user_news;

/**
 * @author adam
 * 创建于 2018-04-10 15:59.
 */
public class Evaluation {

    private Boolean isLike;
    private Boolean isDislike;
    private double score;

    @Override
    public String toString() {
        return "[Evaluation]"+isLike+","+isDislike+","+score;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(String like) {
        isLike = like.contains("yes");
    }

    public Boolean getIsDislike() {
        return isDislike;
    }

    public void setIsDislike(String dislike) {
        isDislike = dislike.contains("yes");
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
