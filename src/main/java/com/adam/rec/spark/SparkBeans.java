package com.adam.rec.spark;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * Spark相关Bean配置。
 */

@Component
public class SparkBeans {

    private SparkSession sparkSession = null;

    @Bean
    public SparkSession sparkSession() {
        SparkSession sparkSession = SparkSession.builder()
                .appName("ApplicationMain")
                .master("local[*]")
                .getOrCreate();
        sparkSession.conf().set("spark.driver.memory","10g");
        this.sparkSession = sparkSession;
        return sparkSession;
    }

    @Bean
    public SQLContext sqlContext() {
        if(sparkSession != null) {
            return sparkSession.sqlContext();
        }
        return null;
    }

}
