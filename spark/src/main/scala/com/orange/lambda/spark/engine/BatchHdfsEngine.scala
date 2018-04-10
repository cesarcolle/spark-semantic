package com.orange.lambda.spark.engine

import com.orange.lambda.spark.batch.Batch

class BatchHdfsEngine(batchList: List[Batch]) {

  def launchBatch(): Unit = {
    batchList.foreach(batch => {
      try {
        batch.launch()
      }
      catch {
        case exception : Exception => exception.printStackTrace()
        case ex : Throwable=> ex.printStackTrace()

      }

    })
  }

}
