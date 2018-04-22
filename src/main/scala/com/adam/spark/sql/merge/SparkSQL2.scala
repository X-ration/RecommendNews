package com.adam.spark.sql.merge

import com.adam.spark.dataFrameManager.DataFrameManager
import org.apache.spark.sql.SparkSession

/**
  * @author Adam
  * Created by Adam on 2018/4/3 16:00.
  * 使用Spark SQL准备进行数据合并 - news_mongo。
  */
object SparkSQL2 {

  def main(args:Array[String]) : Unit = {

    val timeStart = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
      .appName("ApplicationMain")
      .master("local[*]")
      .getOrCreate()
    val sqc = sparkSession.sqlContext
    // 设定运行时配置
    sparkSession.conf.set("spark.driver.memory","6g")

    val df_mongo = sparkSession
      .read
      .json("hdfs://172.17.11.180:9000/data/newsDatas/NewsDatas/*.json")
      .na.drop
      .drop("_id")
      .withColumnRenamed("contents","content")
      .withColumnRenamed("time","publish_time")
      .withColumnRenamed("label","category")
      .withColumnRenamed("title","contenttitle")
      .withColumn("news_id",DataFrameManager.withId())
      .withColumn("likes",DataFrameManager.withDefaultCount(0)())
      .withColumn("dislikes",DataFrameManager.withDefaultCount(0)())
      .withColumn("score",DataFrameManager.withDefaultScore(2.5)())

    df_mongo.show()

    df_mongo.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_mongo.parquet")

    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")

  }

}
