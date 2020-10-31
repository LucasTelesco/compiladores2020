import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import SymbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

import Errors.Errors;

import java.util.ArrayList;

public class Main {





    public static void main(String Args[]) {

        App a = null;
        JFileChooser filea = new JFileChooser();
        String archivo="";
        Errors errors;
        LexicalAnalyzer.OutFile outFile = new LexicalAnalyzer.OutFile();
        try {

            String srcCode = Args[0];//"srcCode";
            FileReader file = new FileReader(srcCode);
            BufferedReader src= new BufferedReader(file);
            String cadena;
            ArrayList<String> estructuras=new ArrayList<>();

            while ((cadena = src.readLine()) != null)
                archivo += cadena+"\n";
            archivo+=  "\n";
            archivo+=  "\n";
            archivo = archivo.substring(0,archivo.length()-1);
            errors = new Errors();
            final SymbolTable st = new SymbolTable();
            final LexicalAnalyzer lexical = new LexicalAnalyzer(archivo,st,errors);
            final AnalizadorSintactico.Parser par = new Parser(lexical,st,errors,estructuras);
            System.out.println("----------------------------------------------------------");
            System.out.println("TOKENS RECONOCIDOS: ");
            par.run();

            outFile.errorView(errors);
            outFile.tlFile(st);
            outFile.structOut(estructuras);

        } catch (IOException e) {
            System.out.println("No se puede crear ejecutable, revise estructura de errores ");
        }
    }
}
