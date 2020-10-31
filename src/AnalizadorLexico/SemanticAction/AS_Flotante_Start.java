package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Flotante_Start extends SemanticAction {

    public AS_Flotante_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.tokenId = LexicalAnalyzer.CTEFLOAT;
        lexical.buffer+= symbol;
        lexical.index++;
    }
}