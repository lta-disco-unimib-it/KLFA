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

java -cp $KLFA_PATH tools.kLFAEngine.LogTraceAnalyzer -splitBehavioralSequences -actionsLines ../actionsAnalysisConfiguration/actions.correct.properties -separator "," -minimizationLimit 100 -outputDir klfaTrainingActionLevel actionLevel training transformersConfig.txt preprocessingRules.txt events.correct.csv
