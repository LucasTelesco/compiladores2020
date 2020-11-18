//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 4 "gramaticaGrupo10.y"
package AnalizadorSintactico;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
import Tercetos.*;
//#line 22 "gramaticaGrupo10.y"


  
//#line 31 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short ID=259;
public final static short CTE=260;
public final static short CADENA_MULTINEA=261;
public final static short ESIGUAL=262;
public final static short MAYIG=263;
public final static short MENIG=264;
public final static short DIST=265;
public final static short FIN=266;
public final static short END_IF=267;
public final static short LOOP=268;
public final static short UNTIL=269;
public final static short FLOTANTE=270;
public final static short LONGINT=271;
public final static short FLOAT=272;
public final static short OUT=273;
public final static short FUNC=274;
public final static short RETURN=275;
public final static short PROC=276;
public final static short NS=277;
public final static short NA=278;
public final static short THEN=279;
public final static short CTELONGINT=280;
public final static short CTEFLOAT=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    0,    1,    1,    3,    3,    5,    5,    5,
    8,    8,    9,    9,    9,   10,    7,    7,    7,    6,
    6,    6,   11,   11,    4,    4,    4,   15,   15,   15,
   16,   16,   16,   17,   17,   17,   17,   17,   12,   12,
   12,   12,   14,   14,   13,   13,   18,   18,   18,   18,
   18,   19,   19,   21,   21,   21,   23,   22,   20,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,
};
final static short yylen[] = {                            2,
    1,    0,    3,    1,    2,    1,    1,    3,    2,    1,
   15,    1,    1,    3,    2,    2,    1,    3,    2,    1,
    1,    2,    1,    2,    2,    1,    2,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    2,    2,    3,    2,
    2,    2,    4,    2,    1,    1,    6,    4,    4,    3,
    4,    4,    3,    1,    3,    3,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    2,    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   57,   20,   21,    0,    0,    0,    0,
    0,    1,    0,    6,    7,    0,    0,    0,   26,    0,
   45,   46,    0,    0,   22,    0,    0,    0,   42,    0,
   44,    0,    0,   36,    0,    0,   34,   35,    0,    0,
    0,    0,    0,    0,    0,   40,    0,    5,    0,    0,
    9,   25,   27,    0,    0,   54,    0,    0,    3,   58,
   50,    0,   39,    0,    0,   68,   69,   37,   38,   66,
   67,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   59,    0,   19,    8,    0,    0,    0,    0,    0,
   53,   48,   51,    0,   43,    0,    0,    0,    0,   64,
   65,   63,   62,   60,   61,   28,   29,   31,   32,   18,
   24,   56,   55,   49,   52,    0,   16,    0,    0,   15,
   47,    0,   14,    0,    0,    0,    0,    0,    0,    0,
    0,   11,
};
final static short yydgoto[] = {                         11,
   12,   26,   13,   56,   15,   16,   50,   17,   98,   99,
   87,   18,   19,   20,   42,   43,   44,   21,   22,   23,
   57,   61,   24,   45,
};
final static short yysindex[] = {                       -40,
  -28,  -36,  -56,    0,    0,    0,  -21, -227,  -10,  -35,
    0,    0,  -34,    0,    0, -225,  -20,  -19,    0,  -15,
    0,    0,   25,   25,    0, -228, -213,   25,    0,  -35,
    0, -208,    8,    0,  -35,  -35,    0,    0, -244,  -35,
  -35,   53,  -17,  -22,   13,    0,  -28,    0,  -41,   -4,
    0,    0,    0,   32,   32,    0, -213,  -29,    0,    0,
    0,   29,    0,   15, -241,    0,    0,    0,    0,    0,
    0,  -35,  -35,  -35,  -35,  -35,  -35,  -35,  -35,  -35,
  -35,    0, -225,    0,    0,   32,  -67,  -66,   25,   21,
    0,    0,    0,   25,    0,  -28, -192,   33,  -42,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -191,    0, -203, -241,    0,
    0,   16,    0, -201,   36, -196,   22, -198,  -39,  -34,
  -31,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   38,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   50,   44,    0,    0,    7,    0,   39,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -25,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   94,    0,  -13,    9,    0,  -48,  -26,    0,  -86,    0,
  -37,    0,    0,    0,   86,  -38,    0,    0,    0,   12,
  -16,  -33,    0,    0,
};
final static int YYTABLESIZE=318;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          9,
   10,  119,   83,    9,   30,    9,   10,   58,   14,   39,
    9,   62,  120,   28,   96,   25,   97,   88,   32,   81,
   10,   14,   84,   89,   80,   78,   10,   79,   94,    5,
    6,   33,  123,   49,   39,   68,   69,   59,   51,   52,
   10,  108,  109,   53,   60,   93,   10,   65,  111,   41,
   97,   40,   64,   82,   85,   95,  110,  112,  113,   12,
    9,   10,   86,   86,    9,   12,  117,   10,    9,   91,
   97,    9,  114,  118,  122,  121,  124,  116,  125,  126,
  127,  129,  128,  130,   33,   10,   33,    4,   33,   10,
   30,   13,   10,  132,   86,   46,   41,   17,    0,   23,
    0,  115,   33,   33,   33,   33,   48,    0,   30,   30,
   30,   30,   77,   75,   76,   63,  131,    0,    0,    0,
   66,   67,    0,    0,    0,   70,   71,    0,    0,    0,
    0,   10,    0,    0,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    0,    0,    0,    0,   55,    0,    0,
    0,   55,    0,    0,    0,    0,    0,  100,  101,  102,
  103,  104,  105,  106,  107,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   29,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   96,    0,    1,    2,   49,    3,   27,
    0,   47,    2,   34,    3,    0,    0,    4,    5,    6,
    5,    6,    7,    4,   31,    8,    5,    6,    7,   90,
    0,    8,    0,    0,   37,   38,    0,    0,   34,    0,
    0,    0,   35,   36,    0,    0,   10,   10,    0,   10,
    0,    0,   10,   10,    0,   10,    2,    0,   10,   37,
   38,   10,   10,   10,   10,    0,   10,   10,   10,   10,
   54,    2,   10,    3,   54,    2,   60,    3,    2,    0,
    3,    0,    4,    0,    0,   92,    4,    7,    0,    4,
    0,    7,    0,    0,    7,    0,   33,   33,   33,    0,
    0,    0,   30,   30,   30,   72,   73,   74,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,   44,   40,   61,   40,    0,   24,    0,   45,
   40,   28,   99,    2,  256,   44,   65,   55,   40,   42,
   61,   13,   49,   57,   47,   43,   61,   45,   62,  271,
  272,  259,  119,  259,   45,  280,  281,  266,   59,   59,
   40,   80,   81,   59,  258,   62,   40,   40,   86,   60,
   99,   62,  261,   41,   59,   41,   83,  125,  125,   59,
   40,   61,   54,   55,   40,   59,  259,   61,   40,   58,
  119,   40,   89,   41,  278,  267,   61,   94,  280,   44,
  277,  280,   61,  123,   41,   61,   43,    0,   45,   61,
   41,   41,   61,  125,   86,   10,   59,   59,   -1,  125,
   -1,   90,   59,   60,   61,   62,   13,   -1,   59,   60,
   61,   62,   60,   61,   62,   30,  130,   -1,   -1,   -1,
   35,   36,   -1,   -1,   -1,   40,   41,   -1,   -1,   -1,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,  130,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,  123,   -1,   -1,   -1,   -1,   -1,   72,   73,   74,
   75,   76,   77,   78,   79,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,   -1,  256,  257,  259,  259,  256,
   -1,  256,  257,  259,  259,   -1,   -1,  268,  271,  272,
  271,  272,  273,  268,  256,  276,  271,  272,  273,  269,
   -1,  276,   -1,   -1,  280,  281,   -1,   -1,  259,   -1,
   -1,   -1,  263,  264,   -1,   -1,  256,  257,   -1,  259,
   -1,   -1,  256,  257,   -1,  259,  266,   -1,  268,  280,
  281,  271,  272,  273,  268,   -1,  276,  271,  272,  273,
  256,  257,  276,  259,  256,  257,  258,  259,  257,   -1,
  259,   -1,  268,   -1,   -1,  267,  268,  273,   -1,  268,
   -1,  273,   -1,   -1,  273,   -1,  263,  264,  265,   -1,
   -1,   -1,  263,  264,  265,  263,  264,  265,
};
}
final static short YYFINAL=11;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","ELSE","ID","CTE","CADENA_MULTINEA",
"ESIGUAL","MAYIG","MENIG","DIST","FIN","END_IF","LOOP","UNTIL","FLOTANTE",
"LONGINT","FLOAT","OUT","FUNC","RETURN","PROC","NS","NA","THEN","CTELONGINT",
"CTEFLOAT",
};
final static String yyrule[] = {
"$accept : programa",
"programa : lista_sentencia",
"$$1 :",
"programa : error $$1 FIN",
"lista_sentencia : sentencia",
"lista_sentencia : sentencia lista_sentencia",
"sentencia : ejecutable",
"sentencia : declaracion",
"declaracion : tipo lista_id ';'",
"declaracion : procedimiento ';'",
"declaracion : error",
"procedimiento : PROC ID '(' lista_parametro ')' NA '=' CTELONGINT ',' NS '=' CTELONGINT '{' sentencia '}'",
"procedimiento : error",
"lista_parametro : parametro",
"lista_parametro : parametro ',' lista_parametro",
"lista_parametro : parametro lista_parametro",
"parametro : tipo ID",
"lista_id : ID",
"lista_id : ID ',' lista_id",
"lista_id : ID lista_id",
"tipo : LONGINT",
"tipo : FLOAT",
"tipo : error ','",
"lista_ejecutable : ejecutable",
"lista_ejecutable : ejecutable lista_ejecutable",
"ejecutable : asignacion ';'",
"ejecutable : bloque",
"ejecutable : exp_out ';'",
"expresion : termino '+' expresion",
"expresion : termino '-' expresion",
"expresion : termino",
"termino : factor '/' termino",
"termino : factor '*' termino",
"termino : factor",
"factor : CTELONGINT",
"factor : CTEFLOAT",
"factor : ID",
"factor : '-' CTELONGINT",
"factor : '-' CTEFLOAT",
"asignacion : ID '=' expresion",
"asignacion : '=' expresion",
"asignacion : ID '='",
"asignacion : ID error",
"exp_out : OUT '(' CADENA_MULTINEA ')'",
"exp_out : OUT error",
"bloque : sent_if",
"bloque : sent_loop",
"sent_if : IF condicion_salto cuerpo else_ cuerpo END_IF",
"sent_if : IF condicion_salto cuerpo END_IF",
"sent_if : condicion_salto cuerpo else_ cuerpo",
"sent_if : IF error else_",
"sent_if : IF condicion_salto cuerpo cuerpo",
"sent_loop : loop_ cuerpo UNTIL condicion_salto",
"sent_loop : loop_ cuerpo condicion_salto",
"cuerpo : ejecutable",
"cuerpo : '{' lista_ejecutable '}'",
"cuerpo : error lista_ejecutable '}'",
"loop_ : LOOP",
"else_ : ELSE",
"condicion_salto : '(' condicion ')'",
"condicion : expresion '>' expresion",
"condicion : expresion '<' expresion",
"condicion : expresion '=' expresion",
"condicion : expresion DIST expresion",
"condicion : expresion MAYIG expresion",
"condicion : expresion MENIG expresion",
"condicion : '>' expresion",
"condicion : '<' expresion",
"condicion : MAYIG expresion",
"condicion : MENIG expresion",
};

