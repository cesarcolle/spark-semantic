package util

import org.apache.spark.sql.SparkSession

trait SparkSessionTest {

  val sparkSession = SparkSession.builder().appName("test-classifier").master("local").getOrCreate()


}
