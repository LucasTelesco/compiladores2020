package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Asignacion_Comparacion extends SemanticAction {

    public AS_Asignacion_Comparacion(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        //lexical.yylval.sval=lexical.buffer+"=";
        switch (lexical.buffer) {
            case "!":
                lexical.tokenId = LexicalAnalyzer.DIST;
                break;
            case "<":
                lexical.tokenId = LexicalAnalyzer.MENIG;
                break;
            case ">":
                lexical.tokenId = LexicalAnalyzer.MAYIG;
                break;
            case "=":
                lexical.tokenId = LexicalAnalyzer.ESIGUAL;
                break;
            default:
                lexical.tokenId = (int)lexical.buffer.charAt(lexical.buffer.length()-1);
                break;
        }

        lexical.index++; // avanzo el cursor porque use el caracter
        lexical.column++;

        lexical.buffer = "";
}
}
