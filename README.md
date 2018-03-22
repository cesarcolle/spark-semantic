# Spark-semantic

Simple spark architecture to compute batch on hdfs file source.

## Architecture



## Stack

### Flume

Flume will allow to consume data from Kafka and feed a HDFS cluster.

### HDFS

Distribued file system to store all the data our system want to ingest. Allow us to have immutable data , avoiding the data corruption.

### Spark

Spark allow batch to be compute in RAM. It's has best performance with machine learning algorithm.
