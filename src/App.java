import javax.swing.*;


import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.Errors;
import SymbolTable.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class App extends JFrame{


    private JPanel panel1;
    private JTextArea textArea2;
    private JButton genArchButton;
    private JFileChooser file = new JFileChooser();
    String archivo="";
    private Errors errors;
    private OutFile outFile = new OutFile();

    public String mostrarToken(int valor){

        if (valor == 0){
            return "EOF";
        }
        else if (valor == LexicalAnalyzer.ID){
            return "IDENTIFICADOR";
        }
        else if (valor == LexicalAnalyzer.LONGINT){
            return "ENTERO LARGO";
        }
        else if (valor == LexicalAnalyzer.FLOTANTE){
            return "FLOTANTE";
        }
        else if (valor == LexicalAnalyzer.CADENA_MULTINEA){
            return "CADENA DE CARACTER";
        }
        else if (valor == LexicalAnalyzer.ASIG){
            return "ASIGNACION";
        }
        else if (valor == LexicalAnalyzer.ID) {
            return "IDENTIFICADOR";
        }

        else if (valor ==LexicalAnalyzer.ELSE) {
            return "PALABRA RESERVADA ELSE";
        }

        else if (valor == LexicalAnalyzer.END_IF) {
            return "PALABRA RESERVADA END_IF";
        }
        else if (valor ==LexicalAnalyzer.IF) {
            return "PALABRA RESERVADA IF";
        }

        else if (valor == LexicalAnalyzer.LOOP) {
            return "PALABRA RESERVADA LOOP";
        }
        else if (valor == LexicalAnalyzer.UNTIL) {
            return "PALABRA RESERVADA UNTIL";
        }

        else if (valor == LexicalAnalyzer.MAYIG) {
            return "OPERADOR MAYOR IGUAL";
        }
        else if (valor == LexicalAnalyzer.MENIG) {
            return "OPERADOR MENOR IGUAL";
        }
        else if (valor == LexicalAnalyzer.DIST) {
            return "DISTINTO";
        }
        else if (valor == LexicalAnalyzer.FUNC) {
            return "PALABRA RESERVADA FUNC";
        }

        else if (valor < 255) {
            return "SIMBOLO "+(char)valor;
        }
        return "";
    }



    public App(/*String srcCode*/)  throws IOException {

       String srcCode ="srcCode"; /**Eliminar para compilar**/
       FileReader file = new FileReader(srcCode);
       BufferedReader src= new BufferedReader(file);
       String cadena;

        while ((cadena = src.readLine()) != null)
          archivo += cadena+"\n";
          archivo+=  "\n";
          archivo+=  "\n";
        archivo = archivo.substring(0,archivo.length()-1);
        errors = new Errors();
        final SymbolTable st = new SymbolTable();
        final LexicalAnalyzer lexical = new LexicalAnalyzer(archivo,st,errors);
        final Parser par = new Parser(lexical,st,errors);


       // Consumidor de Token
//        int token = lexical.getNextToken();
//        while (token!=-1 && token!=0){
//            System.out.println("Token encontrado: "+token);
//            token = lexical.getNextToken();
//        }

        System.out.println(errors.getAll());
        add(panel1);
        setSize(700,500);
        textArea2.append(archivo);// muestra archivo


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        genArchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                par.run();

                try{ Thread.sleep(100); } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }


                outFile.tlFile(st, "tablaSimbolos.txt");
                //outFile.tokenFile(par, "token.txt");
                //outFile.structFile(par, "estructurasReconocidas.txt");
                outFile.errorFiles(errors, "errores.txt");

            }
        });

    }
}
