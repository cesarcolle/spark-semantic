package semantic


import org.apache.log4j.{Level, LogManager}
import org.apache.spark.sql.SparkSession
import semantic.batch.{ClassifierBatch, LearningBatch, OrderingBatch}
import semantic.engine.BatchHdfsEngine
import semantic.fetch.FetchBuilder
import semantic.fetch.FetchBuilder.Fetch.EmptyFetch

object Main {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("odbo-spark").master("spark://spark-master:7077").getOrCreate()
    val log = LogManager.getRootLogger
    log.setLevel(Level.ERROR)

    log.info("Starting batch ...")

    val hostHDFS = "hdfs://".concat(System.getenv("HOSTNAME_NAMENODE"))

    val fetchLearning = new FetchBuilder[EmptyFetch].withSparkContext(spark).withAddress(hostHDFS).withComponent("hdfs").withPath("/learn/*.json").build
    val fetchClassifier = new FetchBuilder[EmptyFetch].withSparkContext(spark).withAddress(hostHDFS).withComponent("hdfs").withPath("/classifier/*.json").build
    val fetchOrdering = new FetchBuilder[EmptyFetch].withSparkContext(spark).withAddress(hostHDFS).withComponent("hdfs").withPath("/flume/*/*/*.json").build

    val batchLearning = new LearningBatch(fetchLearning)
    val batchClassify = new ClassifierBatch(fetchClassifier)
    val batchOrdering = new OrderingBatch(fetchOrdering)

    val engine = new BatchHdfsEngine(List(batchOrdering, batchLearning, batchClassify))
    while (true) {
      try {
        log.info("Starting batch engine ...")
        engine.launchBatch()
        Thread.sleep(100000)
      }
      catch {
        case exception: Exception => exception.printStackTrace()
      }
    }


  }


}
