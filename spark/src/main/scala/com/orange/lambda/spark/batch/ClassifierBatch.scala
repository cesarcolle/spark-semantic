package com.orange.lambda.spark.batch

import org.apache.log4j.{LogManager, Logger}

import com.orange.lambda.spark.fetch.FetchData
import com.orange.lambda.spark.mapper.MLMapper
import com.orange.lambda.spark.ml.TextAnalyzer

class ClassifierBatch(fetchData : FetchData) extends Batch {

  val LOG = LogManager.getRootLogger

  override val fetch: FetchData = fetchData

  override def launch(): Unit = {
    val dataframe = fetch.fetch()
    if (dataframe.count() < 1){
      LOG.error("Nothing to Classify...")
      return
    }
    LOG.error("classify text...")
    TextAnalyzer.classify(MLMapper.fitDataFrame(dataframe))

  }

}
