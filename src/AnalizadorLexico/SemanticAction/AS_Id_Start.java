package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;


public class AS_Id_Start extends SemanticAction
{
    public AS_Id_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override

    public void Action(Character symbol) {
        lexical.tokenId = LexicalAnalyzer.ID;
        lexical.buffer+= symbol;

        lexical.index++;
        lexical.column++;
    }
}
