package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import Errors.Errors;

public class AS_Comparador_Error extends SemanticAction {

    public AS_Comparador_Error(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {

        switch (lexical.buffer) {
            case "<":
                lexical.tokenId = (int)'<';
                lexical.state=StateMachine.FINAL_STATE;
                break;
            case ">":
                lexical.tokenId = (int)'>';
                lexical.state=StateMachine.FINAL_STATE;
                break;
            default:
                String e= Errors.ERROR_FAIL_CHARACTER+" "+lexical.buffer;
                lexical.errors.setError(lexical.row,e);

        }
        lexical.buffer="";
    }
}