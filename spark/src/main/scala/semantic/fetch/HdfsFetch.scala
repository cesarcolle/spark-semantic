package semantic.fetch


import org.apache.log4j.LogManager
import org.apache.spark.sql.{DataFrame, SparkSession}
import semantic.datamodel.DcatDataModel

object HdfsFetch extends Fetch {

  val log = LogManager.getLogger("hdfsFetch")

  override def fetchData(session: SparkSession, address: String): DataFrame = {
    log.info("trying fetching data to " + address)
    try {

      session.read.schema(DcatDataModel.dcatSchema).json(address)
    }
    catch {
      case exception: Exception =>
        exception.printStackTrace()
        null
      case ex: Throwable =>
        ex.printStackTrace()
        null
    }
  }
}
