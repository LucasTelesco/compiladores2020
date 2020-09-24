import AnalizadorLexico.LexicalAnalyzer;
import SymbolTable.SymbolTable;

import javax.swing.text.html.parser.Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public final static short IF=257;
    public final static short ELSE=258;
    public final static short PRINT=259;
    public final static short INTEGER=260;
    public final static short ID=261;
    public final static short CTE=262;
    public final static short CADENA=263;
    public final static short ASIG=264;
    public final static short MAYIG=265;
    public final static short MENIG=266;
    public final static short DIST=267;
    public final static short FIN=268;
    public final static short SINGLE=269;
    public final static short END_IF=270;
    public final static short LOOP=271;
    public final static short UNTIL=272;
    public final static short LET=273;
    public final static short MUT=274;
    public final static short ENTERO=275;
    public final static short FLOTANTE=276;
    public final static short YYERRCODE=256;




    public static void main(String Args[]) {

        App a = null;
        try {
            a = new App(/*Args[0]*/); // Eliminar
            a.setVisible(true);
        } catch (IOException e) {
            System.out.println("No se puede crear ejecutable, revise estructura de errores ");
        }


    }
}
