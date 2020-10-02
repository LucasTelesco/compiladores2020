package AnalizadorLexico;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import AnalizadorLexico.SemanticAction.*;
import AnalizadorLexico.StateMachine.StateMachine;
import Errors.*;
import SymbolTable.*;

public class LexicalAnalyzer {




    //public static ParserVal yylval = null;
    //############  puntero a la tabla de simbolos? #######

    public String lastSymbol;
    public final int MAX_WORD_SIZE = 25;
    public final int MIN_INT_SIZE = 0; // -10 .. 9 pero no puedo reconocer -10 -9
    //por lo que verifico del 0..10 y el sintactico se va a ocupar del 10, ya que la unica
    //forma que venga 10 es siendo negativo. Por que si viene un -9 negativo lo va a aceptar asi que es
    //como verificar el 9 positivo
    public final int MAX_INT_SIZE = (int)Math.pow(2,15);
    public final float MIN_FLOAT_SIZE = (float) Math.pow(1.17549435,-38);
    public final float MAX_FLOAT_SIZE = (float) Math.pow(3.40282347,38);

    public final static short IF=257;
    public final static short ELSE=258;
    public final static short PRINT=259;
    public final static short INTEGER=260;
    public final static short ID=261;
    public final static short CTE=262;
    public final static short CADENA_MULTINEA=263;
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
    public final static short THEN=277;
    public final static short OUT=278;
    public final static short FUNC=279;
    public final static short RETURN=280;
    public final static short LONGINT=281;
    public final static short FLOAT=282;

    public final static short YYERRCODE=256;
    public final static short COMENTARIO=999;

