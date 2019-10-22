
#calculate statistics considering the first two columns as the signature elements
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
java -Dklfa.logging.level=FINEST -cp $KLFA_PATH it.unimib.disco.lta.alfa.parametersAnalysis.ParametersAnalyzer $@
