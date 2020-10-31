package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Entero_Start extends SemanticAction{

    public AS_Entero_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.tokenId = LexicalAnalyzer.CTELONGINT;
        lexical.buffer+= symbol;// no agrega el '
        lexical.index++;
    }
}
