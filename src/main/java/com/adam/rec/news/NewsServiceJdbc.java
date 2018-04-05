package com.adam.rec.news;

import com.adam.rec.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Adam
 * Created at 2018/4/5 13:41.
 */

@Service
public class NewsServiceJdbc extends NewsService {

    private JdbcUtil jdbcUtil;
    private static final String tableName = "emp";

    @Autowired
    public NewsServiceJdbc(NewsCategories newsCategories,int windowInterval,JdbcUtil jdbcUtil) {
        super(newsCategories,windowInterval);
        this.jdbcUtil = jdbcUtil;
        jdbcUtil.initAll();
    }

    @Override
    List<News> getNewsListByIdRange(int startIndex, int endIndex){
        try {
            ResultSet resultSet = jdbcUtil.executeQuery("select * from user_tables");
            while(resultSet.next()) {
                String db = resultSet.getString("TABLE_NAME");
                System.out.println("Query: " + db);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("查询为空");
        }
        return null;
    }

    @Override
    List<News> getNewsListWindow() {
        List<News> result = getNewsListByIdRange(startIndex, startIndex + windowInterval);
        startIndex = startIndex + windowInterval;
        return result;
    }
}
