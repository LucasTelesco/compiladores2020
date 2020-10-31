package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Comentario_End  extends SemanticAction{


    public AS_Comentario_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }


    public void Action(Character symbol) {
        lexical.buffer = "";
        lexical.index++;
        lexical.row++;
    }

}
