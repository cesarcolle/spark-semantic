FROM develar/java:8u45

ENV http_proxy="http://10.193.21.110:8080"
ENV https_proxy="https://10.193.21.110:8080"


ENV FLUME_VERSION 1.8.0
ENV FLUME_HOME /opt/lib/flume

RUN echo "http_proxy=$http_proxy/" >> /etc/wgetrc
RUN echo "use_proxy=on" >> /etc/wgetrc


RUN mkdir -p /opt/lib && \
    wget -q http://www.eu.apache.org/dist/flume/$FLUME_VERSION/apache-flume-$FLUME_VERSION-bin.tar.gz -O /opt/lib/apache-flume-$FLUME_VERSION-bin.tar.gz && \
    tar xzf /opt/lib/apache-flume-$FLUME_VERSION-bin.tar.gz -C /opt/lib && \
    mv /opt/lib/apache-flume-$FLUME_VERSION-bin /opt/lib/flume && \
    rm /opt/lib/apache-flume-$FLUME_VERSION-bin.tar.gz && \
    mv $FLUME_HOME/conf/flume-conf.properties.template $FLUME_HOME/conf/flume-conf.properties

COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

ENV HADOOP_VERSION=2.9.0
ENV HADOOP_HOME=/opt/lib/hadoop-$HADOOP_VERSION

RUN wget -q http://wwwftp.ciril.fr/pub/apache/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz -O /opt/lib/hadoop-$HADOOP_VERSION.tar.gz && \
    tar xzf /opt/lib/hadoop-$HADOOP_VERSION.tar.gz -C /opt/lib && \
    rm /opt/lib/hadoop-$HADOOP_VERSION.tar.gz

RUN apk add --update bash && rm -rf /var/cache/apk/*

ADD flume-conf.properties /opt/flume/conf/
ADD log4j.properties /opt/flume/conf/
ENTRYPOINT [ "/docker-entrypoint.sh" ]