    public String srcCode;
    // nos sirve para decir en donde ocurre un error
    public int row; //controla cada \n del string
    public int column;
    public SymbolTable symbolTable;
    public String buffer;
    public int state;
    public int index; //cursor para seguir el string que viene de forma lineal _a = 6; \n if..
    public int tokenId;
    public Errors errors;
    public Hashtable<String,Integer> reservedWords;
    private void create(){

        //
        SemanticAction tokenAscii = new AS_TokenAscii(this);
        SemanticAction asignacion_Comparacion = new AS_Asignacion_Comparacion(this);
        SemanticAction comp_error = new AS_Comparador_Error(this);
        SemanticAction next = new AS_Next(this);//avanza y guarda en el buffer
        SemanticAction next_line= new AS_NextLine(this);//solo avanza fila
        SemanticAction next_espace = new AS_NextSpace(this);//solo avanza columna
        SemanticAction comentario = new AS_Vaciar_Buffer(this);
        SemanticAction entero_end = new AS_Entero_End(this);
        SemanticAction entero_start = new AS_Entero_Start(this);
        SemanticAction flotante_end = new AS_Flotante_End(this);
        SemanticAction flotante_start = new AS_Flotante_Start(this);
        SemanticAction cadena_start = new AS_Cadena_Start(this);
        SemanticAction cadena_end = new AS_Cadena_End(this);
        SemanticAction next_line_cadena = new AS_NextLineCadena(this);
        SemanticAction id_end = new AS_Id_End(this);
        SemanticAction id_start = new AS_Id_Start(this);
        SemanticAction not_lexema = new AS_ErrorNotLexema(this);
        SemanticAction palabra_reservada = new AS_Palabra_Reservada(this);

        SemanticAction numero_start = new AS_Numero_Start(this);
        SemanticAction comentario_start = new AS_Comentario_Start(this);
        SemanticAction comentario_end = new AS_Comentario_End(this);








        StateMachine.addTransition( 0, 'l',  1, id_start);
        StateMachine.addTransition( 0, 'd',6, numero_start);
        StateMachine.addTransition( 0, '+', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '-', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '*', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '/', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '<', 2,next );
        StateMachine.addTransition( 0, '>', 2,next );
        StateMachine.addTransition( 0, '=', 2,next );
        StateMachine.addTransition( 0, '!', 3,next );
        StateMachine.addTransition( 0, '{', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '}', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '(', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, ')', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, ',', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, ';', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 0, '%', 4,next );
        StateMachine.addTransition( 0, '\n', 0, next_line );
        StateMachine.addTransition( 0, '.', 8, flotante_start );
        StateMachine.addTransition( 0, '_', StateMachine.ERROR_STATE,not_lexema);
        StateMachine.addTransition( 0, 'f', 1, id_start );
        StateMachine.addTransition( 0, '"', 12, cadena_start);
        StateMachine.addTransition( 0, 'C', StateMachine.ERROR_STATE,not_lexema);
        StateMachine.addTransition(0,'a',1, id_start); //minuscula
        StateMachine.addTransition(0,'A',15, next); //mayuscula
        StateMachine.addTransition( 0, ' ', 0, next_espace);


        StateMachine.addTransition( 1, 'l',  1, next);
        StateMachine.addTransition( 1, 'd',1, next);
        StateMachine.addTransition( 1, '+', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '-', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '*', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '/', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '<', StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition( 1, '>', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '=', StateMachine.FINAL_STATE,id_end  );
        StateMachine.addTransition( 1, '!', StateMachine.FINAL_STATE,id_end  );
        StateMachine.addTransition( 1, '{', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '}', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '(', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, ')', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, ',', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, ';', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '%', StateMachine.FINAL_STATE,id_end  );
        StateMachine.addTransition( 1, '\n', StateMachine.FINAL_STATE,id_end  );
        StateMachine.addTransition( 1, '.', StateMachine.FINAL_STATE,id_end );
        StateMachine.addTransition( 1, '_', 1,next);
        StateMachine.addTransition( 1, 'f', 1, next );
        StateMachine.addTransition( 1, '"', StateMachine.FINAL_STATE, id_end);
        StateMachine.addTransition( 1, 'C', StateMachine.ERROR_STATE,id_end);
        StateMachine.addTransition(1,'a',1, next);
        StateMachine.addTransition(1,'A', StateMachine.FINAL_STATE, id_end);
        StateMachine.addTransition( 1, ' ', StateMachine.FINAL_STATE, next_espace);


        StateMachine.addTransition( 2, 'l',  StateMachine.FINAL_STATE, tokenAscii);
        StateMachine.addTransition( 2, 'd',StateMachine.FINAL_STATE, tokenAscii);
        StateMachine.addTransition( 2, '+', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '-', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '*', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '/', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '<', StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition( 2, '>', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '=', StateMachine.FINAL_STATE,asignacion_Comparacion  );
        StateMachine.addTransition( 2, '!', StateMachine.FINAL_STATE,tokenAscii  );
        StateMachine.addTransition( 2, '{', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '}', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '(', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, ')', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, ',', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, ';', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '%', StateMachine.FINAL_STATE,tokenAscii  );
        StateMachine.addTransition( 2, '\n', StateMachine.FINAL_STATE,tokenAscii  );
        StateMachine.addTransition( 2, '.', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '_', StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition( 2, 'f', StateMachine.FINAL_STATE,tokenAscii );
        StateMachine.addTransition( 2, '"', StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition( 2, 'C', StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(2,'a',StateMachine.FINAL_STATE, tokenAscii);
        StateMachine.addTransition(2,'A',StateMachine.FINAL_STATE, tokenAscii);
        StateMachine.addTransition( 2, ' ', StateMachine.FINAL_STATE,tokenAscii);


        StateMachine.addTransition( 3, 'l', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, 'd', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '+', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '-', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '*', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '/', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '<', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '>', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '=', StateMachine.FINAL_STATE,asignacion_Comparacion  );
        StateMachine.addTransition( 3, '!', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '{', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '}', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '(', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, ')', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, ',', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, ';', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '%', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, '\n', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 3, '.', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 3, '_', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, 'f', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 3, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, 'C', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(3,'a',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(3,'A',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 3, ' ', StateMachine.ERROR_STATE, not_lexema);


        StateMachine.addTransition( 4, 'l', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, 'd', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '+', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '-', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '*', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '/', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '<', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '>', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '=', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '!', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '{', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '}', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '(', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, ')', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, ',', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, ';', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, '%', 5, comentario_start);
        StateMachine.addTransition( 4, '\n', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 4, '.', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 4, '_', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, 'f', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 4, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, 'C', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(4,'a',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(4,'A',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 4, ' ', StateMachine.ERROR_STATE, not_lexema);


        StateMachine.addTransition( 5, 'l', 5, next);
        StateMachine.addTransition( 5, 'd', 5, next);
        StateMachine.addTransition( 5, '+', 5, next);
        StateMachine.addTransition( 5, '-', 5, next);
        StateMachine.addTransition( 5, '*', 5, next);
        StateMachine.addTransition( 5, '/', 5, next);
        StateMachine.addTransition( 5, '<', 5, next);
        StateMachine.addTransition( 5, '>', 5, next);
        StateMachine.addTransition( 5, '=', 5, next);
        StateMachine.addTransition( 5, '!', 5, next);
        StateMachine.addTransition( 5, '{', 5, next);
        StateMachine.addTransition( 5, '}', 5, next);
        StateMachine.addTransition( 5, '(', 5, next);
        StateMachine.addTransition( 5, ')', 5, next);
        StateMachine.addTransition( 5, ',', 5, next);
        StateMachine.addTransition( 5, ';', 5, next);
        StateMachine.addTransition( 5, '%', 5, next);
        StateMachine.addTransition( 5, '\n', StateMachine.FINAL_STATE, comentario_end );
        StateMachine.addTransition( 5, '.', 5, next );
        StateMachine.addTransition( 5, '_', 5, next);
        StateMachine.addTransition( 5, 'f', 5, next);
        StateMachine.addTransition( 5, '"', 5, next);
        StateMachine.addTransition( 5, 'C', 5, next);
        StateMachine.addTransition(5,'a',5, next);
        StateMachine.addTransition(5,'A',5, next);
        StateMachine.addTransition( 5, ' ', 5, next);



        StateMachine.addTransition( 6, 'l', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, 'd', 6, next);
        StateMachine.addTransition( 6, '+', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '-', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '*', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '/', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '<', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '>', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '=', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '!', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '{', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '}', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '(', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, ')', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, ',', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, ';', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '%', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, '\n', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 6, '.', 8, next );
        StateMachine.addTransition( 6, '_', 7, next);
        StateMachine.addTransition( 6, 'f', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 6, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, 'C', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(6,'a',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(6,'A',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 6, ' ', StateMachine.ERROR_STATE, not_lexema);


        StateMachine.addTransition( 7, 'l', StateMachine.FINAL_STATE, entero_end);
        StateMachine.addTransition( 7, 'd', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '+', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '-', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '*', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '/', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '<', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '>', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '=', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '!', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '{', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '}', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '(', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, ')', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, ',', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, ';', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '%', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, '\n', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 7, '.', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 7, '_', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 7, 'f', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 7, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, 'C', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(7,'a',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(7,'A',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 7, ' ', StateMachine.ERROR_STATE, not_lexema);


        StateMachine.addTransition( 8, 'l',  StateMachine.FINAL_STATE, flotante_end);
        StateMachine.addTransition( 8, 'd', 8, next);
        StateMachine.addTransition( 8, '+', StateMachine.FINAL_STATE, flotante_end );
        StateMachine.addTransition( 8, '-', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '*', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '/', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '<', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition( 8, '>', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '=', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 8, '!', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 8, '{', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '}', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '(', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, ')', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, ',', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, ';', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '%', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 8, '\n', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 8, '.', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 8, '_', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition( 8, 'f',9, next );
        StateMachine.addTransition( 8, '"', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition( 8, 'C', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition(8,'a',StateMachine.FINAL_STATE, flotante_end);
        StateMachine.addTransition(8,'A',StateMachine.FINAL_STATE, flotante_end);
        StateMachine.addTransition( 8, ' ', StateMachine.FINAL_STATE,flotante_end);


        StateMachine.addTransition( 9, 'l', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, 'd', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '+', 10, next);
        StateMachine.addTransition( 9, '-', 10, next);
        StateMachine.addTransition( 9, '*', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '/', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '<', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '>', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '=', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '!', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '{', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '}', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '(', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, ')', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, ',', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, ';', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '%', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, '\n', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 9, '.', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 9, '_', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 9, 'f', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 9, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, 'C', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(9,'a',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(9,'A',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 9, ' ', StateMachine.ERROR_STATE, not_lexema);


//en la accion semantica de cualquier simbolo que no sea = contemplamos la historia (<>) o (!:)
//si tiene que saltar un error o devolver un token, porque vaya al final o al error
//la accion semantica  es la que decide
        StateMachine.addTransition( 10, 'l', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, 'd', 11, next);
        StateMachine.addTransition( 10, '+', 10, next);
        StateMachine.addTransition( 10, '-', 10, next);
        StateMachine.addTransition( 10, '*', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '/', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '<', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '>', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '=', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '!', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '{', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '}', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '(', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, ')', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, ',', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, ';', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '%', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, '\n', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 10, '.', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 10, '_', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 10, 'f', StateMachine.ERROR_STATE, not_lexema );
        StateMachine.addTransition( 10, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, 'C', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(10,'a',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition(10,'A',StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 10, ' ', StateMachine.ERROR_STATE, not_lexema);





        StateMachine.addTransition( 11, 'l',  StateMachine.FINAL_STATE, flotante_end);
        StateMachine.addTransition( 11, 'd', 11, next);
        StateMachine.addTransition( 11, '+', StateMachine.FINAL_STATE, flotante_end );
        StateMachine.addTransition( 11, '-', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '*', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '/', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '<', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition( 11, '>', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '=', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 11, '!', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 11, '{', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '}', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '(', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, ')', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, ',', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, ';', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '%', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 11, '\n', StateMachine.FINAL_STATE,flotante_end  );
        StateMachine.addTransition( 11, '.', StateMachine.FINAL_STATE,flotante_end );
        StateMachine.addTransition( 11, '_', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition( 11, 'f',9, next );
        StateMachine.addTransition( 11, '"', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition( 11, 'C', StateMachine.FINAL_STATE,flotante_end);
        StateMachine.addTransition(11,'a',StateMachine.FINAL_STATE, flotante_end);
        StateMachine.addTransition(11,'A',StateMachine.FINAL_STATE, flotante_end);
        StateMachine.addTransition( 11, ' ', StateMachine.FINAL_STATE,flotante_end);



        StateMachine.addTransition( 12, 'l', 12, next);
        StateMachine.addTransition( 12, 'd', 12, next);
        StateMachine.addTransition( 12, '+', 12, next);
        StateMachine.addTransition( 12, '-', 13, next);
        StateMachine.addTransition( 12, '*', 12, next);
        StateMachine.addTransition( 12, '/', 12, next);
        StateMachine.addTransition( 12, '<', 12, next);
        StateMachine.addTransition( 12, '>', 12, next);
        StateMachine.addTransition( 12, '=', 12, next);
        StateMachine.addTransition( 12, '!', 12, next);
        StateMachine.addTransition( 12, '{', 12, next);
        StateMachine.addTransition( 12, '}', 12, next);
        StateMachine.addTransition( 12, '(', 12, next);
        StateMachine.addTransition( 12, ')', 12, next);
        StateMachine.addTransition( 12, ',', 12, next);
        StateMachine.addTransition( 12, ';', 12, next);
        StateMachine.addTransition( 12, '%', 12, next);
        StateMachine.addTransition( 12, '\n', 12, next_line_cadena );
        StateMachine.addTransition( 12, '.', 12, next );
        StateMachine.addTransition( 12, '_', 12, next);
        StateMachine.addTransition( 12, 'f', 12, next);
        StateMachine.addTransition( 12, '"', StateMachine.FINAL_STATE, cadena_end);
        StateMachine.addTransition( 12, 'C', 12, next);
        StateMachine.addTransition(12,'a',12, next);
        StateMachine.addTransition(12,'A',12, next);
        StateMachine.addTransition( 12, ' ', 12, next);


        StateMachine.addTransition( 13, 'l', 12, next);
        StateMachine.addTransition( 13, 'd', 12, next);
        StateMachine.addTransition( 13, '+', 12, next);
        StateMachine.addTransition( 13, '-', 13, next);
        StateMachine.addTransition( 13, '*', 12, next);
        StateMachine.addTransition( 13, '/', 12, next);
        StateMachine.addTransition( 13, '<', 12, next);
        StateMachine.addTransition( 13, '>', 12, next);
        StateMachine.addTransition( 13, '=', 12, next);
        StateMachine.addTransition( 13, '!', 12, next);
        StateMachine.addTransition( 13, '{', 12, next);
        StateMachine.addTransition( 13, '}', 12, next);
        StateMachine.addTransition( 13, '(', 12, next);
        StateMachine.addTransition( 13, ')', 12, next);
        StateMachine.addTransition( 13, ',', 12, next);
        StateMachine.addTransition( 13, ';', 12, next);
        StateMachine.addTransition( 13, '%', 12, next);
        StateMachine.addTransition( 13, '\n', 14, next_line_cadena );
        StateMachine.addTransition( 13, '.', 12, next );
        StateMachine.addTransition( 13, '_', 12, next);
        StateMachine.addTransition( 13, 'f', 12, next);
        StateMachine.addTransition( 13, '"', StateMachine.ERROR_STATE, not_lexema);
        StateMachine.addTransition( 13, 'C', 12, next);
        StateMachine.addTransition(13,'a',12, next);
        StateMachine.addTransition(13,'A',12, next);
        StateMachine.addTransition( 13, ' ', 12, next);

        StateMachine.addTransition( 14, 'l', 12, next);
        StateMachine.addTransition( 14, 'd', 12, next);
        StateMachine.addTransition( 14, '+', 12, next);
        StateMachine.addTransition( 14, '-', 12, next);
        StateMachine.addTransition( 14, '*', 12, next);
        StateMachine.addTransition( 14, '/', 12, next);
        StateMachine.addTransition( 14, '<', 12, next);
        StateMachine.addTransition( 14, '>', 12, next);
        StateMachine.addTransition( 14, '=', 12, next);
        StateMachine.addTransition( 14, '!', 12, next);
        StateMachine.addTransition( 14, '{', 12, next);
        StateMachine.addTransition( 14, '}', 12, next);
        StateMachine.addTransition( 14, '(', 12, next);
        StateMachine.addTransition( 14, ')', 12, next);
        StateMachine.addTransition( 14, ',', 12, next);
        StateMachine.addTransition( 14, ';', 12, next);
        StateMachine.addTransition( 14, '%', 12, next);
        StateMachine.addTransition( 14, '\n', 12, next_line_cadena );
        StateMachine.addTransition( 14, '.', 12, next );
        StateMachine.addTransition( 14, '_', 12, next);
        StateMachine.addTransition( 14, 'f', 12, next);
        StateMachine.addTransition( 14, '"', StateMachine.FINAL_STATE, cadena_end);
        StateMachine.addTransition( 14, 'C', 12, next);
        StateMachine.addTransition(14,'a',12, next);
        StateMachine.addTransition(14,'A',12, next);
        StateMachine.addTransition( 14, ' ', 12, next);


        StateMachine.addTransition( 15, 'l', StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, 'd',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '+',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '-',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '*',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '/',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '<',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '>',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '=',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '!',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '{',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '}',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '(',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, ')',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, ',',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, ';',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '%',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '\n',  StateMachine.FINAL_STATE, palabra_reservada );
        StateMachine.addTransition( 15, '.',  StateMachine.FINAL_STATE, palabra_reservada );
        StateMachine.addTransition( 15, '_', 15, next);
        StateMachine.addTransition( 15, 'f',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, '"',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition( 15, 'C',  StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition(15,'a',StateMachine.FINAL_STATE, palabra_reservada);
        StateMachine.addTransition(15,'A',15, next);
        StateMachine.addTransition( 15, ' ',  StateMachine.FINAL_STATE, palabra_reservada);
    }
    private void addReservedWord() {
        reservedWords.put("if", (int) IF);
        reservedWords.put("then", (int) THEN);
        reservedWords.put("else", (int) ELSE);
        reservedWords.put("end_if", (int) END_IF);
        reservedWords.put("out", (int) OUT);
        reservedWords.put("func", (int) FUNC);
        reservedWords.put("return", (int) RETURN);
        reservedWords.put("loop", (int) LOOP);
        reservedWords.put("until", (int) UNTIL);
        reservedWords.put("longint", (int) LONGINT);
        reservedWords.put("longint", (int) FLOAT);
    }

        public int getRow () {
            return row;
        }

        public int getColumn () {
            return column;
        }

    public LexicalAnalyzer(String srcCode, SymbolTable symbolTable, Errors errors) throws
        FileNotFoundException, IOException {
            this.symbolTable = symbolTable;
            row = 1;
            column = 1;
            state = StateMachine.INITIAL_STATE;
            index = 0;
            buffer = "";
            this.srcCode = srcCode;
            this.errors = errors;
            create();
            reservedWords = new Hashtable<String, Integer>();
            addReservedWord();

        }

        public boolean isReservedWord (String lexema){
            //completar
            if (lexema == "loop")
                return true;
            return false;
        }
        public int getNextToken () {
            tokenId = -1;
            state = StateMachine.INITIAL_STATE;
            Character symbol;

           // yylval = new ParserVal();
          //  yylval.setColumna(column);
          //  yylval.setFila(row);

            while (state != StateMachine.FINAL_STATE) {
                if (index >= srcCode.length()) {
                    System.out.println("el token es cero");
                    return 0;
                }
                symbol = srcCode.charAt(index);
                int old = state;
                state = StateMachine.getNextState(state, symbol);
                StateMachine.getSemanticAction(old, symbol).Action(symbol);

                if (state == StateMachine.INITIAL_STATE) {
                  //  yylval.setColumna(column);
                 //   yylval.setFila(row);
                }

            }


            return tokenId;
        }

    }

