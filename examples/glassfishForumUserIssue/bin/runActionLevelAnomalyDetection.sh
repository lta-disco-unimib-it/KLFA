
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH tools.kLFAEngine.LogTraceAnalyzer -splitBehavioralSequences -actionsLines ../actionsAnalysisConfiguration/actions.fail.properties -separator "," -minimizationLimit 100 -outputDir klfaCheckingActionLevel -inputDir klfaTrainingActionLevel actionLevel checking transformersConfig.txt preprocessingRules.txt events.fail.csv
