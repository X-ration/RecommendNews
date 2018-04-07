package com.adam.spark.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTime {

  def main(args:Array[String]) : Unit = {

    val now = LocalDateTime.now
    println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
    val other = LocalDateTime.of(2017,1,3,13,53,9)
    println(other.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))

  }

}
