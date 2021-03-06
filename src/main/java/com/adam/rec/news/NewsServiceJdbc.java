package com.adam.rec.news;

import com.adam.rec.jdbc.JdbcUtil;
import com.adam.rec.user_news.Evaluation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public NewsServiceJdbc(NewsCategories newsCategories, @Qualifier("windowInterval") int windowInterval, JdbcUtil jdbcUtil) {
        super(newsCategories,windowInterval);
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    List<News> getNewsListByIdRange(int startIndex, int endIndex){
        List<News> newsList = null;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery("SELECT * FROM REC_NEWS WHERE news_id>=" + startIndex + " AND news_id<" + endIndex);
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
        return getNewsListByIndexRange(10*page-9,10*page+1);
    }

    @Override
    Boolean writeNewsList(List<News> newsList) throws Exception {
        PreparedStatement preparedStatement = jdbcUtil.createPreparedStatement("INSERT INTO REC_NEWS values(?,?,?,?,?,?,?,?,?)");
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
        List<News> newsList = null;
        try {
            String sql = "SELECT * FROM REC_NEWS WHERE category='"+categories.stream().collect(Collectors.joining("' or '"))+"'";
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
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
    News getNewsById(int newsId) {
        News news = null;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery("SELECT * FROM REC_NEWS WHERE news_id=" + newsId);
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

    @Override
    List<News> getNewsListByIndexRange(int startIndex, int endIndex) {
        List<News> newsList = null;
        try {
            //select news_id,i from (select news_id,rownum i from news where rownum<20) where i>=5;
            String sql =
                    "SELECT news_id,news_title,content,url,category,publish_time,likes,dislikes,score " +
                    "FROM " +
                            "(SELECT news_id,news_title,content,url,category,publish_time,likes,dislikes,score,ROWNUM i " +
                            "FROM REC_NEWS " +
                            "WHERE ROWNUM<"+endIndex+") " +
                    "WHERE i>="+startIndex;
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
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
    public Boolean receiveEvaluation(Evaluation evaluation, double prevPersonalScore,int prevPersonalEvaluation, Boolean hasPrevPresonalScore) {
        int likes = evaluation.getLike()?1:0;
        int dislikes = evaluation.getDislike()?1:0;
        double score = evaluation.getScore();
        int news_id = evaluation.getNewsId();

        String sql = "SELECT likes,dislikes,score FROM REC_NEWS WHERE news_id="+news_id;
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            resultSet.next();
            int prevLikes = resultSet.getInt("likes");
            int prevDislikes = resultSet.getInt("dislikes");
            double prevScore = resultSet.getDouble("score");

            int curLikes,curDislikes;
            double curScore;

            if(hasPrevPresonalScore) {
                curLikes = prevLikes - ((prevPersonalEvaluation == 1)?1:0) + likes;
                curDislikes = prevDislikes - ((prevPersonalEvaluation == -1)?1:0) + dislikes;
                curScore = (prevScore * (prevLikes + prevDislikes) - prevPersonalScore + score) / (curLikes + curDislikes);
            } else {
                curLikes = prevLikes + likes;
                curDislikes = prevDislikes + dislikes;
                curScore = (prevScore * (prevDislikes + prevLikes) + score) / (prevDislikes + prevLikes + 1);
            }

            String updateSql = "UPDATE REC_NEWS SET likes=" + curLikes
                    + ",dislikes=" + curDislikes
                    + ",score=" + curScore
                    + " WHERE news_id=" + news_id;

            int result = jdbcUtil.executeUpdate(updateSql);
            return result >= 0 || result == Statement.SUCCESS_NO_INFO;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
