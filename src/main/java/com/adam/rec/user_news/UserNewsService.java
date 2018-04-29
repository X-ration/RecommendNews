package com.adam.rec.user_news;

/**
 * @author adam
 * 创建于 2018-04-28 20:45.
 */
public abstract class UserNewsService {

    public abstract Boolean saveEvaluation(Evaluation evaluation);
    public abstract Boolean writeEvaluation(Evaluation evaluation);
    public abstract Boolean updateEvaluation(Evaluation evaluation);
    public abstract Boolean writeOrUpdate(Evaluation evaluation);
    public abstract double getPrevScore(int user_id,int news_id);
    public abstract int getPrevEvaluation(int user_id,int news_id);

}
