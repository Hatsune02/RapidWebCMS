#! /bin/bash
echo "STARTING JFLEX COMPILING"
java -jar /home/dog/flexycup/jflex-full-1.9.1.jar -d ../../../../java/com/navi/backend/XMLParserLexer/ XMLLexer.flex

echo "STARTING CUP COMPILING"
java -jar /home/dog/flexycup/java-cup-11b.jar -parser XMLParser XMLParser.cup
mv XMLParser.java ../../../../java/com/navi/backend/XMLParserLexer/XMLParser.java
mv sym.java ../../../../java/com/navi/backend/XMLParserLexer/sym.java

echo "STARTING JFLEX COMPILING"
java -jar /home/dog/flexycup/jflex-full-1.9.1.jar -d ../../../../java/com/navi/backend/CMSParserLexer/ CMSLexer.flex

echo "STARTING CUP COMPILING"
java -jar /home/dog/flexycup/java-cup-11b.jar -parser CMSParser CMSParser.cup
mv CMSParser.java ../../../../java/com/navi/backend/CMSParserLexer/CMSParser.java
mv sym.java ../../../../java/com/navi/backend/CMSParserLexer/sym.java