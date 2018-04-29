package com.adam.rec.user_news;

import com.adam.rec.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author adam
 * 创建于 2018-04-28 20:47.
 */
@Service
public class UserNewsServiceJdbc extends UserNewsService {

    private JdbcUtil jdbcUtil;

    @Autowired
    public UserNewsServiceJdbc(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public Boolean writeEvaluation(Evaluation evaluation) {
        int tEvaluation;
        if (evaluation.getLike()) {
            tEvaluation = 1;
        } else if(evaluation.getDislike()) {
            tEvaluation = -1;
        } else {
            tEvaluation = 0;
        }
        String sql = "INSERT INTO REC_USER_NEWS VALUES(TO_NUMBER(USERNEWSID.NEXTVAL),"
                +evaluation.getNewsId()+","
                +evaluation.getUserId()+","
                +tEvaluation + ","
                +evaluation.getScore() +")";
        int result = jdbcUtil.executeUpdate(sql);
        return result >= 0||result == Statement.SUCCESS_NO_INFO;
    }

    @Override
    public Boolean updateEvaluation(Evaluation evaluation) {
        int tEvaluation;
        if (evaluation.getLike()) {
            tEvaluation = 1;
        } else if(evaluation.getDislike()) {
            tEvaluation = -1;
        } else {
            tEvaluation = 0;
        }
        String sql = "UPDATE REC_USER_NEWS SET evaluation="+tEvaluation
                +",score="+evaluation.getScore()
                +" WHERE news_id=" + evaluation.getNewsId()
                +" AND user_id=" + evaluation.getUserId();
        int result = jdbcUtil.executeUpdate(sql);
        return result >= 0||result == Statement.SUCCESS_NO_INFO;
    }

    @Override
    public Boolean writeOrUpdate(Evaluation evaluation) {
        String sql = "SELECT * FROM REC_USER_NEWS WHERE NEWS_ID="+evaluation.getNewsId()
                +" AND USER_ID="+evaluation.getUserId();
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            return !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getPrevScore(int user_id, int news_id) {
        double score = -10.0;
        String sql = "SELECT score FROM REC_USER_NEWS WHERE user_id=" + user_id + " AND news_id=" + news_id;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            if(resultSet.next()) {
                score = resultSet.getDouble("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    @Override
    public int getPrevEvaluation(int user_id, int news_id) {
        int prevEvaluation = -2;
        String sql = "SELECT evaluation FROM REC_USER_NEWS WHERE user_id=" + user_id + " AND news_id=" + news_id;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            if(resultSet.next()) {
                prevEvaluation = resultSet.getInt("evaluation");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prevEvaluation;
    }

    @Override
    public Boolean saveEvaluation(Evaluation evaluation) {
        if(writeOrUpdate(evaluation)) {
            return writeEvaluation(evaluation);
        } else {
            return updateEvaluation(evaluation);
        }
    }
}
