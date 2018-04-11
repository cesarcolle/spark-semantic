package com.orange.lambda.spark.batch

import com.orange.lambda.spark.datamodel.DcatDataModel
import com.orange.lambda.spark.fetch.FetchData
import com.orange.lambda.spark.mapper.MLMapper
import org.apache.spark.sql.SparkSession
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import util.SparkSessionTest

class ClassifierBatchTest extends FunSuite with SparkSessionTest with BeforeAndAfterAll with MockitoSugar {

  var classifierBatch: ClassifierBatch = _
  var learningBatch: LearningBatch = _
  val fetchLearning: FetchData = mock[FetchData]
  val fetchClassify: FetchData = mock[FetchData]

  override def beforeAll(): Unit = {

    classifierBatch = new ClassifierBatch(fetchClassify)
    learningBatch = new LearningBatch(fetchLearning)
    val dataframeLearning = sparkSession.read.schema(DcatDataModel.dcatSchema).json("src/test/resources/dcat*.json")
    when(fetchLearning.fetch()).thenReturn(dataframeLearning)
    learningBatch.launch()
  }

  override def afterAll(): Unit = {
    if( sparkSession !=  null){
      sparkSession.stop()
      sparkSession.close()
    }
  }

  test("Classify simple JSON") {

    val dataframeClassify = sparkSession.read.schema(DcatDataModel.dcatSchema).json("src/test/resources/classify.json")
    when(fetchClassify.fetch()).thenReturn(dataframeClassify)
    classifierBatch.launch()
  }

}
