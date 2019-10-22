
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH tools.kLFAEngine.LogTraceAnalyzer -splitBehavioralSequences -actionsLines ../actionsAnalysisConfiguration/actions.correct.properties -separator "," -minimizationLimit 100 -outputDir klfaTrainingActionLevel actionLevel training transformersConfig.txt preprocessingRules.txt events.correct.csv
