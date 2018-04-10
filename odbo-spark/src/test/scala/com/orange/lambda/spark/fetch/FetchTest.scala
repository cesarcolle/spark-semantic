package com.orange.lambda.spark.fetch

import com.orange.lambda.spark.datamodel.DcatDataModel
import com.orange.lambda.spark.fetch.FetchBuilder.Fetch.EmptyFetch
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when

class FetchTest extends FunSuite with MockitoSugar with BeforeAndAfter {

  val sparkSession: SparkSession = mock[SparkSession]
  val dataFrameMock: DataFrame = mock[DataFrame]
  val dataFrameReaderMock: DataFrameReader = mock[DataFrameReader]
  val rddDataFrame: RDD[Row] = mock[RDD[Row]]

  before{
    when(sparkSession.read).thenReturn(dataFrameReaderMock)
    when(dataFrameReaderMock.json("mock")).thenReturn(dataFrameMock)
    when(dataFrameReaderMock.schema(DcatDataModel.dcatSchema)).thenReturn(dataFrameReaderMock)
    when(dataFrameMock.rdd).thenReturn(rddDataFrame)
    when(rddDataFrame.count()).thenReturn(1)

  }
  test("Fetch data in the miniHDFS cluster"){
    val content :FetchData = new FetchBuilder[EmptyFetch]().withAddress("mock" ).withComponent("hdfs").withSparkContext(sparkSession).withPath("").build
    assert(content.fetch().rdd.count() == 1)
  }
}
