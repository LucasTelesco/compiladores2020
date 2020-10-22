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
//#line 21 "gramaticaGrupo10.y"


  
//#line 30 "Parser.java"




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
    0,    0,    2,    0,    1,    1,    3,    3,    5,    5,
    7,    7,    7,    6,    6,    6,    8,    8,    4,    4,
    4,   11,   11,   11,   12,   12,   12,   13,   13,   13,
   13,   13,    9,    9,    9,    9,   10,   10,   14,   14,
   14,   14,   14,   15,   15,   17,   17,   17,   19,   18,
   16,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,
};
final static short yylen[] = {                            2,
    1,    1,    0,    3,    1,    2,    1,    1,    3,    1,
    1,    3,    2,    1,    1,    2,    1,    2,    2,    1,
    2,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    2,    2,    3,    2,    2,    2,    1,    1,    6,    4,
    4,    3,    4,    4,    3,    1,    3,    3,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    2,    2,    2,
    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    2,   49,   14,   15,    0,    0,    0,
    1,    0,    7,    8,    0,    0,   20,   37,   38,    0,
    0,   16,    0,    0,    0,   36,    0,   30,   28,   29,
    0,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,    0,    0,   21,   19,    0,    0,   46,    0,
    0,    4,   50,   42,    0,   33,   31,   32,    0,    0,
    0,    0,   60,   61,   58,   59,    0,    0,    0,    0,
    0,    0,   51,    0,   13,    9,    0,    0,    0,    0,
    0,   45,   40,   43,    0,   22,   23,   25,   26,   56,
   57,   55,   54,   52,   53,   12,   18,   48,   47,   41,
   44,    0,   39,
};
final static short yydgoto[] = {                         10,
   11,   23,   12,   49,   14,   15,   44,   78,   16,   17,
   32,   33,   34,   18,   19,   20,   50,   54,   21,   40,
};
final static short yysindex[] = {                       -27,
  -21,  -35,  -41,    0,    0,    0,    0,  -43,  -45,    0,
    0,   23,    0,    0, -233,  -48,    0,    0,    0,   -9,
   -9,    0, -237, -226,   -9,    0,  -43,    0,    0,    0,
 -244,    0,  -10,  -20,  -43,  -43,  -43,  -43,  -53,    2,
  -21,    0,  -34,  -17,    0,    0,  -37,  -37,    0, -226,
  -36,    0,    0,    0,   29,    0,    0,    0,  -43,  -43,
  -43,  -43,    0,    0,    0,    0,  -43,  -43,  -43,  -43,
  -43,  -43,    0, -233,    0,    0,  -37,  -81,  -78,   -9,
    9,    0,    0,    0,   -9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -216,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   53,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -47,    0,    0,    0,
    0,    0,   42,   36,    0,    0,    0,    0,    0,    0,
    6,    0,   -5,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -70,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   44,    0,    0,   28,    0,    0,  -24,  -32,    0,    0,
   90,  -23,    0,    0,    0,   19,   -7,  -25,    0,    0,
};
final static int YYTABLESIZE=307;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
   10,   31,    9,    9,    9,   10,   72,   70,   71,   74,
   46,   35,    9,   51,   38,   79,   37,   55,   75,   27,
   25,   62,   22,    8,   80,   43,   61,   13,   52,   85,
    9,   53,   59,    8,   60,   57,   58,   88,   89,   13,
   10,   76,   73,   98,   97,   10,   99,   84,    9,   96,
  103,    8,    5,   11,   17,   42,    0,    0,    0,    0,
    0,   10,    9,    0,    0,    0,   10,    0,    9,   82,
    0,    0,  100,    0,   77,   77,   27,  102,   27,    0,
   27,    0,   24,    8,    0,    0,    0,    0,    0,    8,
    0,    0,    0,    0,   27,   27,   27,   27,   39,  101,
   24,   24,   24,   24,   77,    0,    0,    0,    0,    0,
    0,    0,    0,   48,    0,    0,   56,    0,    0,    0,
    0,    0,    0,    0,   63,   64,   65,   66,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   86,   87,
    0,   48,    0,    0,    0,    0,   90,   91,   92,   93,
   94,   95,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,   35,   67,
   68,   69,    0,   28,   26,   28,    0,   35,   36,    2,
   24,    3,    0,    0,   43,    0,    0,    0,    1,    2,
    5,    3,   81,    0,   29,   30,   29,   30,    4,    0,
    5,    0,    0,    6,    7,    0,   47,    2,    0,    3,
    0,    0,    0,    0,    0,    0,   10,   10,    5,   10,
    0,   10,   10,    0,   10,    0,    3,    0,   10,    0,
    0,   10,   10,   10,    0,    0,   10,   10,   41,    2,
    0,    3,    0,    0,   47,    2,   53,    3,    0,    0,
    5,   27,    0,    6,    7,   83,    5,   24,   27,   27,
   27,    0,    0,    0,   24,   24,   24,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   45,   40,   40,   40,    0,   60,   61,   62,   44,
   59,   59,   40,   21,   60,   48,   62,   25,   43,   61,
    2,   42,   44,   61,   50,  259,   47,    0,  266,   55,
   40,  258,   43,   61,   45,  280,  281,   61,   62,   12,
   40,   59,   41,  125,   77,   40,  125,   55,   40,   74,
  267,   61,    0,   59,  125,   12,   -1,   -1,   -1,   -1,
   -1,   61,   40,   -1,   -1,   -1,   61,   -1,   40,   51,
   -1,   -1,   80,   -1,   47,   48,   41,   85,   43,   -1,
   45,   -1,   41,   61,   -1,   -1,   -1,   -1,   -1,   61,
   -1,   -1,   -1,   -1,   59,   60,   61,   62,    9,   81,
   59,   60,   61,   62,   77,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,   -1,   27,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   35,   36,   37,   38,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   -1,  123,   -1,   -1,   -1,   -1,   67,   68,   69,   70,
   71,   72,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,  256,  263,
  264,  265,   -1,  259,  256,  259,   -1,  263,  264,  257,
  256,  259,   -1,   -1,  259,   -1,   -1,   -1,  256,  257,
  268,  259,  269,   -1,  280,  281,  280,  281,  266,   -1,
  268,   -1,   -1,  271,  272,   -1,  256,  257,   -1,  259,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,  268,  259,
   -1,  256,  257,   -1,  259,   -1,  266,   -1,  268,   -1,
   -1,  271,  272,  268,   -1,   -1,  271,  272,  256,  257,
   -1,  259,   -1,   -1,  256,  257,  258,  259,   -1,   -1,
  268,  256,   -1,  271,  272,  267,  268,  256,  263,  264,
  265,   -1,   -1,   -1,  263,  264,  265,
};
}
final static short YYFINAL=10;
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
"programa : FIN",
"$$1 :",
"programa : error $$1 FIN",
"lista_sentencia : sentencia",
"lista_sentencia : sentencia lista_sentencia",
"sentencia : ejecutable",
"sentencia : declaracion",
"declaracion : tipo lista_id ';'",
"declaracion : error",
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
"ejecutable : asignacion error",
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

