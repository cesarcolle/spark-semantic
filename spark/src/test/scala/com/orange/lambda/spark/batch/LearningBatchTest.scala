package com.orange.lambda.spark.batch


import com.orange.lambda.spark.datamodel.DcatDataModel
import com.orange.lambda.spark.fetch.FetchData
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import util.SparkSessionTest
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when

class LearningBatchTest extends FunSuite with MockitoSugar with BeforeAndAfterAll with SparkSessionTest {

  var learningBatch: LearningBatch = _
  val fetch : FetchData = mock[FetchData]

  override def beforeAll(){
    learningBatch = new LearningBatch(fetch)
  }

  override def afterAll(): Unit = {
    if( sparkSession !=  null){
      sparkSession.stop()
      sparkSession.close()
    }
  }


  test("Learning from theme JSON.") {
    val  dataframe1 = sparkSession.read.schema(DcatDataModel.dcatSchema).json("src/test/resources/dcat.json")
    when(fetch.fetch()).thenReturn(dataframe1)
    learningBatch.launch()
  }

  test("learning from several JSON"){
    val  dataframe1 = sparkSession.read.schema(DcatDataModel.dcatSchema).json("src/test/resources/dcat*.json")
    dataframe1.show()
    when(fetch.fetch()).thenReturn(dataframe1)
    learningBatch.launch()
  }

  test("learning with complete-dcat-json"){
    val  dataframe1 = sparkSession.read.schema(DcatDataModel.dcatSchema).json("src/test/resources/*.json")
    when(fetch.fetch()).thenReturn(dataframe1)
    learningBatch.launch()
  }



}
