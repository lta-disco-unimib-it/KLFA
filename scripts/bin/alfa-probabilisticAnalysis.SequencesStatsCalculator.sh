#!/bin/bash

#class=`basename $0|sed 's/^alfa-//'|sed 's/.sh$//g'`

java -cp $KLFA_HOME/klfa-standalone.jar it.unimib.disco.lta.alfa.probabilisticAnalysis.SequencesStatsCalculator $*

