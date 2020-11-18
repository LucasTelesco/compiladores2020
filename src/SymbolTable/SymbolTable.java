package SymbolTable;


import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class SymbolTable {

    private Hashtable<String, Symbol> tb;
    private Queue<String> productor;
    public SymbolTable(){
        tb = new Hashtable<>();
        productor = new LinkedList<>();
    }

    public Symbol getSymbol(String key){
        return tb.get(key);
    }


    public boolean existsSymbol(String lexema){
        return tb.containsKey(lexema);
    }

    public String getAtributosNextLexema(){
       return tb.get(productor.remove()).getAtributos();
    }
    public boolean isEmpty(){
        return productor.isEmpty();

    }
    public void setSymbol(String lexema, int number) {
        if (!tb.containsKey(lexema)){
            Symbol token = new Symbol(lexema, number);
            tb.put(lexema, token);
            productor.add(lexema);
        }
    }
    public void setSymbol(Symbol aux) {
        if (!tb.containsKey(aux.getLexema())){
            tb.put(aux.getLexema(), aux);
            productor.add(aux.getLexema());
        }
    }

    public boolean addLongintPositiva(Symbol aux) {
        long numtoAdd = Long.valueOf(aux.getLexema().substring(0,aux.getLexema().length()-2));

        if (  numtoAdd < LexicalAnalyzer.MAX_INT_SIZE){
            setSymbol(aux.getLexema(), aux.getTipo());
            setAtributo(aux.getLexema(),"=>","CTE ENTERO LARGO");
            return true;
        }
        return false;
    }

    public ArrayList<String> getEntradas(){
        return new ArrayList<String>( tb.keySet());
    }


    public boolean addLongintNegativa(Symbol aux){
        long numtoAdd = Long.valueOf(aux.getLexema().substring(0,aux.getLexema().length()-2))*(-1);

        if (  numtoAdd > LexicalAnalyzer.MIN_INT_SIZE){
            setSymbol("-"+aux.getLexema(), aux.getTipo());
            setAtributo("-"+aux.getLexema(),"=>","CTE ENTERO LARGO");
            return true;
        }
       return false;
    }

    public boolean addFloatPositiva(Symbol aux) {
        float floatToAdd  = Float.valueOf(aux.getLexema().substring(0,aux.getLexema().length()));

        if (floatToAdd < LexicalAnalyzer.MAX_FLOAT_SIZE) {
            setSymbol(aux.getLexema(), aux.getTipo());
            setAtributo(aux.getLexema(),"=>","CTE FLOTANTE");
            return true;
        }
        return false;
    }

    public boolean addFloatNegativa(Symbol aux){
        float floatToAdd  = Float.valueOf(aux.getLexema().substring(0,aux.getLexema().length()))*(-1);

        if (floatToAdd > LexicalAnalyzer.MIN_FLOAT_SIZE) {
            setSymbol("-"+aux.getLexema(), aux.getTipo());
            setAtributo("-"+aux.getLexema(),"=>","CTE FLOTANTE");
            return true;
        }
        return false;

       /* if (num != 0.0) {
            if (num < LexicalAnalyzer.MIN_FLOAT_SIZE) {
                lexical.errors.setError(lexical.row,  Errors.ERROR_RANGE);
                num = lexical.MIN_FLOAT_SIZE;
            } else if (num > lexical.MAX_FLOAT_SIZE) {
                lexical.errors.setError(lexical.row,  Errors.ERROR_RANGE);
                num = lexical.MAX_FLOAT_SIZE;
            }
        } */
    }

    public void setAtributo(String lexema, String atributo, Object valor){
        if (tb.containsKey(lexema))
            tb.get(lexema).setAtributos(atributo,valor);
    }

}