//#line 278 "gramaticaGrupo10.y"

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras;
  public ArrayList<String> tokens = new ArrayList<>();
  public ArrayList<String> id = new ArrayList<>();
  public ArrayList<Terceto> listaTercetos = new ArrayList<>();
  int contadorVarAux=0;
  int contadorTerceto=0;

  public Stack<Integer> p = new Stack<Integer>();

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
//#line 418 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 26 "gramaticaGrupo10.y"
{}
break;
case 2:
//#line 27 "gramaticaGrupo10.y"
{yyerror("No hay sentencia");}
break;
case 3:
//#line 27 "gramaticaGrupo10.y"
{}
break;
case 4:
//#line 30 "gramaticaGrupo10.y"
{}
break;
case 5:
//#line 31 "gramaticaGrupo10.y"
{}
break;
case 6:
//#line 34 "gramaticaGrupo10.y"
{}
break;
case 7:
//#line 35 "gramaticaGrupo10.y"
{}
break;
case 8:
//#line 38 "gramaticaGrupo10.y"
{estructuras.add("declaracion "+" fila "+val_peek(2).getFila());}
break;
case 9:
//#line 39 "gramaticaGrupo10.y"
{}
break;
case 10:
//#line 40 "gramaticaGrupo10.y"
{yyerror("Declaracion mal definida ");}
break;
case 11:
//#line 44 "gramaticaGrupo10.y"
{estructuras.add("Procedimiento "+" fila "+val_peek(14).getFila());}
break;
case 12:
//#line 45 "gramaticaGrupo10.y"
{yyerror("Control mal definida ");}
break;
case 13:
//#line 48 "gramaticaGrupo10.y"
{}
break;
case 14:
//#line 49 "gramaticaGrupo10.y"
{}
break;
case 15:
//#line 50 "gramaticaGrupo10.y"
{yyerror("Se esperaba ',' ",val_peek(1).getFila());}
break;
case 16:
//#line 53 "gramaticaGrupo10.y"
{}
break;
case 17:
//#line 56 "gramaticaGrupo10.y"
{
                Vector<ParserVal> vect = new Vector<ParserVal>();/*$1 es el parser val con el symbolo de ese ID*/
                vect.add(val_peek(0));
                yyval.obj = vect; }
