
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector -dontSplitComponents -replacement "CORE5076: Using.*" "Using Java" -replacement ".*/domains/domain1/config/" "/domains/domain1/config/" -replacement "service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi" "" -replacement "service:jmx:rmi:///jndi/rmi://.*:8686/jmxrmi" "" -replacement "\|INFO\|" "" -replacement "\|FINE\|" "" -replacement "\|DEBUG\|" "" -replacement "\|FINEST\|" "" -replacement "\|FINER\|" "" -dataExpression "\[#\|2008.*\|.*\|.*\|.*\|.*\|(.*)\|#\]" -componentExpression "\[#\|2008.*\|.*\|.*\|(.*)\|.*\|.*\|#\]" -loadComponents components.training.properties -exportRules rules.checking.properties -workingDir checkingCsvGen -loadEventPatterns -patternsDir trainingCsvGen -componentsDefinitionFile components.fail.properties events.fail.txt events.fail.csv
