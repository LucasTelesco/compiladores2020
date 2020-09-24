package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import AnalizadorSintactico.Parser;
import Errors.Errors;

public class AS_NextLineCadena extends SemanticAction {


    public AS_NextLineCadena(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        /**
         * Lo podemos usar cuando aparece un salto de linea en una cadena multilinea
         * **/
        lexical.index++;
        lexical.row++;
        lexical.column = 1;

        int posUltimoCaracter=lexical.buffer.length()-1;
        char guion = lexical.buffer.charAt(posUltimoCaracter);
        if (guion=='-')
            //Lo que hace es descartar el guion pq no lo quiero en la tabla de simbolos
            lexical.buffer=lexical.buffer.substring(0,posUltimoCaracter);
        else{
            String e= Errors.ERROR_FAIL_CHARACTER+" falto un guion para la cadena de caracteres";
            lexical.errors.setError(lexical.row,lexical.column,e);
            lexical.state= StateMachine.ERROR_STATE;
            lexical.tokenId = -1;
            //piso estado de cadenas por estado de error???? o sigo como si  nada con warning
            // Hay qe informar el error y continuo
        }

    }
}
