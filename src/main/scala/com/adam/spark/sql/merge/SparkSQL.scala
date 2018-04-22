package com.adam.spark.sql.merge

import com.adam.spark.dataFrameManager.DataFrameManager
import org.apache.spark.sql.SparkSession

/**
  * @author Adam
  * Created by Adam on 2018/4/3 16:00.
  * 使用Spark SQL准备进行数据合并 - news_sohu。
  */
object SparkSQL {

  def main(args : Array[String]) : Unit = {

    val timeStart = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
        .appName("ApplicationMain")
      .master("local[*]")
      .getOrCreate()
    val sqc = sparkSession.sqlContext
    // 设定运行时配置
    sparkSession.conf.set("spark.driver.memory","6g")
//    sparkSession.conf.set("spark.local.dir","G:\\sparkTemp")
//
    val df_sohu = sparkSession
      .read
      .load("hdfs://172.17.11.180:9000/data/rec_news/news_sohu.parquet")
      .na.drop()
      .drop("docno")

    df_sohu.show()

    val df_sohu2 = df_sohu
      .withColumn("news_id",DataFrameManager.withId())
      .withColumn("publish_time",DataFrameManager.withDateTime())
      .withColumn("category",DataFrameManager.withCategory(df_sohu("url")))
      .withColumn("comments",DataFrameManager.withDefaultComment())
      .withColumn("likes",DataFrameManager.withDefaultCount(0)())
      .withColumn("dislikes",DataFrameManager.withDefaultCount(0)())
      .withColumn("score",DataFrameManager.withDefaultScore(2.5)())
      .persist()

    df_sohu2.show()

    df_sohu2.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_sohu.parquet")

    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")

  }

}
