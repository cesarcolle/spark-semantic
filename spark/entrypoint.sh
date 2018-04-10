#!/usr/bin/env bash

echo "Submitting the application ...."
/spark/bin/spark-submit --class com.orange.lambda.spark.Main --master "spark://spark-master:7077" odbo.jar
