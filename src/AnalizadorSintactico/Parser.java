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
    0,    2,    0,    1,    1,    3,    3,    5,    5,    7,
    7,    7,    6,    6,    6,    8,    8,    4,    4,   11,
   11,   11,   12,   12,   12,   13,   13,   13,   13,   13,
    9,    9,    9,    9,   10,   10,   14,   14,   14,   14,
   14,   15,   15,   17,   17,   17,   19,   18,   16,   20,
   20,   20,   20,   20,   20,   20,   20,   20,   20,
};
final static short yylen[] = {                            2,
    1,    0,    3,    1,    2,    1,    1,    3,    1,    1,
    3,    2,    1,    1,    2,    1,    2,    2,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    2,    2,
    3,    2,    2,    2,    1,    1,    6,    4,    4,    3,
    4,    4,    3,    1,    3,    3,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    2,    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   47,   13,   14,    0,    0,    0,    1,
    0,    6,    7,    0,    0,   19,   35,   36,    0,    0,
   15,    0,    0,    0,   34,    0,   28,   26,   27,    0,
   32,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    5,    0,    0,   18,    0,    0,   44,    0,    0,    3,
   48,   40,    0,   31,   29,   30,    0,    0,    0,    0,
   58,   59,   56,   57,    0,    0,    0,    0,    0,    0,
   49,    0,   12,    8,    0,    0,    0,    0,    0,   43,
   38,   41,    0,   20,   21,   23,   24,   54,   55,   53,
   52,   50,   51,   11,   17,   46,   45,   39,   42,    0,
   37,
};
final static short yydgoto[] = {                          9,
   10,   22,   11,   47,   13,   14,   43,   76,   15,   16,
   31,   32,   33,   17,   18,   19,   48,   52,   20,   39,
};
final static short yysindex[] = {                       -29,
   -9,  -36,  -48,    0,    0,    0,  -43,  -45,    0,    0,
   23,    0,    0, -239,  -21,    0,    0,    0,  -35,  -35,
    0, -242, -218,  -35,    0,  -43,    0,    0,    0, -250,
    0,  -22,  -20,  -43,  -43,  -43,  -43,  -53,    3,   -9,
    0,  -34,  -17,    0,  -28,  -28,    0, -218,  -37,    0,
    0,    0,  -12,    0,    0,    0,  -43,  -43,  -43,  -43,
    0,    0,    0,    0,  -43,  -43,  -43,  -43,  -43,  -43,
    0, -239,    0,    0,  -28,  -78,  -75,  -35,   11,    0,
    0,    0,  -35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -215,
    0,
};
final static short yyrindex[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -5,    0,    0,    0,    0,
    0,   20,   33,    0,    0,    0,    0,    0,    0,    6,
    0,   -4,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -69,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
   46,    0,    0,   14,    0,    0,  -24,  -30,    0,    0,
   78,  -23,    0,    0,    0,   17,   19,  -19,    0,    0,
};
final static int YYTABLESIZE=298;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
    9,   30,    8,    8,    8,    9,   70,   68,   69,   72,
    8,    8,   26,   12,   37,   77,   36,   73,   24,   42,
   57,   60,   58,   50,   12,    7,   59,    8,   78,   55,
   56,    7,    7,   83,   21,   86,   87,   44,   49,   51,
    9,   74,   53,   71,   95,    9,   96,   94,    7,   97,
    8,  101,    4,   33,   10,   16,   41,    0,   75,   75,
   22,    9,    8,    0,    0,   80,    9,    0,    0,    0,
    0,   82,    0,   25,    0,   25,    0,   25,   22,   22,
   22,   22,    0,    7,    0,   38,    0,   46,   75,    0,
    0,   25,   25,   25,   25,   99,   98,    0,    0,    0,
    0,  100,    0,   54,    0,    0,    0,    0,    0,    0,
   46,   61,   62,   63,   64,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   84,   85,    0,    0,    0,    0,
    0,    0,   88,   89,   90,   91,   92,   93,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   25,    0,   65,
   66,   67,    0,   27,    0,   27,    0,   34,   35,   23,
   45,    2,    0,    3,   42,    0,    1,    2,    2,    3,
    3,   79,    4,    0,   28,   29,   28,   29,    4,    4,
    0,    5,    6,   45,    2,   51,    3,    0,    0,    0,
    0,    0,    0,    0,   81,    4,    9,    9,    0,    9,
    0,    9,    9,    0,    9,    0,    2,    0,    9,    0,
    0,    9,    9,    9,    0,    0,    9,    9,   40,    2,
    0,    3,   22,   22,   22,    0,    0,    0,    0,    0,
    4,    0,    0,    5,    6,   25,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   45,   40,   40,   40,    0,   60,   61,   62,   44,
   40,   40,   61,    0,   60,   46,   62,   42,    2,  259,
   43,   42,   45,  266,   11,   61,   47,   40,   48,  280,
  281,   61,   61,   53,   44,   59,   60,   59,   20,  258,
   40,   59,   24,   41,   75,   40,  125,   72,   61,  125,
   40,  267,    0,   59,   59,  125,   11,   -1,   45,   46,
   41,   61,   40,   -1,   -1,   49,   61,   -1,   -1,   -1,
   -1,   53,   -1,   41,   -1,   43,   -1,   45,   59,   60,
   61,   62,   -1,   61,   -1,    8,   -1,  123,   75,   -1,
   -1,   59,   60,   61,   62,   79,   78,   -1,   -1,   -1,
   -1,   83,   -1,   26,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   34,   35,   36,   37,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   57,   58,   -1,   -1,   -1,   -1,
   -1,   -1,   65,   66,   67,   68,   69,   70,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,  263,
  264,  265,   -1,  259,   -1,  259,   -1,  263,  264,  256,
  256,  257,   -1,  259,  259,   -1,  256,  257,  257,  259,
  259,  269,  268,   -1,  280,  281,  280,  281,  268,  268,
   -1,  271,  272,  256,  257,  258,  259,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  267,  268,  256,  257,   -1,  259,
   -1,  256,  257,   -1,  259,   -1,  266,   -1,  268,   -1,
   -1,  271,  272,  268,   -1,   -1,  271,  272,  256,  257,
   -1,  259,  263,  264,  265,   -1,   -1,   -1,   -1,   -1,
  268,   -1,   -1,  271,  272,  263,  264,  265,
};
}
final static short YYFINAL=9;
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

