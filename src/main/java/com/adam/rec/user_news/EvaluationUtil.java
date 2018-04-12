package com.adam.rec.user_news;

/**
 * @author adam
 * 创建于 2018-04-12 21:37.
 */
public class EvaluationUtil {

    public static Boolean isValid(Evaluation evaluation) {
        if(!(evaluation.getIsLike() || evaluation.getIsDislike())) return false;
        if(evaluation.getScore()<=0.0 || evaluation.getScore()>5.0) return false;
        return true;
    }

    public static String whyInvalid(Evaluation evaluation) {
        String errMsg = "";
        if(!(evaluation.getIsLike() || evaluation.getIsDislike())) errMsg+="请至少在喜欢/不喜欢中选择一项！";
        if(evaluation.getScore()<=0.0 || evaluation.getScore()>5.0) errMsg+="请对本条新闻评分！";
        return errMsg;
    }

}
