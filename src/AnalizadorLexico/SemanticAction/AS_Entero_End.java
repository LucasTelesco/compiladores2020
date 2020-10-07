package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.*;

public class AS_Entero_End extends SemanticAction{

    public AS_Entero_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        //saca el _ para quedarse con solo el numero
        int num;
        if (lexical.buffer.length()>11){ //supero digitos el string
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_RANGE);
            num = lexical.MAX_INT_SIZE;
        }else {
           /* try{
                num = Integer.valueOf(lexical.buffer.substring(0, lexical.buffer.length() - 1));
            }
            catch (NumberFormatException e){

                    lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                    num = lexical.MAX_INT_SIZE;

            }*/
            num = Integer.valueOf(lexical.buffer.substring(0, lexical.buffer.length() - 1));
            if (num > lexical.MAX_INT_SIZE) {
                lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                num = lexical.MAX_INT_SIZE;
            }

        }

        /// ESTO VA PERO HAY QUE ACOMODARLO! PARA QUE SEA COMPATIBLE 2020
//        lexical.symbolTable.setSymbol(String.valueOf(num)+"_i"/*lexical.buffer+symbol*/, LexicalAnalyzer.ENTERO);
//        lexical.symbolTable.setAtributo(String.valueOf(num)+"_i"/*lexical.buffer+symbol*/,"=>","CTE ENTERO");
//        //PARA LOS IDENTIFICADORES DE ESTE TIPO EN LA GRAMATICA VA ESTO MISMO
//        lexical.symbolTable.getSymbol(String.valueOf(num)+"_i").setTipoVar("integer");
//        lexical.lastSymbol=String.valueOf(num)+"_i"; // no se usa mas

        //lexical.yylval.obj=lexical.symbolTable.getSymbol(String.valueOf(num)+"_i");

        lexical.buffer = "";
        lexical.column++;
        lexical.index++;
        lexical.tokenId = LexicalAnalyzer.CTELONGINT;
    }
}