break;
case 18:
//#line 62 "gramaticaGrupo10.y"
{
                    Vector<ParserVal> vect = (Vector<ParserVal>)(val_peek(0).obj); /*$3 me trae el vector original primero y desp aumenta*/
                    vect.add(val_peek(2));/*ver si anda, hay que castear a Symbol? .obj*/
                    yyval.obj = vect;
	}
break;
case 19:
//#line 68 "gramaticaGrupo10.y"
{yyerror("Se esperaba ';' ",val_peek(1).getFila());}
break;
case 20:
//#line 71 "gramaticaGrupo10.y"
{yyval.sval="longint";}
break;
case 21:
//#line 72 "gramaticaGrupo10.y"
{yyval.sval="float";}
break;
case 22:
//#line 73 "gramaticaGrupo10.y"
{yyerror("Tipo indefinido",val_peek(1).getFila());}
break;
case 23:
//#line 76 "gramaticaGrupo10.y"
{}
break;
case 24:
//#line 77 "gramaticaGrupo10.y"
{}
break;
case 25:
//#line 80 "gramaticaGrupo10.y"
{}
break;
case 26:
//#line 81 "gramaticaGrupo10.y"
{
          }
break;
case 27:
//#line 83 "gramaticaGrupo10.y"
{}
break;
case 28:
//#line 87 "gramaticaGrupo10.y"
{            
  							Terceto t = new T_Suma_Resta(contadorTerceto,"+",val_peek(2).obj,val_peek(0).obj,st);
                /*st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos*/
                t.setVariableAux(contadorVarAux);
                contadorVarAux++;
                contadorTerceto ++;
                listaTercetos.add(t);
                yyval=val_peek(2);
                yyval.obj = t;
}
break;
case 29:
//#line 97 "gramaticaGrupo10.y"
{			    
       						    Terceto t = new T_Suma_Resta(contadorTerceto,"-",val_peek(2).obj,val_peek(0).obj,st);
                      /*st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos*/
                      t.setVariableAux(contadorVarAux);
                      contadorVarAux++;
                      contadorTerceto ++;
                      listaTercetos.add(t);
                      yyval=val_peek(2);
                      yyval.obj = t;
}
break;
case 30:
//#line 107 "gramaticaGrupo10.y"
{yyval=val_peek(0);
                  yyval.obj=val_peek(0).obj; /*VER*/
}
break;
case 31:
//#line 113 "gramaticaGrupo10.y"
{
          Terceto t = new T_Mult_Div(contadorTerceto,"/",val_peek(2).obj,val_peek(0).obj,st);
          t.setVariableAux(contadorVarAux);
          contadorVarAux++;
          contadorTerceto ++;
          listaTercetos.add(t);
          yyval=val_peek(2);
          yyval.obj = t;
}
break;
case 32:
//#line 122 "gramaticaGrupo10.y"
{
          Terceto t = new T_Mult_Div(contadorTerceto,"*",val_peek(2).obj,val_peek(0).obj,st);
          t.setVariableAux(contadorVarAux);
          contadorVarAux++;
          contadorTerceto ++;
          listaTercetos.add(t);
          yyval=val_peek(2);
          yyval.obj = t;
    }
