package com.adam.spark.dataFrameManager

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.adam.rec.news.NewsCategories
import org.apache.spark.sql.functions.udf

import scala.util.Random

object DataFrameManager {

  private var id = 5000000
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

  val withDefaultComment = udf(()=>"[]")

  val withContentListed = udf((content:String)=>Array[String](content))

  val withEmptyComments = udf(()=>Array[String]())   //以此种方式声明数组时一定要声明类型，否则入坑！

  val transferCategory = udf((category:String)=> category match {
    case "finance" => "财经"
    case "news" => "新闻"
    case "auto" => "汽车"
    case "ent" => "娱乐"
    case "edu" => "教育"
    case "games" => "游戏"
    case "mil" => "军事"
    case "sports" => "体育"
    case "house" => "家居"
    case "tech" => "科技"
    case "cj" => "财经"
    case s:String => s
  })

}