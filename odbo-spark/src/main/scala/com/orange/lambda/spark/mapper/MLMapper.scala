package com.orange.lambda.spark.mapper

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object MLMapper {

  def fitDataFrame(dataFrame: DataFrame): DataFrame = {
    val resourcesDF = dataFrame.select(col("name"), explode(col("resources").getField("description")).as("description"))
    val fieldsDF = dataFrame.select("theme", "name", "notes")
    resourcesDF.crossJoin(fieldsDF)
  }

}
