#!/bin/bash

#change to the location of your JDBC driver
export JDBC_DRIVER=/opt/mysql-connector-java-5.1.7/mysql-connector-java-5.1.7-bin.jar
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar

java -cp $KLFA_PATH:$JDBC_DRIVER tools.dataExporting.NormalizedDataCsvExporter $@


