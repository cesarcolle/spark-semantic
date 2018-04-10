package com.orange.lambda.spark.mapper

import com.orange.lambda.spark.datamodel.DcatDataModel
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.SparkSessionTest

class MLMapperTest extends FunSuite with SparkSessionTest with BeforeAndAfter{

  after{
    if( sparkSession !=  null){
      sparkSession.stop()
      sparkSession.close()
    }
  }

  test("reformat first dataset"){
    val dataframe = sparkSession.read.schema(DcatDataModel.dcatSchema).json("src/test/resources/dcat.json")
    val out = MLMapper.fitDataFrame(dataframe)
    assert(out.select("theme").rdd.count() > 0)
    assert(out.select("description").rdd.collect()(0).get(0) == "this is a description")

  }

}
