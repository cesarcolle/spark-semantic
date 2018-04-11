package semantic.batch

import org.apache.log4j.{LogManager, Logger}
import semantic.fetch.FetchData

class OrderingBatch(fetchData: FetchData) extends Batch {

  override val fetch: FetchData = fetchData
  val LOG: Logger = LogManager.getRootLogger

  override def launch(): Unit = {
    val dataframe = fetch.fetch()
    if (dataframe.count() < 1) {
      LOG.error("Nothing to Order...")
      return
    }
    val classify = dataframe.filter("theme is null or theme == 'null'")
    classify.write.mode("append").json(fetch.getAddress + "/classifier/")
    dataframe.intersect(classify).write.mode("append").json(fetch.getAddress + "/learn/")
  }
}
