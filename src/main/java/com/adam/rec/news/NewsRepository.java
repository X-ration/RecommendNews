package com.adam.rec.news;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻仓库。
 */

@Repository
public class NewsRepository {

    private SparkSession sparkSession;
    private final String newsTable = "newsTable";
    private List<News> allNews = null;

    //控制新闻数量
    private static int startIndex = 0;
    private static int intervalNum = 10;

    @Autowired
    public NewsRepository(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    /**
     * 初始化新闻仓库（借助Spark SQL查询）。
     */
    void init() {
        Dataset<Row> dataset = sparkSession
                .read()
                .load("hdfs://172.17.11.180:9000/data/rec_news/news_sohu.parquet")
                .na().drop();
        dataset.registerTempTable(newsTable);
        this.allNews = sparkSession
                .sql("SELECT * FROM " + newsTable)
                .collectAsList()
                .stream()
                .map(row -> new News(row.getString(2),row.getString(1),row.getString(0),row.getString(3)))
                .collect(Collectors.toList());
        dataset.show();
    }

    /**
     * 获取10条新闻数据。
     * @return
     */
    List<News> getTenNews() {
        List<News> result = allNews.subList(startIndex,intervalNum);
        startIndex = startIndex + intervalNum;
        return result;
    }

}
