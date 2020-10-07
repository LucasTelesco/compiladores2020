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
	 | FIN {yyerror("No hay sentencia");}
     | error {yyerror("No hay sentencia");} FIN{}
	 ;

lista_sentencia : sentencia{}
	 | sentencia lista_sentencia{}
	 ;

sentencia: ejecutable {}
	| declaracion {}
        ;

declaracion: tipo lista_id ';' {
        System.out.println("Encontro declaracion ");
		}

        | error {yyerror("Declaracion mal definida ");}
        ;

// control: PROC ID '(' tipo lista_id ')' {
//        System.out.println("Encontro un control ");
//        }
//         | error {yyerror("Control mal definida ");

//         }
//        ;



lista_id: ID {//  id.add( ((Symbol)($1.obj)).getLexema() );
                Vector<ParserVal> vect = new Vector<ParserVal>();//$1 es el parser val con el symbolo de ese ID
                vect.add($1);//ver si anda, hay que castear a Symbol?
                $$.obj = vect; }
	

	| ID ',' lista_id {//id.add(((Symbol)($1.obj)).getLexema());
                    Vector<ParserVal> vect = (Vector<ParserVal>)($3.obj); //$3 me trae el vector original primero y desp aumenta
                    vect.add($1);//ver si anda, hay que castear a Symbol? .obj
                    $$.obj = vect;
	}
	
    | ID lista_id{yyerror("Se esperaba ';' ",$1.getFila(),$1.getColumna());}
    ;

tipo: LONGINT {$$.sval="longint";}
	| FLOAT {$$.sval="float";}
	| error ','{yyerror("Tipo indefinido",$1.getFila(),$1.getColumna());}
	;

lista_ejecutable: ejecutable {}
	| ejecutable lista_ejecutable {}
	;

ejecutable: asignacion ';'{}
          | bloque {//#######Solo llego aca si termino un if o un loop
          }
          // | exp_print ','{}

;

expresion: termino '+' expresion {            
                $$=$1;
               // $$.obj = t;
}
	     | termino '-' expresion {			    
            $$=$1;
            //$$.obj = t;
}
	     | termino {$$=$1;
         $$.obj=$1.obj; //VER
}
;


termino: factor '/' termino {
$$=$1;
//$$.obj = t;

}
	| factor '*' termino{
              
$$=$1;
//$$.obj = t;
    }
    | factor {$$=$1;
			 // $$.obj=$1.obj;
			 }
	;
// LONGINT y FLOAT 
factor: CTELONGINT {$$=$1;}
	| CTEFLOAT {$$=$1;}
	| ID {if(!((Symbol)($1.obj)).isUsada()){
			//error
			yyerror("variable no declarada",$1.getFila(),$1.getColumna());
			}
			 $$=$1;
	}
	| '-' CTELONGINT {
					  $$=$2;
                      st.addcambiarSigno(((Symbol)($2.obj)));  //((Symbol))($2.obj))
 		              }
	|'-' CTEFLOAT{	
		             $$=$2;
                     st.addcambiarSigno(((Symbol)($2.obj)));  //((Symbol))($2.obj))
                    }
	;

asignacion: ID '=' expresion{
            // if (!((Symbol)($1.obj)).isUsada()){
            //     yyerror("La variable no esta definida ",$1.getFila(),$1.getColumna());
            // }
            $$=$1;
            //$$.obj = t;
            //estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna()); 
    }
	| '=' expresion  {yyerror("Falta elemento de asignacion ",$1.getFila(),$1.getColumna());}
	| ID '='  {yyerror("Falta elemento de asignacion ",$1.getFila(),$1.getColumna());}
	| ID error  {yyerror("no se encontro '=' ",$1.getFila(),$1.getColumna());}
	;


// exp_out: OUT '(' CADENA_MULTINEA ')' { estructuras.add("Expresion out "+" fila "+$1.getFila()+" columna "+$1.getColumna());
// 		$$=$1;
// }
// 		| OUT error {yyerror("Linea  Error en la construccion del out",$1.getFila(),$1.getColumna());
// 		}
//                ;

bloque: sent_if {}
	| sent_loop {}
	;


sent_if: IF condicion_salto cuerpo else_ cuerpo END_IF{estructuras.add("Sentencia IF Else" +" fila "+$1.getFila()+" columna "+$1.getColumna());}
        |IF condicion_salto cuerpo END_IF{estructuras.add("Sentencia IF " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	|  condicion_salto cuerpo else_ cuerpo {yyerror(" falta la palabra reservada IF",$1.getFila(),$1.getColumna());}
	| IF error else_ {yyerror(" Error en la construccion de la sentencia IF ",$1.getFila(),$1.getColumna());}
	| IF condicion_salto cuerpo cuerpo {yyerror(" Falta la palabra reservada ELSE ",$1.getFila(),$1.getColumna());}
	;

sent_loop: loop_ cuerpo UNTIL condicion_salto {estructuras.add("Sentencia Loop " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| loop_ cuerpo condicion_salto {yyerror("Linea  Falta palabra reservada UNTIL",$1.getFila(),$1.getColumna());}
	;

cuerpo: ejecutable {}
	| '{' lista_ejecutable '}'{}
	| error lista_ejecutable '}' {yyerror("LInea  Omision de la palabra reservada '{' ",$1.getFila(),$1.getColumna());}
	;

loop_: LOOP {System.out.println("Encontro LOOP");}
;


else_: ELSE {//#### aca hacemos el salto incondicional, debimos inventar este no terminal porque no diferenciamos bloque else de bloque if
        //aca ya hicimos el pop cuando termino el cuerpo del if
    $$=$1;
    //$$.obj = t;														
    }
;

condicion_salto: '(' condicion ')' {
    $$=$1;
    //$$.obj = t;
};

condicion: expresion '>' expresion {
    $$=$1;
   // $$.obj = t;
   	}
	| expresion '<' expresion {
    $$=$1;
   // $$.obj = t;										
   }
	| expresion '=' expresion {

    $$=$1;
    //$$.obj = t;										
    }
	| expresion DIST expresion {

    $$=$1;
   // $$.obj = t;										
   }
	| expresion MAYIG expresion {
    $$=$1;
    //$$.obj = t;										
    }
	| expresion MENIG expresion {
    $$=$1;
    //$$.obj = t;										
    }
	| '>' expresion {yyerror("Linea  se esperaba una expresion y se encontro '>'",$1.getFila(),$1.getColumna());}
	| '<' expresion {yyerror("Linea  se esperaba una expresion y se encontro '<'",$1.getFila(),$1.getColumna());}
	| MAYIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '>='",$1.getFila(),$1.getColumna());}
	| MENIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '<='",$1.getFila(),$1.getColumna());}
	;

%%

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras=new ArrayList<>();
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
    tokens.add(yylval.toString()+" fila: "+yylval.getFila()+" columna: "+yylval.getColumna());
    return a;
  }

  public Parser(LexicalAnalyzer lex,SymbolTable st, Errors er)
{
  this.lex = lex;
  this.st = st;
  this.errors=er;
}

void yyerror(String s){
    errors.setError(lex.row, lex.column,s);
  }
void yyerror(String s,int row,int column){
      errors.setError(row,column,s);
  }