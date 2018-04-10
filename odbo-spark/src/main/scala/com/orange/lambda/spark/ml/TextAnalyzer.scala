package com.orange.lambda.spark.ml

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, RegexTokenizer, StringIndexer}
import org.apache.spark.sql.DataFrame

object TextAnalyzer extends Analyzer {
  private val label: StringIndexer = new StringIndexer().setInputCol("theme").setOutputCol("label").setHandleInvalid("keep")

  private val tokenizer: RegexTokenizer = new RegexTokenizer()
    .setInputCol("description")
    .setOutputCol("words")

  private val hashingTF: HashingTF = new HashingTF()
    .setInputCol(tokenizer.getOutputCol)
    .setOutputCol("features")
    .setNumFeatures(5000)

  private val pipeline: Pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, label, algorithm))

  protected var learningModel: PipelineModel = _


  override def fit(dataFrame: DataFrame): Unit = {
    learningModel = pipeline.fit(dataFrame)
  }

  override def classify(dataFrame: DataFrame): Unit = {
    val transform = learningModel.transform(dataFrame)
  }
}
