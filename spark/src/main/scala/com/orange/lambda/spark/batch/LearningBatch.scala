package com.orange.lambda.spark.batch

import com.orange.lambda.spark.fetch.FetchData
import com.orange.lambda.spark.mapper.MLMapper
import com.orange.lambda.spark.ml.TextAnalyzer
import org.apache.log4j.{LogManager, Logger}

class LearningBatch(fetchData: FetchData) extends Batch {
  override val fetch: FetchData = fetchData
  val LOG: Logger = LogManager.getRootLogger


  override def launch(): Unit = {
    val dataframe = fetch.fetch()
    if (dataframe.count() < 1){
      LOG.error("Nothing to Learn...")
      return
    }
    LOG.error("Learning from text...")

    TextAnalyzer.fit(MLMapper.fitDataFrame(dataframe))
  }
}
