package AnalizadorLexico.StateMachine;
import AnalizadorLexico.SemanticAction.SemanticAction;

import java.util.Hashtable;

import static java.lang.Integer.MAX_VALUE;

public final class StateMachine {
    public static final int FINAL_STATE = MAX_VALUE;
    public static final int INITIAL_STATE = 0;
    public static final int ERROR_STATE = 0;
    private static Hashtable<Integer, Hashtable<Character, Dupla>> transitionMatrix= new Hashtable<>();

    private static Character convert(Character c){
        /*este metodo mapea cualquier letra a una 'a'
        //y cualquier numero a '1'
        para tener la matriz de transicion con menos columnas*/
        int asciiChar = (int )c;

        if (asciiChar==11 || asciiChar==9)
            return ' ';

        if ((asciiChar>=97 && asciiChar<=100)||(asciiChar==101)||(asciiChar>=103 && asciiChar<=107)||(asciiChar>=109 && asciiChar<=122)){
            //{universo de letras minusculas} - {l} -{f} -{d}
            return 'a';
        }
        if ((asciiChar>=65 && asciiChar<=66)||(asciiChar>=68 && asciiChar<=90)){
            //{universo de letras mayusculas} - {C}
            return 'A';
        }
        if (asciiChar>= 48 && asciiChar<=57){
            //numero de 0-9
            return 'd';
        }

        if ((asciiChar==42)||(asciiChar==47)||(asciiChar==45)||(asciiChar==62)||(asciiChar==61)||(asciiChar==33)
                ||(asciiChar==123)||(asciiChar==125)||(asciiChar==40)||(asciiChar==41)||(asciiChar==44)||(asciiChar==59)
                ||(asciiChar==37)||(asciiChar==46)||(asciiChar==95)||(asciiChar==34)||(asciiChar==32)
                ||(asciiChar==108)||(asciiChar==100)||(asciiChar==102)||(asciiChar==10)){
            //simbolos unitarios ascii
            return c;
        }
        return 'C';
    }

    public static void addTransition(Integer state, Character symbol, Integer nextState, SemanticAction semanticAction){
        Dupla pair = new Dupla(nextState, semanticAction);

        if (transitionMatrix.containsKey(state)) {
            transitionMatrix.get(state).put(symbol,pair);
        }else{
            transitionMatrix.put(state,new Hashtable<Character, Dupla>());
            transitionMatrix.get(state).put(symbol,pair);
        }

    }

    public static Dupla getPair(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol);
    }

    public static SemanticAction getSemanticAction(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(convert(symbol)).getSemanticActions();
    }

    public static Integer getNextState(Integer state, Character symbol){

        return  (transitionMatrix.get(state)).get(convert(symbol)).getState();
    }

}
