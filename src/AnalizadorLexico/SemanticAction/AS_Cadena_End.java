package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Cadena_End extends SemanticAction{

    public AS_Cadena_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.symbolTable.setSymbol(lexical.buffer, LexicalAnalyzer.CADENA_MULTINEA);
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","CADENA");
        lexical.lastSymbol=lexical.buffer; // guardo el simbolo viejo

        lexical.symbolTable.getSymbol(lexical.buffer).setTipoVar("cadena");
        lexical.symbolTable.getSymbol(lexical.buffer).setLexema(lexical.buffer);

        lexical.buffer = "";
        lexical.index++;
        lexical.column++;
    }
}