//#line 225 "gramaticaGrupo10.y"

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
//#line 394 "Parser.java"
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
//#line 25 "gramaticaGrupo10.y"
{}
break;
case 2:
//#line 26 "gramaticaGrupo10.y"
{yyerror("No hay sentencia");}
break;
case 3:
//#line 27 "gramaticaGrupo10.y"
{yyerror("No hay sentencia");}
break;
case 4:
//#line 27 "gramaticaGrupo10.y"
{}
break;
case 5:
//#line 30 "gramaticaGrupo10.y"
{}
break;
case 6:
//#line 31 "gramaticaGrupo10.y"
{}
break;
case 7:
//#line 34 "gramaticaGrupo10.y"
{}
break;
case 8:
//#line 35 "gramaticaGrupo10.y"
{}
break;
case 9:
//#line 38 "gramaticaGrupo10.y"
{
        System.out.println("Encontro declaracion ");
		}
break;
case 10:
//#line 42 "gramaticaGrupo10.y"
{yyerror("Declaracion mal definida ");}
break;
case 11:
//#line 55 "gramaticaGrupo10.y"
{/*  id.add( ((Symbol)($1.obj)).getLexema() );*/
                Vector<ParserVal> vect = new Vector<ParserVal>();/*$1 es el parser val con el symbolo de ese ID*/
                vect.add(val_peek(0));/*ver si anda, hay que castear a Symbol?*/
                yyval.obj = vect; }
