#!/bin/bash

set -e


export FLUME_AGENT_NAME=odboagent

FLUME_CONF_DIR=${FLUME_CONF_DIR:-/opt/lib/flume/conf}
FLUME_CONF_FILE=${FLUME_CONF_FILE:-/opt/lib/flume/conf/flume-conf.properties}

[[ -z "${FLUME_AGENT_NAME}" ]] && { echo "FLUME_AGENT_NAME required"; exit 1; }

echo "Starting flume agent : ${FLUME_AGENT_NAME}"

$FLUME_HOME/bin/flume-ng agent -c /opt/flume/conf/ -f /opt/flume/conf/flume-conf.properties -n odboagent
