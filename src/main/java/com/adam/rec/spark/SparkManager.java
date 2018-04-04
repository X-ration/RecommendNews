package com.adam.rec.spark;

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

    @Autowired
    public SparkManager(SparkSession sparkSession, SQLContext sqlContext) {
        this.sparkSession = sparkSession;
        this.sqlContext = sqlContext;
    }



}
