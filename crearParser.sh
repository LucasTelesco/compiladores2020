#!/bin/bash
./yacc.linux -J gramaticaGrupo10.y
mv Parser.java src/AnalizadorSintactico/
rm ParserVal.java
