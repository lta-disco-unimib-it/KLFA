
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.klfa.LogTraceAnalyzer -separator "," -minimizationLimit 100 -outputDir klfaTrainingApplicationLevel applicationLevel training transformersConfig.txt preprocessingRules.txt events.correct.csv
