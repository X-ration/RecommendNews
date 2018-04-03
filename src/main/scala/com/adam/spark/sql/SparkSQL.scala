package com.adam.spark.sql

import org.apache.spark.sql.SparkSession

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

    val df_sohu = sparkSession.read.load("hdfs://172.17.11.180:9000/data/rec_news/news_sohu.parquet")
    val df_ten = sparkSession.read.load("hdfs://172.17.11.180:9000/data/rec_news/news_ten.parquet").na.drop()

//    val df_sohu_id = sqc.range(1,df_sohu.count()+1)
//    df_sohu_id.show()
//    println(df_sohu.count())
//    println(df_sohu_id.describe())
    //df_sohu.withColumn("id",df_sohu_id("id"))

    df_sohu.show()
    df_ten.show()

    df_sohu.registerTempTable("sohu")
    val df = sparkSession.sql("SELECT url FROM sohu ").rdd.map(row => row.getString(0).split('/')(2))
    df.distinct().foreach(println)


    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")

  }

}
