package com.orange.lambda.spark.ml

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.DataFrame

trait Analyzer {

  def algorithm: LogisticRegression = new LogisticRegression()

  def fit(dataFrame: DataFrame)

  def classify(dataFrame: DataFrame)

}
