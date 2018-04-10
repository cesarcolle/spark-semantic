#FROM hseeberger/scala-sbt as builder

FROM bde2020/spark-base:2.3.0-hadoop2.7

WORKDIR /root
COPY ./target/scala-2.11/odbo-spark_2.11-0.1.jar odbo.jar

ENV INIT_DAEMON_STEP=setup_spark
EXPOSE "8080:8080"
EXPOSE "7077:7077"
COPY entrypoint.sh .
CMD ["/bin/bash", "entrypoint.sh"]