package com.orange.lambda.spark.engine

import java.nio.file.{Files, Paths}

import com.orange.lambda.spark.batch.{ClassifierBatch, LearningBatch, OrderingBatch}
import com.orange.lambda.spark.fetch.FetchBuilder
import com.orange.lambda.spark.fetch.FetchBuilder.Fetch.EmptyFetch
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.util.Shell
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import util.{MiniHdfsCluster, SparkSessionTest}

class BatchHdfsEngineTest extends FunSuite  with BeforeAndAfterAll with SparkSessionTest {

    val hdfsCluster: String = MiniHdfsCluster.provideCluster()

    var learningBatch: LearningBatch = _
    var classifierBatch: ClassifierBatch = _
    var orderingBatch: OrderingBatch = _
    var fileSystem: FileSystem = _

    override def afterAll(): Unit = {
      MiniHdfsCluster.shutdown()
      if( sparkSession !=  null){
        sparkSession.stop()
        sparkSession.close()
      }
    }

    override def beforeAll(): Unit = {

      MiniHdfsCluster.writeFile("src/test/resources/dcat.json","/flume/123/12345/classify.json" )
      MiniHdfsCluster.writeFile("src/test/resources/dcat-citizen.json","/flume/1245/1698/classify2.json" )
      MiniHdfsCluster.writeFile("src/test/resources/classify.json","/flume/4568/45697/dcat.json" )


      val fetchClassifier = new FetchBuilder[EmptyFetch].withPath("/classifier/*.json").withComponent("hdfs")
        .withSparkContext(sparkSession).withAddress(hdfsCluster).build

      val fetchLearning = new FetchBuilder[EmptyFetch].withPath("/learn/*.json")
        .withAddress(hdfsCluster).withSparkContext(sparkSession).withComponent("hdfs").build

      val fetchOrdering = new FetchBuilder[EmptyFetch].withPath("/flume/*/*/*.json")
        .withAddress(hdfsCluster).withSparkContext(sparkSession).withComponent("hdfs").build

      learningBatch = new LearningBatch(fetchLearning)
      classifierBatch = new ClassifierBatch(fetchClassifier)
      orderingBatch = new OrderingBatch(fetchOrdering)

    }

    test("testLaunchBatchEngine") {
      val engine = new BatchHdfsEngine(List(orderingBatch, learningBatch, classifierBatch))
      engine.launchBatch()
    }

}
