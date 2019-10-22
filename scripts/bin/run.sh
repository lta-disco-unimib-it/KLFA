#!/bin/bash

class=`basename $0|sed 's/^alfa-//'|sed 's/.sh$//g'`

java -cp $ALFA_HOME/bin $class $*

