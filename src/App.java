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

    public App(String srcCode)  throws IOException {

       //String srcCode ="srcCode"; /**Eliminar para compilar**/
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
                outFile.errorFiles(errors, "errores.txt");
            }
        });

    }
}
