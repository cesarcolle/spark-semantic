package com.orange.lambda.spark.batch

import com.orange.lambda.spark.fetch.FetchData

trait Batch {
  val fetch: FetchData

  def launch()

}
