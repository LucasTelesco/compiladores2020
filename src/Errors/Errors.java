package Errors;

import java.util.LinkedList;
import java.util.Queue;

public class Errors {
    private class Terna{
        public int row;
        public String error;

        public Terna(int row,  String error){
            this.row = row;
            this.error = error;
        }
    }
 // table
    private Queue<Terna> errors;
    private Terna terna;
    public final static String ERROR_MAX_WORD_SIZE = "WARNING: supera el maximo permitido, el identificador sera truncado";
    public final static String ERROR_FAIL_CHARACTER= "ERROR: lexema no valido";
    public final static String ERROR_RANGE= "ERROR: fuera de rango";
    public Errors(){
        errors = new LinkedList<>();
    }

    public void setError(int row, String error){
        errors.add(new Terna(row,error));
    }

    public String getError(){
        terna = errors.remove();
        return terna.error;
    }

    public String getAll(){
        String out = new String();

        for (Terna elem : errors){
            out += elem.error + " fila " + elem.row + "\n";
        }
        return out;
    }

    public int getRow(){
        return terna.row;
    }


    public boolean isEmpty(){
        return errors.isEmpty();
    }
}
