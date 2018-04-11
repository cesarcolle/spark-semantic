package semantic.batch

import com.orange.lambda.spark.mapper.MLMapper
import com.orange.lambda.spark.ml.TextAnalyzer
import org.apache.log4j.{LogManager, Logger}
import semantic.fetch.FetchData

class LearningBatch(fetchData: FetchData) extends Batch {
  override val fetch: FetchData = fetchData
  val LOG: Logger = LogManager.getRootLogger


  override def launch(): Unit = {
    val dataframe = fetch.fetch()
    if (dataframe.count() < 1) {
      LOG.error("Nothing to Learn...")
      return
    }
    LOG.info("Learning from text...")

    TextAnalyzer.fit(MLMapper.fitDataFrame(dataframe))
  }
}
