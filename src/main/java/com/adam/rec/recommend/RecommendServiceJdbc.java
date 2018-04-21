package com.adam.rec.recommend;

import com.adam.rec.jdbc.JdbcUtil;
import com.adam.rec.news.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adam
 * 创建于 2018-04-21 13:59.
 */
@Service
public class RecommendServiceJdbc extends RecommendService {

    private JdbcUtil jdbcUtil;

    @Autowired
    public RecommendServiceJdbc(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public List<News> getNewsListFiltered(List<String> categories) {
        List<News> newsListFiltered = new ArrayList<>();
//        "SELECT * FROM news WHERE category='afds' or category='fads' or category='fsda'";
        String sql = "SELECT * FROM news WHERE category='" + categories.stream().collect(Collectors.joining("' or category='")) + "'";

        try{
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            while(resultSet.next()) {
                newsListFiltered.add(new News(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5), LocalDateTime.parse(resultSet.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        resultSet.getInt(7),resultSet.getInt(8),resultSet.getDouble(9)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsListFiltered;
    }

}
