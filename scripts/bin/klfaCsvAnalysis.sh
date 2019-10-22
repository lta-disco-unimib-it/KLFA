
#. ./setVariables.sh
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.klfa.LogTraceAnalyzer $@
