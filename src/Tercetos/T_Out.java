package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_Out extends Terceto {
	

	
	public T_Out(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,"trampita",ts);
		}
	
	
	
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector<String> getAssembler() {
    	Vector<String> codigoAss = new Vector<String>();
		codigoAss.add(new String("\r\ninvoke MessageBox, NULL, addr " + "_"+((Symbol)operando1).getLexema().replace(" ","_") + ", addr TITULO , MB_OK "));
		return codigoAss;
	}

	public String toString() {
		String op1,op2;
		if (operando1!="trampita")
			if (esTerceto(1))
				op1 =operando1.toString().substring(0,3);
			else
				op1=((Symbol)operando1).getLexema();
		else
			op1="- ";

		return "["+num+"] ("+operador+", "+op1+", [-]).";
	}
	
	public SymbolTable getTabla() {
		return ts;
	}
	public void setTabla(SymbolTable ts) {
		this.ts = ts;
	}
}
