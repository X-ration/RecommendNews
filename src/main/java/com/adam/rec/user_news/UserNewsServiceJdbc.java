package com.adam.rec.user_news;

import com.adam.rec.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
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

}
