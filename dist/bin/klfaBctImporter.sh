#!/bin/bash
#*******************************************************************************
#    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
#   
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#*******************************************************************************

#change to the location of your JDBC driver
export JDBC_DRIVER=/opt/mysql-connector-java-5.1.7/mysql-connector-java-5.1.7-bin.jar
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar

java -cp $KLFA_PATH:$JDBC_DRIVER tools.dataExporting.NormalizedDataCsvExporter $@


