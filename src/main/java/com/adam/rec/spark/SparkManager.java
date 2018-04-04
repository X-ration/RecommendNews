package com.adam.rec.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Adam
 * Created at 2018/4/3 21:09.
 */

@Component
public class SparkManager {

    private SparkSession sparkSession;
    private SQLContext sqlContext;

    public static final String NEWS_SOHU_LOCATION = "hdfs://172.17.11.180:9000/data/rec_news/news/news_sohu.parquet";
    public static final String NEWS_SOHU_TABLE = "news_sohu";

    @Autowired
    public SparkManager(SparkSession sparkSession, SQLContext sqlContext) {
        this.sparkSession = sparkSession;
        this.sqlContext = sqlContext;
    }

    public void initTable(String hdfsLocation, String tableName) {
        Dataset<Row> dataset = sparkSession
                .read()
                .load(hdfsLocation);
        dataset.createOrReplaceTempView("news_sohu");
    }

    public Dataset<Row> executeQuery(String sql) {
        return sparkSession.sql(sql);
    }

}
