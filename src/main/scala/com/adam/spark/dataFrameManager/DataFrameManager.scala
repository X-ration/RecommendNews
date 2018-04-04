package com.adam.spark.dataFrameManager

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.adam.rec.news.NewsCategories
import org.apache.spark.sql.functions.udf

import scala.util.Random

object DataFrameManager {

  private var id = 0
  private val random = new Random()

  def generateCurrentId : Int = {
    id = id+1
    id
  }

  val withId = udf(()=>generateCurrentId)

  def generateRandomDateTime : LocalDateTime = {
    val day = random.nextInt(3)+1
    val hour = random.nextInt(24)
    val minute = random.nextInt(60)
    val second = random.nextInt(60)
    LocalDateTime.of(2018,4,day,hour,minute,second)
  }

  val withDateTime = udf(()=>generateRandomDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))

  val withCategory = udf((url:String)=>NewsCategories.identifyCategory(url))

  val withDefaultCount = (default:Int) => udf(()=>default)
  val withDefaultScore = (default:Double) => udf(()=>default)

}