break;
case 33:
//#line 131 "gramaticaGrupo10.y"
{yyval=val_peek(0);
			        yyval.obj=val_peek(0).obj;
		}
break;
case 34:
//#line 136 "gramaticaGrupo10.y"
{yyval=val_peek(0);
                      if(!st.addLongintPositiva(((Symbol)(val_peek(0).obj)))){
                        yyerror("constante fuera de rango",val_peek(0).getFila());
                      }  
                    }
break;
case 35:
//#line 141 "gramaticaGrupo10.y"
{yyval=val_peek(0);
               if(!st.addFloatPositiva(((Symbol)(val_peek(0).obj)))){
                  yyerror("constante fuera de rango",val_peek(0).getFila());
                }  
             }
break;
case 36:
//#line 146 "gramaticaGrupo10.y"
{if(!((Symbol)(val_peek(0).obj)).isUsada()){
			/*error*/
			yyerror("variable no declarada",val_peek(0).getFila());
			}
			 yyval=val_peek(0);
	}
break;
case 37:
//#line 152 "gramaticaGrupo10.y"
{
					           yyval=val_peek(0);
                      if(!st.addLongintNegativa(((Symbol)(val_peek(0).obj)))){
                        yyerror("constante fuera de rango",val_peek(1).getFila());
                      }  
 		              }
