package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.*;

public class AS_Id_End extends SemanticAction{

    public AS_Id_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    //deja el tipo asignado en Start
    @Override
    public void Action(Character symbol) {
        if (lexical.buffer.length() > lexical.MAX_WORD_SIZE){
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_MAX_WORD_SIZE);
            lexical.buffer = lexical.buffer.substring(0,lexical.MAX_WORD_SIZE);

        }

        lexical.symbolTable.setSymbol(lexical.buffer, LexicalAnalyzer.ID);
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","IDENTIFICADOR");

        lexical.lastSymbol=lexical.buffer;
        lexical.buffer = "";
    }
}
