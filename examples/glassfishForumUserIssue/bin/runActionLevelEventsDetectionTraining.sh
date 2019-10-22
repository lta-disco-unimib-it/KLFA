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

export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector -dontSplitComponents -slctExecutablePath $SLCT_BIN -replacement "CORE5076: Using.*" "Using Java" -replacement ".*/domains/domain1/config/" "/domains/domain1/config/" -replacement "service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi" "" -replacement "service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi" "" -replacement "\|INFO\|" "" -replacement "\|FINE\|" "" -replacement "\|DEBUG\|" "" -replacement "\|FINEST\|" "" -replacement "\|FINER\|" "" -dataExpression "\[#\|2008.*\|.*\|.*\|.*\|.*\|(.*)\|#\]" -componentExpression "\[#\|2008.*\|.*\|.*\|(.*)\|.*\|.*\|#\]" -exportRules rules.properties -workingDir trainingCsvGen -componentsDefinitionFile components.training.properties events.correct.txt events.correct.csv
