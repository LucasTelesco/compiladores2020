package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;

public class AS_Flotante_End extends SemanticAction{

    public AS_Flotante_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    //deja el tipo asignado en Start
    @Override
    public void Action(Character symbol) {

        /*if (lexical.buffer.length() == 1 && lexical.buffer.contains(".")){
            lexical.buffer = "";
            lexical.tokenId = 250;
        }*/


        if (lexical.buffer.contains("f")) {
            lexical.buffer = lexical.buffer.replace('f', 'e');
        }
            if (lexical.buffer.charAt(0) == '.' ){
                lexical.buffer = "0" + lexical.buffer;
            }

            float num = Float.valueOf(lexical.buffer);


            if (num != 0.0) {
                if (num < lexical.MIN_FLOAT_SIZE) {
                    lexical.errors.setError(lexical.row,  Errors.ERROR_RANGE);
                    num = lexical.MIN_FLOAT_SIZE;
                } else if (num > lexical.MAX_FLOAT_SIZE) {
                    lexical.errors.setError(lexical.row,  Errors.ERROR_RANGE);
                    num = lexical.MAX_FLOAT_SIZE;
                }
            }
            lexical.buffer = "";
            lexical.tokenId = LexicalAnalyzer.CTEFLOAT;

          lexical.symbolTable.setSymbol(String.valueOf(num), LexicalAnalyzer.FLOTANTE);
          lexical.symbolTable.setAtributo(String.valueOf(num),"=>","CTE FLOTANTE");
        // ver que onda, convertir a 2020
//        lexical.symbolTable.setSymbol(String.valueOf(num), LexicalAnalyzer.FLOTANTE);
//        lexical.symbolTable.setAtributo(String.valueOf(num),"=>","CTE FLOTANTE");
//        //lexical.yylval.obj=lexical.symbolTable.getSymbol(String.valueOf(num));
//
//        //PARA LOS IDENTIFICADORES DE ESTE TIPO EN LA GRAMATICA VA ESTO MISMO
//        lexical.symbolTable.getSymbol(String.valueOf(num)).setTipoVar("single");




    }
}
