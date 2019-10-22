#!/bin/bash

mkdir tmp
cd tmp
cp ../../../BCT/bin/* . -R
cp ../../bin/* . -R

jar cmf ../../META-INF/MANIFEST.MF klfa.jar *
