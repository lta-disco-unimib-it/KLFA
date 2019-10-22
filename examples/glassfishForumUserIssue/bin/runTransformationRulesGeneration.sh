
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.parametersAnalysis.TransformationRulesGenerator -patterns rules.properties -signatureElements 0,1 events.correct.csv
