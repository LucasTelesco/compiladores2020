package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Comentario_Start extends SemanticAction {

    public AS_Comentario_Start(LexicalAnalyzer lexicalAnalyzer){
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        lexical.tokenId = LexicalAnalyzer.COMENTARIO;
        //lexical.buffer+= symbol;

        lexical.index++;
        lexical.column++;
    }
}
