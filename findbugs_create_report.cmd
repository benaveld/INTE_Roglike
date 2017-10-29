@echo off
mkdir findbugs
"C:\Program Files (x86)\findbugs-3.0.1\bin\findbugs" -textui -maxHeap 1500 -nested:false -output findbugs/findbugs_report.html -effort:max -low -sortByClass -html:fancy.xsl  -auxclasspath d:\Studier\SU\INTE\HT2017\Kod\INTE_Roglike\tools\junit-4.12.jar d:\Studier\SU\INTE\HT2017\Kod\INTE_Roglike\lib\INTE_Roglike.jar
