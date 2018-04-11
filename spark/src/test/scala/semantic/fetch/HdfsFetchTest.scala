package com.orange.lambda.spark.fetch

import java.io.File
import java.nio.file.{Files, Paths}

import com.orange.lambda.spark.fetch.FetchBuilder.Fetch.EmptyFetch
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.hadoop.hdfs.MiniDFSCluster
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.{MiniHdfsCluster, SparkSessionTest}

class HdfsFetchTest extends FunSuite  with BeforeAndAfter with MockitoSugar with SparkSessionTest {

  var namenode : String = _
  var dfsCluster : MiniDFSCluster = _

  before{
   namenode = MiniHdfsCluster.provideCluster()

    val conf = new Configuration()
    conf.set("fs.defaultFS", namenode)

    val fileSystem = FileSystem.get(conf)

    val one = fileSystem.create(new Path("/one.json"))
    val two = fileSystem.create(new Path("/two.json"))
    val three = fileSystem.create(new Path("/three/three.json"))
    one.writeBytes("{one : 1}")
    two.writeBytes("{two : 2}")
    three.writeBytes("{three : 3}")
    one.close()
    two.close()
    three.close()
  }

  after{

    MiniHdfsCluster.shutdown()
    sparkSession.stop()
    sparkSession.close()
  }

  test("Fetch hdfs file") {
    val data = new FetchBuilder[EmptyFetch].withSparkContext(sparkSession = sparkSession).withAddress(namenode).withPath("/*").withComponent("hdfs").build
    val out = data.fetch()
    assert(out.count() == 3)
  }




}
