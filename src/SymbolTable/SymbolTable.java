package SymbolTable;


import AnalizadorLexico.LexicalAnalyzer;

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
        //debemos agregar un else para contemplar los casos donde el lexema ya existe pero es otro token
        // por ejemplo 25_i y '25_i' o _palabra y 'palabra' o 2.5F35 y '2.5F35'
        //donde albos token tienen el mismo lexema pero distinto tipo
        //como el probrema viene traido por las cadenas de caracteres, guardamos el primer ' como parte del lexema
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

        return false;
    }

    public boolean addFloatNegativa(Symbol aux){

        return false;
    }

    public void setAtributo(String lexema, String atributo, Object valor){
        if (tb.containsKey(lexema))
            tb.get(lexema).setAtributos(atributo,valor);
    }

}
