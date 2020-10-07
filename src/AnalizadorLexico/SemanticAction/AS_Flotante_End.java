package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;

public class AS_Flotante_End extends SemanticAction{

    public AS_Flotante_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    @Override
    public void Action(Character symbol) {


        if (lexical.buffer.contains("f")) {
            lexical.buffer = lexical.buffer.replace('f', 'e');
        }
            if (lexical.buffer.charAt(0) == '.' ){
                lexical.buffer = "0" + lexical.buffer;
            }

            float num = Float.valueOf(lexical.buffer);


            if (num != 0.0) {
                if (num < lexical.MIN_FLOAT_SIZE) {
                    lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                    num = lexical.MIN_FLOAT_SIZE;
                } else if (num > lexical.MAX_FLOAT_SIZE) {
                    lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                    num = lexical.MAX_FLOAT_SIZE;
                }
            }
            lexical.buffer = "";
            lexical.tokenId = LexicalAnalyzer.CTEFLOAT;

    }
}
