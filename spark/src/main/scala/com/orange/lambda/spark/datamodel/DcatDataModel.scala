package com.orange.lambda.spark.datamodel

import org.apache.spark.sql.types.{ArrayType, StringType, StructField, StructType}

object DcatDataModel {
  val dcatSchema = StructType(Seq(StructField("theme", StringType),
    StructField("name", StringType), StructField("notes", StringType), StructField("resources", ArrayType(StructType(Array(
      StructField("description", StringType),
      StructField("type", StringType)
    ))))))
}
