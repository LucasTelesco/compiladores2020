package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.*;

public class AS_Id_End extends SemanticAction{

    public AS_Id_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    @Override
    public void Action(Character symbol) {
        if (lexical.buffer.length() > lexical.MAX_WORD_SIZE){
            lexical.errors.setError(lexical.row,Errors.ERROR_MAX_WORD_SIZE);
            lexical.buffer = lexical.buffer.substring(0,lexical.MAX_WORD_SIZE);
            //siempre acota a 25 caracteres
        }

        lexical.symbolTable.setSymbol(lexical.buffer, LexicalAnalyzer.ID);
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","IDENTIFICADOR");

        lexical.lastSymbol=lexical.buffer; // guardo el simbolo viejo
        lexical.buffer = "";
    }
}
