package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.*;

public class AS_Entero_End extends SemanticAction{

    public AS_Entero_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        int num;
        if (lexical.buffer.length()>11){
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_RANGE);
            num = lexical.MAX_INT_SIZE;
        }else {
            num = Integer.valueOf(lexical.buffer.substring(0, lexical.buffer.length() - 1));
            if (num > lexical.MAX_INT_SIZE) {
                lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                num = lexical.MAX_INT_SIZE;
            }

        }

        lexical.buffer = "";
        lexical.column++;
        lexical.index++;
        lexical.tokenId = LexicalAnalyzer.CTELONGINT;
    }
}
