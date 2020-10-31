package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.Symbol;

public class AS_Flotante_End extends SemanticAction{

    public AS_Flotante_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    //deja el tipo asignado en Start
    @Override
    public void Action(Character symbol) {
        
        if (lexical.buffer.contains("f")) {
            lexical.buffer = lexical.buffer.replace('f', 'e');
        }
            if (lexical.buffer.charAt(0) == '.' ){
                lexical.buffer = "0" + lexical.buffer;
            }

            float num = Float.valueOf(lexical.buffer);
            lexical.buffer = "";
            lexical.tokenId = LexicalAnalyzer.CTEFLOAT;
            lexical.yylval.obj= new Symbol(String.valueOf(num), LexicalAnalyzer.CTEFLOAT);
    }
}
