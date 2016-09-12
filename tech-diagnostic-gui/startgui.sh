#!/bin/bash

if [ $# -ne "1" ]
then
  echo "Usage: `basename $0` log4jpropertiesfile"
  exit $E_BADARGS
fi

java -cp tech-diagnostic-gui.jar:`hadoop classpath`:/usr/lib/flume-ng/lib/* -Dlog4j.configuration=$1 com.loudacre.techdiagnosticgui.TechDiagnosticGui