//#line 223 "gramaticaGrupo10.y"

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
    tokens.add(yylval.toString()+" fila: "+yylval.getFila());
    return a;
  }

  public Parser(LexicalAnalyzer lex,SymbolTable st, Errors er)
{
  this.lex = lex;
  this.st = st;
  this.errors=er;
}

void yyerror(String s){
    errors.setError(lex.row,s);
  }
void yyerror(String s,int row){
      errors.setError(row,s);
  }
//#line 388 "Parser.java"
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
        yyerror("ERROR: error de sintaxis en");
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
{yyerror("ERROR: No hay sentencia en");}
break;
case 3:
//#line 26 "gramaticaGrupo10.y"
{}
break;
case 4:
//#line 29 "gramaticaGrupo10.y"
{}
break;
case 5:
//#line 30 "gramaticaGrupo10.y"
{}
break;
case 6:
//#line 33 "gramaticaGrupo10.y"
{}
break;
case 7:
//#line 34 "gramaticaGrupo10.y"
{}
break;
case 8:
//#line 37 "gramaticaGrupo10.y"
{
        System.out.println("Encontro declaracion ");
		}
break;
case 9:
//#line 41 "gramaticaGrupo10.y"
{yyerror("ERROR: Declaracion mal definida en");}
break;
case 10:
//#line 54 "gramaticaGrupo10.y"
{/*  id.add( ((Symbol)($1.obj)).getLexema() );*/
                Vector<ParserVal> vect = new Vector<ParserVal>();/*$1 es el parser val con el symbolo de ese ID*/
                vect.add(val_peek(0));/*ver si anda, hay que castear a Symbol?*/
                yyval.obj = vect; }
