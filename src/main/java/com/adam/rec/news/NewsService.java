package com.adam.rec.news;

import com.adam.rec.spark.SparkManager;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Adam
 * Created at 2018/4/4 18:53.
 */

@Service
public class NewsService {

    private SparkManager sparkManager;
    private Map<String,String> categories;

    @Autowired
    public NewsService(SparkManager sparkManager, NewsCategories newsCategories){
        this.sparkManager = sparkManager;
        this.categories = newsCategories.getCategories();

        sparkManager.initTable(SparkManager.NEWS_SOHU_LOCATION,SparkManager.NEWS_SOHU_TABLE);
    }

    /**
     * 根据URL地址，识别新闻分类。
     * @param url 新闻原始URL
     * @return 新闻类别
     */
    public String identifyCategory(String url) {
        String s = url.split("/")[2];
        for(String key : categories.keySet()){
            if(url.contains(key)) return categories.get(key);
        }
        return "其他";
    }

    public List<News> getNewsListByIdRange(int startIndex,int endIndex) {
        if(startIndex >= endIndex){
            return null;
        } else if(startIndex < 1) {
            return null;
        }
        else {
            Dataset<Row> dataset = sparkManager.executeQuery("SELECT * FROM " + SparkManager.NEWS_SOHU_TABLE
                    + " WHERE news_id>=" + startIndex + " AND news_id<" + endIndex);
            List<News> result = dataset.collectAsList()
            .stream()
            .map(row -> new News(row.getInt(3),row.getString(1),
                        row.getString(0),row.getString(2),
                        identifyCategory(row.getString(2)),
                        LocalDateTime.parse(row.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        row.getInt(6),row.getInt(7),row.getDouble(8)))
                    .collect(Collectors.toList());
            return result;
        }
    }

    static int startIndex = 0;
    static int interval = 10;

    public List<News> getNewsListTenDynamic() {
        List<News> result = getNewsListByIdRange(startIndex,startIndex+interval);
        startIndex += interval;
        return result;
    }

}
