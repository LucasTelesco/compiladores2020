package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import Errors.Errors;

public class AS_Vaciar_Buffer extends SemanticAction{
    public AS_Vaciar_Buffer(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    public void Action(Character symbol) {
        lexical.index++;
        lexical.buffer = "";

    }
}
