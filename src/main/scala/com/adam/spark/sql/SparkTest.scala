package com.adam.spark.sql

import com.adam.rec.spark.SparkManager
import org.apache.spark.sql.SparkSession

/**
  * @author adam
  *         创建于 2018-04-23 15:44.
  *
  */
object SparkTest {

  def main(args:Array[String]):Unit = {

    val timeStart = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
      .appName("ApplicationMain")
      .master("local[*]")
      .getOrCreate()
    val sqc = sparkSession.sqlContext
    // 设定运行时配置
    sparkSession.conf.set("spark.driver.memory","6g")
    //    sparkSession.conf.set("spark.local.dir","G:\\sparkTemp")


    val df_all = sparkSession
      .read.load(SparkManager.NEWS_ALL_LOCATION)
    df_all.createOrReplaceTempView("news_all")
    val query = sparkSession.sql("SELECT MAX(news_id) FROM " + SparkManager.NEWS_ALL_TABLE);
    query.show(100)


    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")

  }

}
