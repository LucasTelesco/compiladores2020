package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Asignacion_Comparacion extends SemanticAction {

    public AS_Asignacion_Comparacion(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {

        if(symbol.equals('=')) {
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

            }
            lexical.index++; // avanzo el cursor porque use el caracter
        }

        else{
            lexical.tokenId = (int) lexical.buffer.charAt(lexical.buffer.length() - 1);
        }

        lexical.buffer = "";
    }
}
