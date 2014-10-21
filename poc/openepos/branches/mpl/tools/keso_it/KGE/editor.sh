#!/bin/sh

rm -rf editor_classes
mkdir editor_classes

echo "Dont forget to run make in src"
echo "Compile..."
javac -d ./editor_classes -classpath ./:./zip/:./epslib/src/:./swt-linux-x86/swt.jar:../src/builder/classes keso/editor/KGEMain.java

echo "Start..."
java -Djava.library.path="./swt-linux-x86/" -classpath ./:./epslib/src/:./swt-linux-x86/swt.jar:../src/builder/classes:./editor_classes keso.editor.KGEMain
