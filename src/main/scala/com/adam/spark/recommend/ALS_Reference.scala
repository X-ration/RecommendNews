package com.adam.spark.recommend

import org.apache.spark.mllib.evaluation.{BinaryClassificationMetrics, MulticlassMetrics, RegressionMetrics}
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SparkSession

/**
  * Created by Adam on 2018/3/26.
  */
object ALS_Reference {

  def main(args:Array[String]) : Unit = {

    val timeStart = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
      .appName("ApplicationMain")
      .master("local[*]")
      .getOrCreate()
    // 设定运行时配置
    sparkSession.conf.set("spark.driver.memory","10g")
    sparkSession.conf.set("spark.local.dir","G:\\sparkTemp")
    val sc = sparkSession.sparkContext

    val rawDataUserArtist = sc.textFile("file:\\F:\\data\\spark_rec_music\\user_artist_data.txt")
    //检查用户产品ID是否合法
    //println(rawDataUserArtist.map(_.split(' ')(0).toDouble).stats())
    //println(rawDataUserArtist.map(_.split(' ')(1).toDouble).stats())

    //处理数据中的少量非法信息
    val rawDataArtist = sc.textFile("file:\\F:\\data\\spark_rec_music\\artist_data.txt")
    val dataArtist = rawDataArtist.flatMap(line => {
      val (id,name) = line.span(_ != '\t')
      if(name.isEmpty) None
      else {
        try {
          Some((id.toInt,name.trim))
        } catch {
          case e:NumberFormatException => None
        }
      }
    })
    //dataArtist.collect.foreach(println)
    val rawDataArtistAlias = sc.textFile("file:\\F:\\data\\spark_rec_music\\artist_alias.txt")
    val dataArtistAlias = rawDataArtistAlias.flatMap( line => {
      val tokens = line.split('\t')
      if(tokens(0).isEmpty || tokens(1).isEmpty) None
      else {
        Some((tokens(0).toInt,tokens(1).toInt))
      }
    }).collectAsMap()
    //dataArtistAlias.foreach(println)

    //借助广播变量，将艺术家ID变换为正确ID
    val bDataArtistAlias = sc.broadcast(dataArtistAlias)
    val allData = rawDataUserArtist.map(line => {
      val Array(userId,artistId,count) = line.split(' ').map(_.toInt)
      val finalArtistId = bDataArtistAlias.value.getOrElse(artistId,artistId)
      Rating(userId,finalArtistId,count)
    }).cache()

    //训练ALS模型
    val Array(trainData, cvData) = allData.randomSplit(Array(0.9,0.1))
    trainData.cache()
    cvData.cache()
    val allArtistIds = allData.map(_.product).distinct().collect()
    val bAllArtistIds = sc.broadcast(allArtistIds)
    val model = ALS.trainImplicit(trainData, 10, 5, 0.01, 1.0)
//    println(model.userFeatures.mapValues(_.mkString(", ")).first())  //检查模型训练出的特征

    //检查用户2093760用户推荐结果
    val rawArtistForUser = rawDataUserArtist.map(_.split(' '))
      .filter{case Array(user,_,_) => user.toInt == 2093760}
    val playedArtistsForUser = rawArtistForUser
      .map{case Array(_,artist,_)=>artist.toInt}
      .collect
      .toSet
    println("--------------\n以下为用户播放过的艺术家")
    dataArtist.filter{case (id,name) => playedArtistsForUser.contains(id)}
      .values
      .collect
      .foreach(println)
    val recommendationsForUser = model.recommendProducts(2093760,5).map(_.product)
//    recommendationsForUser.foreach(println)
    println("--------------\n以下为为用户推荐的艺术家")
    dataArtist.filter{case (id,name) => recommendationsForUser.contains(id)}
      .values
      .collect.foreach(println)

//    //评估模型
    val userArtistIds = allData.map{case Rating(user,product,rating) => (user,product)}
    val predictions = model.predict(userArtistIds).map{case Rating(user,product,rating) =>
      ((user,product),rating)}
    val ratingsAndPredictions = allData.map{case Rating(user,product,rating) =>
      ((user,product),rating)}.join(predictions).map(_._2)
    val regressionMetrics = new RegressionMetrics(ratingsAndPredictions)
    println("RMSE="+regressionMetrics.rootMeanSquaredError)

//
//    //与简单推荐模型比较
//    val bListenCount = sc.broadcast(allData.map(r=>(r.product,r.rating)).reduceByKey(_+_).collectAsMap())
//    val predictions2 = userArtistIds.map{case (user,product)=>Rating(user,product,bListenCount.value.getOrElse(product,0.0))}.map{case Rating(user,product,rating) => ((user,product),rating)}
//    val ratingsAndPredictions2 = allData.map{case Rating(user,product,rating) => ((user,product),rating)}.join(predictions2).map(_._2)
//    val regressionMetrics2 = new RegressionMetrics(ratingsAndPredictions2)
//    println("RMSE=" + regressionMetrics2.rootMeanSquaredError)

    //寻找最优参数
    val rank=50
    val lambda = 1.0
    val evaluations =
      //for(rank<-Array(10,50);lambda<-Array(1.0,0.0001);alpha<-Array(1.0,40.0))
      for(alpha<-Array(1.0,40.0,100.0))
        yield {
          val model = ALS.trainImplicit(trainData,rank,10,lambda,alpha)
          val predictions = model.predict(userArtistIds).map{case Rating(user,product,rating)=>((user,product),rating)}
          val ratingsAndPredictions = allData.map{case Rating(user,product,rating) => ((user,product),rating)}.join(predictions).map(_._2)
          val regressionMetrics = new RegressionMetrics(ratingsAndPredictions)
          val rmse = regressionMetrics.rootMeanSquaredError
          val result = ((rank,lambda,alpha),rmse)
          println(result)
          result
        }
    evaluations.sortBy(_._2).reverse.foreach(println)

    val timeEnd = System.currentTimeMillis()
    println("本次程序运行"+(timeEnd-timeStart)/1000.0+"s.")



  }

}
