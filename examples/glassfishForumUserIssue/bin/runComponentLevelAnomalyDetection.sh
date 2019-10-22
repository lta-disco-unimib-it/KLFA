
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH tools.kLFAEngine.LogTraceAnalyzer -separator "," -minimizationLimit 100 -inputDir klfaTrainingComponentLevel -outputDir klfaCheckingComponentLevel componentLevel checking transformersConfig.txt preprocessingRules.txt events.fail.csv