break;
case 12:
//#line 61 "gramaticaGrupo10.y"
{/*id.add(((Symbol)($1.obj)).getLexema());*/
                    Vector<ParserVal> vect = (Vector<ParserVal>)(val_peek(0).obj); /*$3 me trae el vector original primero y desp aumenta*/
                    vect.add(val_peek(2));/*ver si anda, hay que castear a Symbol? .obj*/
                    yyval.obj = vect;
	}
break;
case 13:
//#line 67 "gramaticaGrupo10.y"
{yyerror("Se esperaba ';' ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 14:
//#line 70 "gramaticaGrupo10.y"
{yyval.sval="longint";}
break;
case 15:
//#line 71 "gramaticaGrupo10.y"
{yyval.sval="float";}
break;
case 16:
//#line 72 "gramaticaGrupo10.y"
{yyerror("Tipo indefinido",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 17:
//#line 75 "gramaticaGrupo10.y"
{}
break;
case 18:
//#line 76 "gramaticaGrupo10.y"
{}
break;
case 19:
//#line 79 "gramaticaGrupo10.y"
{}
break;
case 20:
//#line 80 "gramaticaGrupo10.y"
{/*#######Solo llego aca si termino un if o un loop*/
          }
break;
case 21:
//#line 82 "gramaticaGrupo10.y"
{yyerror("Asignacion mal definida",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 22:
//#line 87 "gramaticaGrupo10.y"
{            
                yyval=val_peek(2);
               /* $$.obj = t;*/
}
break;
case 23:
//#line 91 "gramaticaGrupo10.y"
{			    
            yyval=val_peek(2);
            /*$$.obj = t;*/
}
break;
case 24:
//#line 95 "gramaticaGrupo10.y"
{yyval=val_peek(0);
         yyval.obj=val_peek(0).obj; /*VER*/
}
break;
case 25:
//#line 101 "gramaticaGrupo10.y"
{
yyval=val_peek(2);
/*$$.obj = t;*/

}
break;
case 26:
//#line 106 "gramaticaGrupo10.y"
{
              
yyval=val_peek(2);
/*$$.obj = t;*/
    }
break;
case 27:
//#line 111 "gramaticaGrupo10.y"
{yyval=val_peek(0);
			 /* $$.obj=$1.obj;*/
			 }
break;
case 28:
//#line 116 "gramaticaGrupo10.y"
{yyval=val_peek(0);}
break;
case 29:
//#line 117 "gramaticaGrupo10.y"
{yyval=val_peek(0);}
break;
case 30:
//#line 118 "gramaticaGrupo10.y"
{if(!((Symbol)(val_peek(0).obj)).isUsada()){
			/*error*/
			yyerror("variable no declarada",val_peek(0).getFila(),val_peek(0).getColumna());
			}
			 yyval=val_peek(0);
	}
break;
case 31:
//#line 124 "gramaticaGrupo10.y"
{
					  yyval=val_peek(0);
                      st.addcambiarSigno(((Symbol)(val_peek(0).obj)));  /*((Symbol))($2.obj))*/
 		              }
break;
case 32:
//#line 128 "gramaticaGrupo10.y"
{	
		             yyval=val_peek(0);
                     st.addcambiarSigno(((Symbol)(val_peek(0).obj)));  /*((Symbol))($2.obj))*/
                    }
break;
case 33:
//#line 134 "gramaticaGrupo10.y"
{
            /* if (!((Symbol)($1.obj)).isUsada()){*/
            /*     yyerror("La variable no esta definida ",$1.getFila(),$1.getColumna());*/
            /* }*/
            yyval=val_peek(2);
            /*$$.obj = t;*/
            /*estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna()); */
    }
break;
case 34:
//#line 142 "gramaticaGrupo10.y"
{yyerror("Falta elemento de asignacion ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 35:
//#line 143 "gramaticaGrupo10.y"
{yyerror("Falta elemento de asignacion ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 36:
//#line 144 "gramaticaGrupo10.y"
{yyerror("no se encontro '=' ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 37:
//#line 155 "gramaticaGrupo10.y"
{}
break;
case 38:
//#line 156 "gramaticaGrupo10.y"
{}
break;
case 39:
//#line 160 "gramaticaGrupo10.y"
{estructuras.add("Sentencia IF Else" +" fila "+val_peek(5).getFila()+" columna "+val_peek(5).getColumna());}
break;
case 40:
//#line 161 "gramaticaGrupo10.y"
{estructuras.add("Sentencia IF " +" fila "+val_peek(3).getFila()+" columna "+val_peek(3).getColumna());}
break;
case 41:
//#line 162 "gramaticaGrupo10.y"
{yyerror(" falta la palabra reservada IF",val_peek(3).getFila(),val_peek(3).getColumna());}
break;
case 42:
//#line 163 "gramaticaGrupo10.y"
{yyerror(" Error en la construccion de la sentencia IF ",val_peek(2).getFila(),val_peek(2).getColumna());}
break;
case 43:
//#line 164 "gramaticaGrupo10.y"
{yyerror(" Falta la palabra reservada ELSE ",val_peek(3).getFila(),val_peek(3).getColumna());}
break;
case 44:
//#line 167 "gramaticaGrupo10.y"
{estructuras.add("Sentencia Loop " +" fila "+val_peek(3).getFila()+" columna "+val_peek(3).getColumna());}
break;
case 45:
//#line 168 "gramaticaGrupo10.y"
{yyerror("Linea  Falta palabra reservada UNTIL",val_peek(2).getFila(),val_peek(2).getColumna());}
break;
case 46:
//#line 171 "gramaticaGrupo10.y"
{}
break;
case 47:
//#line 172 "gramaticaGrupo10.y"
{}
break;
case 48:
//#line 173 "gramaticaGrupo10.y"
{yyerror("LInea  Omision de la palabra reservada '{' ",val_peek(2).getFila(),val_peek(2).getColumna());}
break;
case 49:
//#line 176 "gramaticaGrupo10.y"
{System.out.println("Encontro LOOP");}
break;
case 50:
//#line 180 "gramaticaGrupo10.y"
{/*#### aca hacemos el salto incondicional, debimos inventar este no terminal porque no diferenciamos bloque else de bloque if*/
        /*aca ya hicimos el pop cuando termino el cuerpo del if*/
    yyval=val_peek(0);
    /*$$.obj = t;														*/
    }
break;
case 51:
//#line 187 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
    /*$$.obj = t;*/
}
break;
case 52:
//#line 192 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
   /* $$.obj = t;*/
   	}
break;
case 53:
//#line 196 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
   /* $$.obj = t;										*/
   }
break;
case 54:
//#line 200 "gramaticaGrupo10.y"
{

    yyval=val_peek(2);
    /*$$.obj = t;										*/
    }
break;
case 55:
//#line 205 "gramaticaGrupo10.y"
{

    yyval=val_peek(2);
   /* $$.obj = t;										*/
   }
break;
case 56:
//#line 210 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
    /*$$.obj = t;										*/
    }
break;
case 57:
//#line 214 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
    /*$$.obj = t;										*/
    }
break;
case 58:
//#line 218 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>'",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 59:
//#line 219 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<'",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 60:
//#line 220 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>='",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 61:
//#line 221 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<='",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
//#line 860 "Parser.java"
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
