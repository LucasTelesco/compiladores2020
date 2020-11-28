package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import SymbolTable.Symbol;

public class AS_Cadena_End extends SemanticAction{

    public AS_Cadena_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.symbolTable.setSymbol(lexical.buffer, LexicalAnalyzer.CADENA_MULTINEA);
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","CADENA");
        lexical.lastSymbol=lexical.buffer; // guardo el simbolo viejo

        lexical.symbolTable.getSymbol(lexical.buffer).setTipoVar("cadena");
        lexical.symbolTable.getSymbol(lexical.buffer).setLexema(lexical.buffer);

        Symbol symbol2 = new Symbol(lexical.buffer, LexicalAnalyzer.CADENA_MULTINEA);

        lexical.yylval.obj= symbol2;

        lexical.buffer = "";
        lexical.index++;
    }
}