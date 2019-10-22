
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector -dontSplitComponents -slctExecutablePath $SLCT_BIN -replacement "CORE5076: Using.*" "Using Java" -replacement ".*/domains/domain1/config/" "/domains/domain1/config/" -replacement "service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi" "" -replacement "service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi" "" -replacement "\|INFO\|" "" -replacement "\|FINE\|" "" -replacement "\|DEBUG\|" "" -replacement "\|FINEST\|" "" -replacement "\|FINER\|" "" -dataExpression "\[#\|2008.*\|.*\|.*\|.*\|.*\|(.*)\|#\]" -componentExpression "\[#\|2008.*\|.*\|.*\|(.*)\|.*\|.*\|#\]" -exportRules rules.properties -workingDir trainingCsvGen -componentsDefinitionFile components.training.properties events.correct.txt events.correct.csv
