/* Declaraciones */

%{
package AnalizadorSintactico;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
%}

/* Lista de tokens */

%token IF ELSE ID CTE CADENA_MULTINEA ESIGUAL MAYIG MENIG DIST FIN  END_IF LOOP UNTIL FLOTANTE LONGINT FLOAT OUT FUNC RETURN PROC NS NA THEN CTELONGINT CTEFLOAT

/* Reglas */

%{


  %}
%%
programa : lista_sentencia{}
     | error {yyerror("No hay sentencia");} FIN{}
	 ;

lista_sentencia : sentencia{}
	 | sentencia lista_sentencia{}
	 ;

sentencia: ejecutable {}
	| declaracion {}
        ;

declaracion: tipo lista_id ';' {estructuras.add("declaracion "+" fila "+$1.getFila());}
        | procedimiento ';'{}
        | error {yyerror("Declaracion mal definida ");}
        ;

procedimiento:  PROC ID '(' lista_parametro ')' NA '=' CTELONGINT ',' NS '=' CTELONGINT '{' sentencia '}'
                  {estructuras.add("Procedimiento "+" fila "+$1.getFila());}
                | error {yyerror("procedimiento mal definido ");}
                ;

lista_parametro:  parametro {}
                | parametro ',' lista_parametro {}
                | parametro lista_parametro {yyerror("Se esperaba ',' ",$1.getFila());}
                ;

parametro: tipo ID {}
         ;

lista_id: ID {
                Vector<ParserVal> vect = new Vector<ParserVal>();//$1 es el parser val con el symbolo de ese ID
                vect.add($1);
                $$.obj = vect; }
	

	| ID ',' lista_id {
                    Vector<ParserVal> vect = (Vector<ParserVal>)($3.obj); //$3 me trae el vector original primero y desp aumenta
                    vect.add($1);//ver si anda, hay que castear a Symbol? .obj
                    $$.obj = vect;
	}
	
    | ID lista_id{yyerror("Se esperaba ';' ",$1.getFila());}
    ;

tipo: LONGINT {$$.sval="longint";}
	| FLOAT {$$.sval="float";}
	| error ','{yyerror("Tipo indefinido",$1.getFila());}
	;

lista_ejecutable: ejecutable {}
	| ejecutable lista_ejecutable {}
	;

ejecutable: asignacion ';'{}
          | bloque {
          }
          | exp_out ';'{}

;

expresion: termino '+' expresion {            
                $$=$1;

}
	     | termino '-' expresion {			    
            $$=$1;

}
	     | termino {$$=$1;
         $$.obj=$1.obj; //VER
}
;


termino: factor '/' termino {
$$=$1;


}
	| factor '*' termino{
              
$$=$1;

    }
    | factor {$$=$1;
			  $$.obj=$1.obj;
			 }
	;
// LONGINT y FLOAT 
factor: CTELONGINT  {$$=$1;
                      if(!st.addLongintPositiva(((Symbol)($1.obj)),$1.getFila())){
                        yyerror("constante fuera de rango",$1.getFila());
                      }  
                    }
	| CTEFLOAT {$$=$1;
               if(!st.addFloatPositiva(((Symbol)($1.obj)))){
                  yyerror("constante fuera de rango",$1.getFila());
                }  
             }
	| ID {if(!((Symbol)($1.obj)).isUsada()){
			//error
			yyerror("variable no declarada",$1.getFila());
			}
			 $$=$1;
	}
	| '-' CTELONGINT {
					           $$=$2;
                      if(!st.addLongintNegativa(((Symbol)($2.obj)))){
                        yyerror("constante fuera de rango",$1.getFila());
                      }  
 		              }
	|'-' CTEFLOAT{	
		             $$=$2;
                  if(!st.addFloatNegativa(((Symbol)($2.obj)))){
                    yyerror("constante fuera de rango",$1.getFila());
                  }    
                    }
	;

asignacion: ID '=' expresion{

            $$=$1;

            estructuras.add("Asignacion "+" fila "+$1.getFila());
    }
	| '=' expresion  {yyerror("Falta elemento de asignacion ",$1.getFila());}
	| ID '='  {yyerror("error en el identificador, falta asignacion",$1.getFila());}
	| ID error  {yyerror("error en el identificador, falta asignacion",$1.getFila());}
	;


exp_out: OUT '(' CADENA_MULTINEA ')' { estructuras.add("Expresion out "+" fila "+$1.getFila());
	      	$$=$1;}
		    | OUT error {yyerror("Error en la construccion del out",$1.getFila());}
        ;


bloque: sent_if {}
	| sent_loop {}
	;


sent_if: IF condicion_salto cuerpo else_ cuerpo END_IF{estructuras.add("Sentencia IF Else" +" fila "+$1.getFila());}
        |IF condicion_salto cuerpo END_IF{estructuras.add("Sentencia IF " +" fila "+$1.getFila());}
	|  condicion_salto cuerpo else_ cuerpo {yyerror(" falta la palabra reservada IF",$1.getFila());}
	| IF error else_ {yyerror(" Error en la construccion de la sentencia IF ",$1.getFila());}
	| IF condicion_salto cuerpo cuerpo {yyerror(" Falta la palabra reservada ELSE ",$1.getFila());}
	;

sent_loop: loop_ cuerpo UNTIL condicion_salto {estructuras.add("Sentencia Loop " +" fila "+$1.getFila());}
	| loop_ cuerpo condicion_salto {yyerror("Linea  Falta palabra reservada UNTIL",$1.getFila());}
	;

cuerpo: ejecutable {}
	| '{' lista_ejecutable '}'{}
	| error lista_ejecutable '}' {yyerror("LInea  Omision de la palabra reservada '{' ",$1.getFila());}
	;

loop_: LOOP {System.out.println("Encontro LOOP");}
;


else_: ELSE {
    $$=$1;
    }
;

condicion_salto: '(' condicion ')' {
    $$=$1;
   
};

condicion: expresion '>' expresion {
    $$=$1;

   	}
	| expresion '<' expresion {
    $$=$1;

   }
	| expresion '=' expresion {

    $$=$1;

    }
	| expresion DIST expresion {

    $$=$1;
	
   }
	| expresion MAYIG expresion {
    $$=$1;
    
    }
	| expresion MENIG expresion {
    $$=$1;

    }
	| '>' expresion {yyerror("Linea  se esperaba una expresion y se encontro '>'",$1.getFila());}
	| '<' expresion {yyerror("Linea  se esperaba una expresion y se encontro '<'",$1.getFila());}
	| MAYIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '>='",$1.getFila());}
	| MENIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '<='",$1.getFila());}
	;

%%

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras;
  public ArrayList<String> tokens = new ArrayList<>();
  public ArrayList<String> id = new ArrayList<>();

  public Stack<Integer> p = new Stack<Integer>();
  int contadorVarAux=0;


    int yylex(){

    int a = lex.getNextToken();

    if (lex.yylval != null){
      yylval = lex.yylval;
      lex.yylval = null;
    }else{
      yylval = new ParserVal();
    }
    tokens.add(yylval.toString()+" fila: "+yylval.getFila());
    return a;
  }

  public Parser(LexicalAnalyzer lex,SymbolTable st, Errors er,ArrayList<String> estrc)
{
  this.lex = lex;
  this.st = st;
  this.errors=er;
  this.estructuras=estrc;
}

void yyerror(String s){
    errors.setError(lex.row,s);
  }
void yyerror(String s,int row){
      errors.setError(row,s);
  }