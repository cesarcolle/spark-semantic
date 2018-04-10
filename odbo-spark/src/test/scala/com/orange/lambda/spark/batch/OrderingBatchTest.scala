package com.orange.lambda.spark.batch

import java.io.File
import java.nio.file.{Files, Paths}

import com.orange.lambda.spark.fetch.FetchBuilder
import com.orange.lambda.spark.fetch.FetchBuilder.Fetch.EmptyFetch
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.hadoop.hdfs.MiniDFSCluster
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}
import util.{MiniHdfsCluster, SparkSessionTest}

class OrderingBatchTest extends FunSuite with BeforeAndAfter with SparkSessionTest {

  var hdfsCluster: String = _
  var fileSystem: FileSystem = _

  var dfsCluster: MiniDFSCluster = _
  var namenode: String = _

  var testDir = "src/test/resources/hdfsOrderingBatch"


  before {

    namenode = MiniHdfsCluster.provideCluster()
    println(namenode)
    val conf = new Configuration()
    conf.set("fs.defaultFS", namenode)
    fileSystem = FileSystem.get(conf)
    val contentClassify = Files.readAllBytes(Paths.get("src/test/resources/dcat.json"))
    val contentLearning = Files.readAllBytes(Paths.get("src/test/resources/classify.json"))

    val bufferClassify = fileSystem.create(new Path("/flume/1805/123/classify.json"))
    val bufferLearning = fileSystem.create(new Path("/flume/1789/12345/dcat.json"))

    bufferClassify.write(contentClassify)
    bufferLearning.write(contentLearning)

    bufferClassify.close()
    bufferLearning.close()
  }

  after {
    sparkSession.stop()
    sparkSession.close()

    MiniHdfsCluster.shutdown()
  }

  test("ordering json in hdfs") {

    val fetch = new FetchBuilder[EmptyFetch].withComponent("hdfs").withAddress(namenode).withPath("/flume/*/*/*.json").withSparkContext(sparkSession = sparkSession).build
    val orderingBatch = new OrderingBatch(fetch)
    orderingBatch.launch()
    val out = fileSystem.listFiles(new Path("/classifier"), true)
    assert(out.hasNext)

  }

}
