package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Cadena_Start extends SemanticAction{

    public AS_Cadena_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.tokenId = LexicalAnalyzer.CADENA_MULTINEA;
        lexical.buffer+= symbol;

        lexical.index++;
    }
}