break;
case 38:
//#line 158 "gramaticaGrupo10.y"
{
		              yyval=val_peek(0);
                      if(!st.addFloatNegativa(((Symbol)(val_peek(0).obj)))){
                        yyerror("constante fuera de rango",val_peek(1).getFila());
                      }
                   }
break;
case 39:
//#line 166 "gramaticaGrupo10.y"
{
            Terceto t = new T_Asignacion(contadorTerceto,":=",val_peek(2).obj,val_peek(0).obj,st);
            t.setVariableAux(contadorVarAux);/*casi seguro que si hay que crearla aca*/
            contadorVarAux++;
            contadorTerceto ++;
            listaTercetos.add(t);
            yyval=val_peek(2);
            yyval.obj = t;
            estructuras.add("Asignacion "+" fila "+val_peek(2).getFila());
    }
break;
case 40:
//#line 176 "gramaticaGrupo10.y"
{yyerror("Error de asignacion ",val_peek(1).getFila());}
break;
case 41:
//#line 177 "gramaticaGrupo10.y"
{yyerror("Error de asignacion ",val_peek(1).getFila());}
break;
case 42:
//#line 178 "gramaticaGrupo10.y"
{yyerror("no se encontro '=' ",val_peek(1).getFila());}
break;
case 43:
//#line 182 "gramaticaGrupo10.y"
{ 
        estructuras.add("Expresion out "+" fila "+val_peek(3).getFila());
        Terceto t = new T_Out(contadorTerceto,"OUT",val_peek(1).obj,"",st);
        contadorTerceto ++;
        listaTercetos.add(t);
        yyval=val_peek(3);
        yyval.obj = t;
        }
