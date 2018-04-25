package com.adam.rec.spark;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * Spark相关Bean配置。
 */

@Configuration
public class SparkBeans {

    @Bean
    public SparkSession sparkSession() {
        SparkSession sparkSession = SparkSession.builder()
                .appName("Rec_News_Web")
                .master("spark://172.17.11.181:7077")
                .config("spark.executor.memory","8g")
                .getOrCreate();
//        sparkSession.conf().set("spark.executor.memory","3g");
        return sparkSession;
    }

    @Bean
    @Autowired
    public SQLContext sqlContext(SparkSession sparkSession) {
        return sparkSession.sqlContext();
    }

    @Bean
    @Autowired
    public SparkManager sparkManager(SparkSession sparkSession, SQLContext sqlContext) {
        SparkManager sparkManager = new SparkManager(sparkSession,sqlContext);
        sparkManager.initTable(SparkManager.NEWS_ORIGINAL_LOCATION,SparkManager.NEWS_ORIGINAL_TABLE);
        return sparkManager;
    }

}
