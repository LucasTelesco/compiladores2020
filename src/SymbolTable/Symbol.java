package SymbolTable;

import java.util.Hashtable;

public class Symbol {
    private String lexema;
    private String tipoVar = "sin asignar"; // tipo de dato para identificadores(set en AS) y constantes(set en gramatica)
    private short tipo;
    private boolean usada=false;

    private Hashtable <String,Object> atributos;

     /** Constantes tienen tipo de token y tipo de dato
     * Guardarlo en lexico: tipo de dato (integer o float) tipo de token
     *
     * los identificadores el tipo de datos en la parte semantica
     *
     * Cadena solo tienen tipo de token
     * las variables tienen tipo de toque tipo de dato y mutabilidad
     * las variables de tipo puntero agregan mutabilidad de lo apuntado y saber si es un puntero
     * **/
    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public Symbol(String lexema, int number) {
        this.lexema = lexema;
        this.tipo = (short) number;
        atributos=new Hashtable<>();
    }

    @Override
    public String toString() {
        if (atributos.containsKey("=>"))
            return (String) atributos.get("=>");
        return getLexema();
    }

    public String getLexema () {
        return this.lexema;
    }

    public void setLexema (String lexema){
        this.lexema = lexema;
    }

    public int getTipo () {
        return (int) tipo;
    }

    public void setTipo (int t){
        this.tipo = (short) t;
    }

    public Object getAtributo(String clave) {
        return atributos.get(clave);
    }

    public Boolean contieneAtributo(String clave) { return atributos.containsKey(clave);}

    public void setAtributos(String atributo, Object valor) {
        atributos.put(atributo,valor);
    }
    public void cambiarSigno (){

    }

    public String getAtributos(){
        String attr = lexema+"   tipo :   "+tipo;
        for (String key : atributos.keySet()) {
            attr+=" "+key+" "+atributos.get(key);
        }
        return attr;
    }

    public void setTipoVar(String tipoVar) {
        this.tipoVar = tipoVar;
    }

    public String getTipoVar() {
        return tipoVar;
    }
}