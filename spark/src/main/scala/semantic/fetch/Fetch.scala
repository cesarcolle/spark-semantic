package semantic.fetch

import org.apache.spark.sql.{DataFrame, SparkSession}

trait Fetch {

  def fetchData(session: SparkSession, address: String): DataFrame

}
