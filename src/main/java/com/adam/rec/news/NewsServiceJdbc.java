package com.adam.rec.news;

import com.adam.rec.jdbc.JdbcUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        //jdbcUtil.initAll();
    }

    @Override
    List<News> getNewsListByIdRange(int startIndex, int endIndex){
        List<News> newsList = null;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery("SELECT * FROM news WHERE news_id>=" + startIndex + " AND news_id<" + endIndex);
            newsList = new ArrayList<>();
            while(resultSet.next()) {
                newsList.add(new News(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5), LocalDateTime.parse(resultSet.getString(6),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        resultSet.getInt(7),resultSet.getInt(8),resultSet.getDouble(9)
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("查询为空");
        }
        return newsList;
    }

    @Override
    List<News> getNewsListWindow() {
        List<News> result = getNewsListByIdRange(startIndex, startIndex + windowInterval);
        startIndex = startIndex + windowInterval;
        return result;
    }

    @Override
    List<News> getNewsListPage(int page) {
        return getNewsListByIdRange(10*page-9,10*page+1);
    }

    @Override
    Boolean writeNewsList(List<News> newsList) throws Exception {
        PreparedStatement preparedStatement = jdbcUtil.createPreparedStatement("INSERT INTO news values(?,?,?,?,?,?,?,?,?)");
        for(News news:newsList) {
            preparedStatement.setInt(1,news.getId());
            preparedStatement.setString(2,news.getTitle());
            preparedStatement.setString(3,news.getContent());
            preparedStatement.setString(4,news.getUrl());
            preparedStatement.setString(5,news.getCategory());
            preparedStatement.setString(6,news.getPublish_time());
            preparedStatement.setInt(7,news.getLikes());
            preparedStatement.setInt(8,news.getDislikes());
            preparedStatement.setDouble(9,news.getScore());
            jdbcUtil.batchOperationsAddPrepared();
        }
        int[] result = jdbcUtil.batchOperationsExecutePrepared();
        return ArrayUtils.contains(result,Statement.EXECUTE_FAILED);
    }

    @Override
    List<News> getNewsListByCategoriesAndAmount(List<String> categories, int amountEachCategory) {
        return null;
    }

    @Override
    News getNewsById(int newsId) {
        News news = null;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery("SELECT * FROM news WHERE news_id=" + newsId);
            if (resultSet.next()) {
                news = new News(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5), LocalDateTime.parse(resultSet.getString(6),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        resultSet.getInt(7),resultSet.getInt(8),resultSet.getDouble(9)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("查询为空");
        }
        return news;
    }
}
