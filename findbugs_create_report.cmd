@echo off
mkdir docs\findbugs
"C:\Program Files (x86)\findbugs-3.0.1\bin\findbugs" -textui -maxHeap 1500 -nested:false -output docs/findbugs/findbugs_report.html -effort:max -low -sortByClass -html:fancy.xsl  -auxclasspath tools\junit-4.12.jar lib\INTE_Roglike.jar