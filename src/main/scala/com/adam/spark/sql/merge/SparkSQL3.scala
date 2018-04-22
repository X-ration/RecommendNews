package com.adam.spark.sql.merge

import com.adam.spark.dataFrameManager.DataFrameManager
import org.apache.spark.sql.SparkSession

/**
  * @author Adam
  * Created by Adam on 2018/4/3 16:00.
  * 使用Spark SQL准备进行数据合并 - 最终合并。
  */
object SparkSQL3 {

  def main(args:Array[String]) : Unit = {

    val timeStart = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
      .appName("ApplicationMain")
      .master("local[*]")
      .getOrCreate()
    val sqc = sparkSession.sqlContext
    // 设定运行时配置
    sparkSession.conf.set("spark.driver.memory","6g")

    //合并
//    val df_sohu = sqc
//      .read
//      .load("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_sohu.parquet")
//      .withColumnRenamed("content","content_o")
//      .withColumnRenamed("comments","comments_o")
//
//    val df_sohu2 = df_sohu
//      .withColumn("content",DataFrameManager.withContentListed(df_sohu("content_o")))
//      .withColumn("comments",DataFrameManager.withEmptyComments())
//      .select("news_id","contenttitle","content","url","publish_time","category","comments","likes","dislikes","score")
//
//    val df_mongo = sqc
//      .read
//      .load("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_mongo.parquet")
//      .select("news_id","contenttitle","content","url","publish_time","category","comments","likes","dislikes","score")
//
//    df_sohu2.printSchema()
//    df_mongo.printSchema()
//
//    val df_merged = df_sohu2.union(df_mongo)
//    println(df_sohu2.count())
//    println(df_mongo.count())
//    println(df_merged.count())
//    df_merged.show()
//
//    df_merged.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_merged.parquet")

    val df_merged_o = sqc
      .read
      .load("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_merged.parquet")
      .withColumnRenamed("category","category_o")

    val df_merged = df_merged_o
      .withColumn("category",DataFrameManager.transferCategory(df_merged_o("category_o")))
      .select("news_id","contenttitle","content","url","publish_time","category","comments","likes","dislikes","score")

    df_merged.createOrReplaceTempView("news")
    sparkSession.sql("SELECT category,count(*) FROM news GROUP BY category").show(100)

    df_merged.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/mergeIntermediate/news_merged_mergedCategories.parquet")

    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")

  }

}
