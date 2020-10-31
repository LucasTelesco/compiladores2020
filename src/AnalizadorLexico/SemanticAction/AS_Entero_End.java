package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.*;
import SymbolTable.Symbol;

public class AS_Entero_End extends SemanticAction{

    public AS_Entero_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        long num;
        try{
            num = Long.valueOf(lexical.buffer.substring(0, lexical.buffer.length() - 1));
        }
        catch (NumberFormatException e) {
            num = lexical.MAX_INT_SIZE;
        }

        lexical.yylval.obj= new Symbol(String.valueOf(num)+"_l", LexicalAnalyzer.CTELONGINT);

        lexical.buffer = "";
        lexical.index++;
        lexical.tokenId = LexicalAnalyzer.CTELONGINT;
    }
}