break;
case 11:
//#line 60 "gramaticaGrupo10.y"
{/*id.add(((Symbol)($1.obj)).getLexema());*/
                    Vector<ParserVal> vect = (Vector<ParserVal>)(val_peek(0).obj); /*$3 me trae el vector original primero y desp aumenta*/
                    vect.add(val_peek(2));/*ver si anda, hay que castear a Symbol? .obj*/
                    yyval.obj = vect;
	}
break;
case 12:
//#line 66 "gramaticaGrupo10.y"
{yyerror("ERROR: Se esperaba ';' en",val_peek(1).getFila());}
break;
case 13:
//#line 69 "gramaticaGrupo10.y"
{yyval.sval="longint";}
break;
case 14:
//#line 70 "gramaticaGrupo10.y"
{yyval.sval="float";}
break;
case 15:
//#line 71 "gramaticaGrupo10.y"
{yyerror("ERROR: Tipo indefinido en",val_peek(1).getFila());}
break;
case 16:
//#line 74 "gramaticaGrupo10.y"
{}
break;
case 17:
//#line 75 "gramaticaGrupo10.y"
{}
break;
case 18:
//#line 78 "gramaticaGrupo10.y"
{}
break;
case 19:
//#line 79 "gramaticaGrupo10.y"
{/*#######Solo llego aca si termino un if o un loop*/
          }
break;
case 20:
//#line 85 "gramaticaGrupo10.y"
{            
                yyval=val_peek(2);
               /* $$.obj = t;*/
}
break;
case 21:
//#line 89 "gramaticaGrupo10.y"
{			    
            yyval=val_peek(2);
            /*$$.obj = t;*/
}
break;
case 22:
//#line 93 "gramaticaGrupo10.y"
{yyval=val_peek(0);
         yyval.obj=val_peek(0).obj; /*VER*/
}
break;
case 23:
//#line 99 "gramaticaGrupo10.y"
{
yyval=val_peek(2);
/*$$.obj = t;*/

}
break;
case 24:
//#line 104 "gramaticaGrupo10.y"
{
              
yyval=val_peek(2);
/*$$.obj = t;*/
    }
break;
case 25:
//#line 109 "gramaticaGrupo10.y"
{yyval=val_peek(0);
			 /* $$.obj=$1.obj;*/
			 }
break;
case 26:
//#line 114 "gramaticaGrupo10.y"
{yyval=val_peek(0);}
break;
case 27:
//#line 115 "gramaticaGrupo10.y"
{yyval=val_peek(0);}
break;
case 28:
//#line 116 "gramaticaGrupo10.y"
{if(!((Symbol)(val_peek(0).obj)).isUsada()){
			/*error*/
			yyerror("ERROR: variable no declarada en",val_peek(0).getFila());
			}
			 yyval=val_peek(0);
	}
break;
case 29:
//#line 122 "gramaticaGrupo10.y"
{
					  yyval=val_peek(0);
                      st.addcambiarSigno(((Symbol)(val_peek(0).obj)));  /*((Symbol))($2.obj))*/
 		              }
break;
case 30:
//#line 126 "gramaticaGrupo10.y"
{	
		             yyval=val_peek(0);
                     st.addcambiarSigno(((Symbol)(val_peek(0).obj)));  /*((Symbol))($2.obj))*/
                    }
break;
case 31:
//#line 132 "gramaticaGrupo10.y"
{
            /* if (!((Symbol)($1.obj)).isUsada()){*/
            /*     yyerror("La variable no esta definida ",$1.getFila());*/
            /* }*/
            yyval=val_peek(2);
            /*$$.obj = t;*/
            /*estructuras.add("Asignacion "+" fila "+$1.getFila());*/
    }
