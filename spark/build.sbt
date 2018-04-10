name := "odbo-spark"

// sbt-assembly
version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.3.0"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
libraryDependencies += scalacheck % Test

lazy val buildSettings = Seq(
  organization := "com.orange.lambda.spark",
  version := "1.0"
)

parallelExecution in Test := false
parallelExecution in ThisBuild := false

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.hadoop" % "hadoop-client" % "2.7.2" % "provided",
  "org.apache.hadoop" % "hadoop-hdfs" % "2.7.2" % "test",
  "org.apache.hadoop" % "hadoop-common" % "2.7.2" % "test",
  "org.apache.hadoop" % "hadoop-hdfs" % "2.7.2" % "test" classifier "tests",
  "org.apache.hadoop" % "hadoop-common" % "2.7.2" % "test" classifier "tests",
  "org.scalatest" % "scalatest_2.11" % "3.0.5" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)

