package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Comentario_End  extends SemanticAction{


    public AS_Comentario_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }


    public void Action(Character symbol) {
        System.out.println("Se reconocio correctamente un comentario: "+ lexical.buffer);

        lexical.buffer = "";
        lexical.index++;
//        LEXICAL.COLUMN++;
        lexical.row++;
        lexical.column = 1;
    }

}
