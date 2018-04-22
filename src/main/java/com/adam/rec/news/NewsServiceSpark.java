package com.adam.rec.news;

import com.adam.rec.spark.SparkManager;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Adam
 * Created at 2018/4/4 18:53.
 */

@Service
public class NewsServiceSpark extends NewsService{

    private SparkManager sparkManager;

    @Autowired
    public NewsServiceSpark(NewsCategories newsCategories,int windowInterval,SparkManager sparkManager){
        super(newsCategories,windowInterval);
        this.sparkManager = sparkManager;
    }

    public List<News> getNewsListByIdRange(int startIndex,int endIndex) {
        if(startIndex >= endIndex){
            return null;
        } else if(startIndex < 1 || endIndex < 1) {
            return null;
        }
        else {
            String sql = "SELECT news_id,contenttitle,content,url,category,publish_time,likes,dislikes,score FROM " + SparkManager.NEWS_SOHU_TABLE
                    + " WHERE news_id>=" + startIndex + " AND news_id<" + endIndex;
            Dataset<Row> dataset = sparkManager.executeQuery(sql);
            List<Row> medium = dataset.collectAsList();
            System.out.println(medium
                    .stream()
                    .map(Row::toString)
                    .collect(Collectors.joining(","))
            );
            List<News> result = medium
            .stream()
            .map(row -> new News(row.getInt(0),row.getString(1),
                        row.getSeq(2).mkString("\r\n"),row.getString(3),
                        row.getString(4),
                        LocalDateTime.parse(row.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        row.getInt(6),row.getInt(7),row.getDouble(8)))
                    .collect(Collectors.toList());
            return result;
        }
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
    Boolean writeNewsList(List<News> newsList) {
        return null;
    }

    @Override
    News getNewsById(int newsId) {
        return null;
    }

}
