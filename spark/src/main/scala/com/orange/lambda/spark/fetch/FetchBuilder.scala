package com.orange.lambda.spark.fetch
import org.apache.spark.sql.{DataFrame, SparkSession}

object FetchBuilder {

  sealed trait Fetch

  object Fetch {

    sealed trait EmptyFetch extends Fetch

    sealed trait Address extends Fetch

    sealed trait Path extends Fetch

    sealed trait Component extends Fetch

    sealed trait Spark extends Fetch

    type FullFetch = EmptyFetch with Address with Component with Spark with Path
  }

}

class FetchData(component: String, address: String, sparkSession: SparkSession, path: String) {
  private val fetchs = Map("hdfs" -> HdfsFetch)

  val getAddress: String = address

  def fetch(): DataFrame = {
    fetchs(component).fetchData(sparkSession, address + path)
  }
}

class FetchBuilder[Fetch <: FetchBuilder.Fetch](address: String = "", component: String = "", sparkSession: SparkSession = null, path: String = "") {

  import FetchBuilder.Fetch._

  def withAddress(addr: String): FetchBuilder[Fetch with Address] = new FetchBuilder[Fetch with Address](addr, component, sparkSession, path)

  def withComponent(compo: String): FetchBuilder[Fetch with Component] = new FetchBuilder[Fetch with Component](address, compo, sparkSession, path)

  def withSparkContext(sparkSession: SparkSession): FetchBuilder[Fetch with Spark] = new FetchBuilder[Fetch with Spark](address, component, sparkSession, path)

  def withPath(pathToFile: String): FetchBuilder[Fetch with Path] = new FetchBuilder[Fetch with Path](address, component, sparkSession, pathToFile)


  def build(implicit ev: Fetch =:= FullFetch): FetchData = {
    new FetchData(component, address, sparkSession, path)
  }

}
