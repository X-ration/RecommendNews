package com.adam.spark.inputFormats

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Adam
  * Created by Adam on 2018/3/23.
  * 使用Spark读取JSON文件。
  */
object SparkJson {
  def main(args:Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[4]")
      .setAppName("WordCount") //设置APP 的name，设置Local 模式的CPU资源
    val sc = new SparkContext(conf)
    val ssc = new SQLContext(sc)
    val df = ssc.read.json("hdfs://172.17.11.180:9000/data/newsDatas/NewsDatas/qqNewsEdu.json")

    df.show()
  }
}
