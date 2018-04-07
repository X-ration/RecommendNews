package com.adam.spark.inputFormats

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Adam
  * Created by Adam on 2018/3/23.
  * 使用Spark读取XML文件。
  */
object SparkXml {

  def main(args:Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("ReadXml")
      .setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqc = new SQLContext(sc)

    val df = sqc.read
      .format("com.databricks.spark.xml")
      .option("rowTag","doc")
      .load("C:\\Users\\Adam\\Documents\\toutiao\\news_sohusite_xml.full\\news_sohusite_xml.xml")

    df.show()
    df.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/news_sohu.parquet")

    val df2 = sqc.read
      .format("com.databricks.spark.xml")
      .option("rowTag","doc")
      .load("C:\\Users\\Adam\\Documents\\toutiao\\news_tensite_xml.full\\news_tensite_xml.xml")
      .drop("_corrupt_record")

    df2.show()
    df2.write.format("parquet").save("hdfs://172.17.11.180:9000/data/rec_news/news_ten.parquet")

  }

}
