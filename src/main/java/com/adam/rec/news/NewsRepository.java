package com.adam.rec.news;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻仓库。
 */

@Repository
public class NewsRepository {

    private final String newsTable = "newsTable";
    private List<News> allNews = new ArrayList<>();

    //控制新闻数量
    private static Map<String,Integer> startIndexSession = new HashMap<>();
    private static final int intervalNum = 4;

    /**
     * 初始化新闻仓库（借助Spark SQL查询）。
     */
    void init() {
//        Dataset<Row> dataset = sparkSession
//                .read()
//                .load("hdfs://172.17.11.180:9000/data/rec_news/news_sohu.parquet")
//                .na().drop();
//        Dataset<Row> ids = sqlContext.range(1,dataset.count());
//        Dataset<Row> datasetWithId = dataset.withColumn("id",ids.apply("id"));
//        datasetWithId.registerTempTable(newsTable);
//        datasetWithId.show();
//        System.out.println(dataset.count());
        try(InputStream inputStream = new FileInputStream("./localData/newsrepo");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
            String s;
            while((s = bufferedReader.readLine()) != null) {
                String[] splits = s.split("::");
                System.out.println(Arrays.toString(splits));
                allNews.add(new News(splits[1],splits[2],(splits.length==4)?splits[3]:"",splits[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取10条新闻数据。
     * @return
     */
    List<News> getNews(String currentSession) {
        List<News> result = null;
//        String currentSession = request.getSession().getId();
        if(startIndexSession.get(currentSession) == null) {
            startIndexSession.put(currentSession,0);
        } else if(startIndexSession.get(currentSession) > allNews.size()) {
            System.out.println(currentSession + "没有更多新闻了。");
            return null;
        }
        int currentStartIndex = startIndexSession.get(currentSession);
        try {
            result = allNews.subList(currentStartIndex, currentStartIndex + intervalNum);
            startIndexSession.put(currentSession,currentStartIndex + intervalNum);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(currentSession+"没有更多新闻了");
        }
//        List<News> result = sparkSession
//                .sql("SELECT * FROM " + newsTable)
//                .limit(10)
//                .collectAsList()
//                .stream()
//                .map(row -> new News(row.getString(2),row.getString(1),row.getString(0),row.getString(3)))
//                .collect(Collectors.toList());
        return result;
    }

}