break;
case 32:
//#line 140 "gramaticaGrupo10.y"
{yyerror("ERROR: Falta elemento de asignacion en",val_peek(1).getFila());}
break;
case 33:
//#line 141 "gramaticaGrupo10.y"
{yyerror("ERROR: Falta elemento de asignacion en",val_peek(1).getFila());}
break;
case 34:
//#line 142 "gramaticaGrupo10.y"
{yyerror("ERROR: no se encontro '=' en",val_peek(1).getFila());}
break;
case 35:
//#line 153 "gramaticaGrupo10.y"
{}
break;
case 36:
//#line 154 "gramaticaGrupo10.y"
{}
break;
case 37:
//#line 158 "gramaticaGrupo10.y"
{estructuras.add("Sentencia IF Else" +" fila "+val_peek(5).getFila());}
break;
case 38:
//#line 159 "gramaticaGrupo10.y"
{estructuras.add("Sentencia IF " +" fila "+val_peek(3).getFila());}
break;
case 39:
//#line 160 "gramaticaGrupo10.y"
{yyerror(" falta la palabra reservada IF",val_peek(3).getFila());}
break;
case 40:
//#line 161 "gramaticaGrupo10.y"
{yyerror(" Error en la construccion de la sentencia IF ",val_peek(2).getFila());}
break;
case 41:
//#line 162 "gramaticaGrupo10.y"
{yyerror(" Falta la palabra reservada ELSE ",val_peek(3).getFila());}
break;
case 42:
//#line 165 "gramaticaGrupo10.y"
{estructuras.add("Sentencia Loop " +" fila "+val_peek(3).getFila());}
break;
case 43:
//#line 166 "gramaticaGrupo10.y"
{yyerror("Linea  Falta palabra reservada UNTIL",val_peek(2).getFila());}
break;
case 44:
//#line 169 "gramaticaGrupo10.y"
{}
break;
case 45:
//#line 170 "gramaticaGrupo10.y"
{}
break;
case 46:
//#line 171 "gramaticaGrupo10.y"
{yyerror("LInea  Omision de la palabra reservada '{' ",val_peek(2).getFila());}
break;
case 47:
//#line 174 "gramaticaGrupo10.y"
{System.out.println("Encontro LOOP");}
break;
case 48:
//#line 178 "gramaticaGrupo10.y"
{/*#### aca hacemos el salto incondicional, debimos inventar este no terminal porque no diferenciamos bloque else de bloque if*/
        /*aca ya hicimos el pop cuando termino el cuerpo del if*/
    yyval=val_peek(0);
    /*$$.obj = t;														*/
    }
break;
case 49:
//#line 185 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
    /*$$.obj = t;*/
}
break;
case 50:
//#line 190 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
   /* $$.obj = t;*/
   	}
break;
case 51:
//#line 194 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
   /* $$.obj = t;										*/
   }
break;
case 52:
//#line 198 "gramaticaGrupo10.y"
{

    yyval=val_peek(2);
    /*$$.obj = t;										*/
    }
break;
case 53:
//#line 203 "gramaticaGrupo10.y"
{

    yyval=val_peek(2);
   /* $$.obj = t;										*/
   }
break;
case 54:
//#line 208 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
    /*$$.obj = t;										*/
    }
break;
case 55:
//#line 212 "gramaticaGrupo10.y"
{
    yyval=val_peek(2);
    /*$$.obj = t;										*/
    }
break;
case 56:
//#line 216 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>'",val_peek(1).getFila());}
break;
case 57:
//#line 217 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<'",val_peek(1).getFila());}
break;
case 58:
//#line 218 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>='",val_peek(1).getFila());}
break;
case 59:
//#line 219 "gramaticaGrupo10.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<='",val_peek(1).getFila());}
break;
//#line 846 "Parser.java"
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
