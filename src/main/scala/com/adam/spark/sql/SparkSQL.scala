package com.adam.spark.sql

import com.adam.spark.dataFrameManager.DataFrameManager
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

/**
  * @author Adam
  * Created by Adam on 2018/4/3 16:00.
  * 使用Spark SQL读取Parquet文件并执行查询。
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
//    val df_sohu = sparkSession
//      .read
//      .load("hdfs://172.17.11.180:9000/data/rec_news/former/news_sohu.parquet")
//      .na.drop()
//      .drop("docno")
//    val df_ten = sparkSession
//      .read
//      .load("hdfs://172.17.11.180:9000/data/rec_news/news_ten.parquet")
//      .na.drop()
//      .drop("docno")

//    df_sohu.show()
//    df_ten.show()

//    val df_sohu2 = df_sohu
//      .withColumn("news_id",DataFrameManager.withId())
//      .withColumn("publish_time",DataFrameManager.withDateTime())
//      .withColumn("category",DataFrameManager.withCategory(df_sohu("url")))
//      .withColumn("likes",DataFrameManager.withDefaultCount(0)())
//      .withColumn("dislikes",DataFrameManager.withDefaultCount(0)())
//      .withColumn("score",DataFrameManager.withDefaultScore(2.5)())
//      .persist()
//    df_sohu2.registerTempTable("news")
//    val selected = sparkSession.sql("SELECT * FROM news")
//    df_sohu2.show(1010)
//    selected.show(1010)
    //df_sohu2.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/news/news_sohu.parquet")

//    df_sohu.registerTempTable("sohu")
//    val df = sparkSession.sql("SELECT url FROM sohu ").rdd.map(row => row.getString(0).split('/')(2))
//    df.distinct().foreach(println)

//    val df_sohu = sparkSession
//          .read
//          .load("hdfs://172.17.11.180:9000/data/rec_news/original/news/news_sohu.parquet")

    val df_sohu = sparkSession
      .read
      .load("hdfs://172.17.11.180:9000/data/rec_news/news/news_sohu.parquet")
//      .persist(StorageLevel.MEMORY_ONLY)
    df_sohu.createOrReplaceTempView("news_sohu")
    val timeEnd1 = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd1-timeStart)/1000.0+"s.")
    val query = sparkSession.sql("SELECT * FROM news_sohu ORDER BY publish_time DESC LIMIT 1000")
    query.show(1010)

    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")

  }

}