break;
case 44:
//#line 190 "gramaticaGrupo10.y"
{yyerror("Error en la construccion del out",val_peek(1).getFila());}
break;
case 45:
//#line 194 "gramaticaGrupo10.y"
{}
break;
case 46:
//#line 195 "gramaticaGrupo10.y"
{}
break;
case 47:
//#line 199 "gramaticaGrupo10.y"
{estructuras.add("Sentencia IF Else" +" fila "+val_peek(5).getFila());}
break;
case 48:
//#line 200 "gramaticaGrupo10.y"
{estructuras.add("Sentencia IF " +" fila "+val_peek(3).getFila());}
break;
case 49:
//#line 201 "gramaticaGrupo10.y"
{yyerror(" falta la palabra reservada IF",val_peek(3).getFila());}
break;
case 50:
//#line 202 "gramaticaGrupo10.y"
{yyerror(" Error en la construccion de la sentencia IF ",val_peek(2).getFila());}
break;
case 51:
//#line 203 "gramaticaGrupo10.y"
{yyerror(" Falta la palabra reservada ELSE ",val_peek(3).getFila());}
break;
case 52:
//#line 206 "gramaticaGrupo10.y"
{estructuras.add("Sentencia Loop " +" fila "+val_peek(3).getFila());}
break;
case 53:
//#line 207 "gramaticaGrupo10.y"
{yyerror("Linea  Falta palabra reservada UNTIL",val_peek(2).getFila());}
break;
case 54:
//#line 210 "gramaticaGrupo10.y"
{}
break;
case 55:
//#line 211 "gramaticaGrupo10.y"
{}
break;
case 56:
//#line 212 "gramaticaGrupo10.y"
{yyerror("LInea  Omision de la palabra reservada '{' ",val_peek(2).getFila());}
break;
case 57:
//#line 215 "gramaticaGrupo10.y"
{System.out.println("Encontro LOOP");}
break;
case 58:
//#line 219 "gramaticaGrupo10.y"
{
    yyval=val_peek(0);
    }
break;
case 59:
//#line 224 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
   
}
break;
case 60:
//#line 229 "gramaticaGrupo10.y"
{
    Terceto t = new T_Comparador(contadorTerceto,">",val_peek(2).obj,val_peek(0).obj,st);
    contadorTerceto ++;
    listaTercetos.add(t);
    yyval=val_peek(2);
    yyval.obj = t;
   	}
break;
case 61:
//#line 236 "gramaticaGrupo10.y"
{
    Terceto t = new T_Comparador(contadorTerceto,"<",val_peek(2).obj,val_peek(0).obj,st);
    contadorTerceto ++;
    listaTercetos.add(t);
    yyval=val_peek(2);
    yyval.obj = t;
   }
break;
case 62:
//#line 243 "gramaticaGrupo10.y"
{
    Terceto t = new T_Comparador(contadorTerceto,"=",val_peek(2).obj,val_peek(0).obj,st);
    contadorTerceto ++;
    listaTercetos.add(t);
    yyval=val_peek(2);
    yyval.obj = t;
    }
break;
case 63:
//#line 250 "gramaticaGrupo10.y"
{
    Terceto t = new T_Comparador(contadorTerceto,"!=",val_peek(2).obj,val_peek(0).obj,st);
    contadorTerceto ++;
    listaTercetos.add(t);
    yyval=val_peek(2);
    yyval.obj = t;
   }
break;
case 64:
//#line 257 "gramaticaGrupo10.y"
{
    Terceto t = new T_Comparador(contadorTerceto,">=",val_peek(2).obj,val_peek(0).obj,st);
    contadorTerceto ++;
    listaTercetos.add(t);
    yyval=val_peek(2);
    yyval.obj = t;
    }
break;
case 65:
//#line 264 "gramaticaGrupo10.y"
{
    Terceto t = new T_Comparador(contadorTerceto,"<=",val_peek(2).obj,val_peek(0).obj,st);
    contadorTerceto ++;
    listaTercetos.add(t);
    yyval=val_peek(2);
    yyval.obj = t;
    }
break;
case 66:
//#line 271 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>'",val_peek(1).getFila());}
break;
case 67:
//#line 272 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<'",val_peek(1).getFila());}
break;
case 68:
//#line 273 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>='",val_peek(1).getFila());}
break;
case 69:
//#line 274 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<='",val_peek(1).getFila());}
break;
//#line 969 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
