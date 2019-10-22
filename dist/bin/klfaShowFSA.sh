
#. ./setVariables.sh
export KLFA_PATH=$KLFA_HOME/klfa-standalone.jar

java -cp $KLFA_PATH tools.ShowFSA $@
