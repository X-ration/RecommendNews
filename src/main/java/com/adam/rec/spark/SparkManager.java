package com.adam.rec.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Adam
 * Created at 2018/4/3 21:09.
 */

public class SparkManager {

    private SparkSession sparkSession;
    private SQLContext sqlContext;

    public static final String NEWS_SOHU_LOCATION = "hdfs://172.17.11.181:9000/data/rec_news/mergeIntermediate/news_sohu.parquet";
    public static final String NEWS_SOHU_TABLE = "news_sohu";
    public static final String NEWS_ALL_LOCATION = "hdfs://172.17.11.181:9000/data/rec_news/mergeIntermediate/news_merged_ultimate.parquet";
    public static final String NEWS_ALL_TABLE = "news_all";
    public static final String NEWS_ORIGINAL_LOCATION = "hdfs://172.17.11.181:9000/data/rec_news/build/original/news_original.parquet";
    public static final String NEWS_ORIGINAL_TABLE = "news_original";

    public SparkManager(SparkSession sparkSession, SQLContext sqlContext) {
        this.sparkSession = sparkSession;
        this.sqlContext = sqlContext;
    }

    public void initTable(String hdfsLocation, String tableName) {
        Dataset<Row> dataset = sparkSession
                .read()
                .load(hdfsLocation);
        dataset.createOrReplaceTempView(tableName);
    }

    public Dataset<Row> executeQuery(String sql) {
        return sparkSession.sql(sql);
    }

    public Dataset<Row> createEmptyNewsDataframe() {
        StructType schema = new StructType(
                new StructField[]{
                        DataTypes.createStructField("news_id",DataTypes.IntegerType,true),
                        DataTypes.createStructField("contenttitle",DataTypes.StringType,true),
                        DataTypes.createStructField("content",DataTypes.createArrayType(DataTypes.StringType,true),true),
                        DataTypes.createStructField("url",DataTypes.StringType,true),
                        DataTypes.createStructField("publish_time",DataTypes.StringType,true),
                        DataTypes.createStructField("category",DataTypes.StringType,true),
                        DataTypes.createStructField("likes",DataTypes.IntegerType,true),
                        DataTypes.createStructField("dislikes",DataTypes.IntegerType,true),
                        DataTypes.createStructField("score",DataTypes.DoubleType,true)
                }
        );
        return sparkSession.createDataFrame(sparkSession.emptyDataFrame().rdd(),schema);
    }

}
