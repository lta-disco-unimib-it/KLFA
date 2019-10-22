
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.preprocessing.rawEventsSeparation.RegexBasedRawEventsSeparator -eventStartExpression "\[#\|2008.*" correctLogs/server.log* events.correct.txt
