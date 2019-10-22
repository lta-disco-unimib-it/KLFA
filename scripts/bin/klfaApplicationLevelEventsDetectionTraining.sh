
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar
export SLCT_BIN=/opt/slct/slct

java -cp $KLFA_PATH it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector -dontSplitComponents -slctExecutablePath $SLCT_BIN -exportRules rules.properties -workingDir trainingCsvGen -componentsDefinitionFile components.training.properties $@
