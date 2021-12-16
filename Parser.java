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






//#line 2 "proyecto.y"

    import java.lang.Math;
    import java.util.StringTokenizer;
    import java.io.*;
    import Config.Config;
//#line 23 "Parser.java"




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
public final static short COMPARACION=257;
public final static short DIFERENTES=258;
public final static short MAYOR=259;
public final static short MENOR=260;
public final static short MAYORIGUAL=261;
public final static short MENORIGUAL=262;
public final static short AND=263;
public final static short OR=264;
public final static short IF=265;
public final static short ELSE=266;
public final static short WHILE=267;
public final static short FOR=268;
public final static short FUNCT=269;
public final static short FUNC=270;
public final static short RETURN=271;
public final static short PARAM=272;
public final static short PROC=273;
public final static short NUMBER=274;
public final static short VAR=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    1,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    5,    5,
    5,    6,    3,    3,    3,    3,    3,    3,    3,   15,
   14,   12,    4,   13,    7,    8,    9,   10,   11,   11,
   11,
};
final static short yylen[] = {                            2,
    0,    2,    3,    2,    1,    3,    2,    1,    2,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    1,    4,    0,    1,
    3,    0,   13,   11,   10,   16,    8,    8,    5,    1,
    1,    1,    1,    0,    0,    1,    1,    1,    0,    1,
    3,
};
final static short yydefred[] = {                         1,
    0,   46,   47,   48,   40,   42,    0,   27,   41,   10,
    0,    0,    0,    2,    0,    0,    0,    5,    0,    0,
    0,    0,    0,    0,    0,    0,   26,    0,    0,   25,
    0,    3,    0,    7,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    4,    0,    0,    0,
    0,   43,    0,    0,    0,    0,    0,   16,    0,    6,
    0,    0,   21,   19,   22,   20,   23,   24,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   28,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   39,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   37,   38,    0,   32,
   45,    0,    0,   45,   35,    0,    0,   34,    0,    0,
    0,   33,    0,    0,   45,   36,
};
final static short yydgoto[] = {                          1,
   16,   17,   18,   19,   74,  124,   59,   20,   21,   22,
   78,   23,  111,   24,   25,
};
final static short yysindex[] = {                         0,
  -10,    0,    0,    0,    0,    0,  -26,    0,    0,    0,
  -58,  -26,  -26,    0,  -26,    1,   38,    0,  -24,  -18,
    5,   15, -269, -269,  -67,  -26,    0,  -26,  -38,    0,
   28,    0,   46,    0,  -26,  -26,  -26,  -26,  -26,  -26,
  -26,  -26,  -26,  -26,  -26,  -26,    0,  -26,  -26,  -26,
  -26,    0,   32,   36,  -26,   28,   56,    0,   -3,    0,
  -54,  -90,    0,    0,    0,    0,    0,    0,  -38,  -38,
  -61,  -61,   56,  -29,   56,   56,   56,   -4,   27,   33,
  -34,  -45,    0,  -26,   45,   49,  -26,   35,  -19,  -12,
   60,  -32,   56,    2,    8,   56,  -26,  -32,  -32,    0,
  -32,  -32,  -32,   56,  -32,  -32,   14,  -32,  -32,   62,
   24,   25, -143,   26,   30,  -26,    0,    0,   10,    0,
    0,   -4,  -32,    0,    0,   87,  -32,    0,   29,   37,
  -32,    0,  -32,   39,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   97,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -2,    0,    0,
  -27,    0,    0,    0,  -11,    0,   43,    0,    0,    0,
   73,   65,    0,    0,    0,    0,    0,    0,  102,  134,
  -16,    7,    3,    0,  112,  112,  -39,   98,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   51,    0,    0,  -23,    0,    0,    0,    0,
   40,    0,    0,   98,   57,   57,    0,   40,   40,    0,
    0,    0,    0,    0,    0,   12,    0,    0,    0,    0,
    0,  112,    0,    0,    0,    0,   40,    0,    0,    0,
    0,    0,   40,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  250,  319,   21,   34,  104,    0,   59,    0,    0,    0,
   68,    0,   70,    0,    0,
};
final static int YYTABLESIZE=452;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         14,
   13,   50,   28,   45,   50,   52,   13,   15,   46,   84,
   32,   83,   12,   26,   84,   48,   49,   51,   12,   50,
   51,   49,   13,   55,   12,   12,   12,   12,   12,   15,
   12,   49,   29,   13,   12,   51,   34,   82,   29,   87,
   15,   29,   12,   30,   50,   12,   30,   13,   13,   13,
   13,   13,   49,   13,   51,   49,   53,   54,   91,   43,
    8,    8,    8,    8,    8,   13,    8,   89,   58,   45,
   43,   79,   44,   90,   46,   80,   12,   92,    8,   45,
   43,   29,   44,   11,   46,   94,   11,   45,   43,   95,
   44,   31,   46,   97,   31,   30,   47,   45,   43,   13,
   44,   11,   46,   98,   60,   18,   18,   18,   18,   18,
   99,   18,    8,   17,   17,   17,   17,   17,  100,   17,
  116,   34,  119,   18,  102,   34,   34,  129,   34,   34,
  103,   17,  123,   85,   86,   11,   88,    9,  113,    9,
    9,    9,   14,   31,   14,   14,   14,   34,  117,  118,
  120,  131,   45,   34,  121,    9,   45,   18,   81,  107,
   14,  132,  110,  135,   45,   17,  114,  115,   37,   38,
   39,   40,   41,   42,   15,  112,   15,   15,   15,  125,
  126,   44,  128,  122,    0,  130,    0,    0,    0,    9,
    0,  134,   15,  136,   14,   35,   36,   37,   38,   39,
   40,   41,   42,   36,   37,   38,   39,   40,   41,   42,
    0,    0,    0,    0,    0,    0,    0,    0,   35,   36,
   37,   38,   39,   40,   41,   42,   15,    0,    0,    0,
    0,    0,    2,    0,    3,    4,    5,    6,    7,    8,
    9,   10,   11,    0,    7,    8,    0,   10,   11,    0,
    0,    0,    0,    0,    2,    0,    3,    4,    5,    6,
    7,    8,    9,   10,   11,    2,    0,    3,    4,    5,
    6,    7,    8,    9,   10,   11,    8,    8,    8,    8,
    8,    8,    8,    8,   35,   36,   37,   38,   39,   40,
   41,   42,    0,    0,   35,   36,   37,   38,   39,   40,
   41,   42,   35,   36,   37,   38,   39,   40,   41,   42,
    0,    0,   35,   36,   37,   38,   39,   40,   41,   42,
    0,   18,   18,    0,    0,   27,    0,    0,    0,   17,
   29,   30,    0,   31,   33,    0,    0,    0,    0,    0,
    0,  101,    0,    0,   56,    0,   57,  105,  106,    0,
    0,  108,  109,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   72,    0,   73,   75,   76,   77,
    0,    0,  127,   73,    0,    0,    0,    0,    0,    0,
  133,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   93,    0,    0,   96,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  104,    0,    0,    0,   33,
    0,    0,    0,   33,   33,    0,   33,   33,    0,    0,
    0,    0,    0,    0,   77,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,   33,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   33,   41,   61,   42,   44,  275,   33,   40,   47,   44,
   10,   41,   45,   40,   44,   40,   44,   41,   45,   59,
   44,   40,   33,   91,   41,   42,   43,   44,   45,   40,
   47,   59,   44,   33,   45,   59,   16,   41,   41,   44,
   40,   44,   59,   41,   40,   45,   44,   41,   42,   43,
   44,   45,   41,   47,   40,   44,   23,   24,   93,   40,
   41,   42,   43,   44,   45,   59,   47,   41,   41,   42,
   43,   40,   45,   41,   47,   40,   93,  123,   59,   42,
   43,   93,   45,   41,   47,   41,   44,   42,   43,   41,
   45,   41,   47,   59,   44,   93,   59,   42,   43,   93,
   45,   59,   47,  123,   59,   41,   42,   43,   44,   45,
  123,   47,   93,   41,   42,   43,   44,   45,   59,   47,
   59,  101,  266,   59,  123,  105,  106,   41,  108,  109,
  123,   59,  123,   75,   76,   93,   78,   41,  125,   43,
   44,   45,   41,   93,   43,   44,   45,  127,  125,  125,
  125,  123,   41,  133,  125,   59,   59,   93,   55,  101,
   59,  125,  104,  125,  125,   93,  108,  109,  259,  260,
  261,  262,  263,  264,   41,  106,   43,   44,   45,  121,
  122,  125,  124,  116,   -1,  127,   -1,   -1,   -1,   93,
   -1,  133,   59,  135,   93,  257,  258,  259,  260,  261,
  262,  263,  264,  258,  259,  260,  261,  262,  263,  264,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,  264,   93,   -1,   -1,   -1,
   -1,   -1,  265,   -1,  267,  268,  269,  270,  271,  272,
  273,  274,  275,   -1,  271,  272,   -1,  274,  275,   -1,
   -1,   -1,   -1,   -1,  265,   -1,  267,  268,  269,  270,
  271,  272,  273,  274,  275,  265,   -1,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  257,  258,  259,  260,
  261,  262,  263,  264,  257,  258,  259,  260,  261,  262,
  263,  264,   -1,   -1,  257,  258,  259,  260,  261,  262,
  263,  264,  257,  258,  259,  260,  261,  262,  263,  264,
   -1,   -1,  257,  258,  259,  260,  261,  262,  263,  264,
   -1,  257,  258,   -1,   -1,    7,   -1,   -1,   -1,  257,
   12,   13,   -1,   15,   16,   -1,   -1,   -1,   -1,   -1,
   -1,   92,   -1,   -1,   26,   -1,   28,   98,   99,   -1,
   -1,  102,  103,   35,   36,   37,   38,   39,   40,   41,
   42,   43,   44,   45,   46,   -1,   48,   49,   50,   51,
   -1,   -1,  123,   55,   -1,   -1,   -1,   -1,   -1,   -1,
  131,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   84,   -1,   -1,   87,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   97,   -1,   -1,   -1,  101,
   -1,   -1,   -1,  105,  106,   -1,  108,  109,   -1,   -1,
   -1,   -1,   -1,   -1,  116,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  127,   -1,   -1,   -1,   -1,
   -1,  133,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'",null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"COMPARACION","DIFERENTES","MAYOR",
"MENOR","MAYORIGUAL","MENORIGUAL","AND","OR","IF","ELSE","WHILE","FOR","FUNCT",
"FUNC","RETURN","PARAM","PROC","NUMBER","VAR",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list comando '\\n'",
"comando : expresion ';'",
"comando : stmt",
"comando : comando expresion ';'",
"comando : comando stmt",
"expresion : VAR",
"expresion : '-' expresion",
"expresion : NUMBER",
"expresion : VAR '=' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : '(' expresion ')'",
"expresion : expresion COMPARACION expresion",
"expresion : expresion DIFERENTES expresion",
"expresion : expresion MENOR expresion",
"expresion : expresion MENORIGUAL expresion",
"expresion : expresion MAYOR expresion",
"expresion : expresion MAYORIGUAL expresion",
"expresion : expresion AND expresion",
"expresion : expresion OR expresion",
"expresion : '!' expresion",
"expresion : RETURN expresion",
"expresion : PARAM",
"expresion : nombreProcedimiento '(' arglist ')'",
"arglist :",
"arglist : expresion",
"arglist : arglist ',' expresion",
"no :",
"stmt : '(' expresion stop ')' '{' comando stop '}' ELSE '{' comando stop '}'",
"stmt : if '(' expresion stop ')' '{' comando stop '}' no stop",
"stmt : while '(' expresion stop ')' '{' comando stop '}' stop",
"stmt : for '(' instr stop ';' expresion stop ';' instr stop ')' '{' comando stop '}' stop",
"stmt : funcion nombreProcedimiento '(' ')' '{' comando null '}'",
"stmt : proc nombreProcedimiento '(' ')' '{' comando null '}'",
"stmt : instrr '[' arglist ']' ';'",
"instrr : FUNCT",
"proc : PROC",
"funcion : FUNC",
"nombreProcedimiento : VAR",
"null :",
"stop :",
"if : IF",
"while : WHILE",
"for : FOR",
"instr :",
"instr : expresion",
"instr : instr ',' expresion",
};

//#line 189 "proyecto.y"


TablaSimb tablaSimbol = new TablaSimb();
MaquinaPila maquina = new MaquinaPila(tablaSimbol);
int i = 0, j = 0;
double [][] aux;
Func funcaux;
boolean error, newline;
String ins;
StringTokenizer st;

void yyerror(String s){
    error = true;
    System.out.println("error: "+s);
    System.exit(0);
}
int yylex(){
    String cad;
    int tok = 0;
    Double d;
    if(!st.hasMoreTokens()){
        if(!newline){
            newline = true;
            return '\n';
        }else
            return 0;
    }
    cad = st.nextToken();
    try{
        d = Double.valueOf(cad);
        yylval = new ParserVal(d.doubleValue());
        return NUMBER;
    }catch(Exception e){}

    if(esVar(cad)){
        if(cad.equals("proc")){
            return PROC;
        }
        if(cad.charAt(0) == '$'){
            yylval = new ParserVal((int)Integer.parseInt(cadena.substring(1)));
			return PARAM;
        }
        if(cad.equals("return")){
            return RETURN;
        }
        if(cad.equals("func")){
            return FUNC;
        }
        if(cad.equals("if")){
            return IF;
        }
        if(cad.equals("else")){
            return ELSE;
        }
        if(cad.equals("while")){
            return WHILE;
        }
        if(cad.equals("for")){
            return FOR;
        }
        boolean esFunc = false;
        Object objeto = tablaSimbol.encontrar(cadena);
        if(objeto instanceof Funcion){
            funcaux = (Func)objeto;
            yylval = new ParserVal(objeto);
            esFunc = true;
            return FUNCT;
        }
        if(!esFunc){
            yylval = new ParserVal(cadena);
            return VAR;
        }
    }else{
        if(cad.equals("==")){
            return COMPARACION;
        }
        if(cad.equals("!=")){
            return DIFERENTES;
        }
        if(cad.equals("<")){
            return MENOR;
        }
        if(cad.equals("<=")){
            return MENORIGUAL;
        }
        if(cad.equals(">=")){
            return MAYORIGUAL;
        }
        if(cad.equals(">")){
            return MAYOR;
        }
        if(cad.equals("&&")){
            return AND;
        }
        if(cad.equals("||")){
            return OR;
        }
        tok = cad.charAt(0);
    }
    return tok;
    String resv[] = {">", ">=", "&&", "||", "<", "<=", "==", "!=", "=", "{", "}", "(", ")", ",", "*", "+", "-", "|", "[", "]", ";", "/", "!"};
    public String ajustCad(String cad){
        String nueva = "";
        boolean encont = false;
        for(int i = 0; i<cad.length; i++){
            encont = false;
            for(int j = 0; j<resv.length; j++){
                if(cad.substring(i, i+resv[j].length()).equals(resv[j])){
                    i+=resv[j].length()-1;
                    nueva += " "+resv[j]+" ";
                    encont = true;
                    break;
                }
            }
            if(!encont){
                nueva += cad.charAt(i);
            }
        }
        nueva += cad.charAt(cad.length()-1);
        return nueva;
    }
    boolean esVar(String cad){
        boolean valido = true;
        for(int i = 0; i<resv.length; i++){
            if(cad.equals(resv[i])){
                valido = false;
            }
        }
        return valido;
    }
    public void insertInstr(){
        tablaSimbol.insert("TURN", new MaquinaPila.Girar());
        tablaSimbol.insert("FORWARD", new MaquinaPila.Avanza());
        tablaSimbol.insert("COLOR", new MaquinaPila.Color());
        tablaSimbol.insert("PenUP", new MaquinaPila.SubirPincel());
        tablaSimbol.insert("penDOWN", new MaquinaPila.BajarPincel());
    }
    public Config ejecutarCodigo(String cad){
        st = new StringTokenizer(ajustCad(cad));
        newline = false;
        yyparse();
        if(!error)
            maquina.ejecuta();
        return maquina.getConfig();
    }
    public boolean compilar(String code){
        st = new StringTokenizer(ajustCad(code));
        newline = false;
        yyparse();
        return !error;
    }
    public boolean ejecutaSig(){
        return maquina.ejecutaSig();
    }
    public Config getConfig(){
        return maquina.getConfig();
    }
    public void limpia(){
        tablaSimbol = new TablaSimb();
        insertInstr();
        maquina = new MaquinaPila(tablaSimbol);
    }
    public Config ejecuta(){
        maquina.ejecuta();
        return maquina.getConfig();
    }
    void dotest() throws Exception{
        insertInstr();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            error = false;
            try{
                 ins = ajustCad(in.readLine());
            }catch(Exception e){}
            st = new StringTokenizer(ins);
            newline = false;
            yyparse();
            if(!error)
                maquina.ejecuta();
        }
    }
}

public static void main(String args[])throws Exception{
    Parse par = new Parser(false);
    par.dotest();
}
//#line 555 "Parser.java"
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
case 4:
//#line 30 "proyecto.y"
{yyval = val_peek(1);}
break;
case 5:
//#line 31 "proyecto.y"
{yyval = val_peek(0);}
break;
case 6:
//#line 32 "proyecto.y"
{yyval = val_peek(2);}
break;
case 7:
//#line 33 "proyecto.y"
{yyval = val_peek(1);}
break;
case 8:
//#line 35 "proyecto.y"
{
                yyval = new ParserVal(maquina.agregarOpera("varPevaluar"));
                maquina.agregar(val_peek(0).sval);
                }
break;
case 9:
//#line 39 "proyecto.y"
{
                yyval = new ParserVal(maquina.agregarOpera("neg"));
                }
break;
case 10:
//#line 42 "proyecto.y"
{
                 yyval = new ParserVal(maquina.agregarOpera("constPush"));
                 maquina.agregar(val_peek(0).dval);
                 }
break;
case 11:
//#line 46 "proyecto.y"
{
                            yyval = new ParserVal(val_peek(0).ival);
                            maquina.agregarOpera("varPush");
                            maquina.agregar(val_peek(2).sval);
                            maquina.agregarOpera("asign");
                            maquina.agregarOpera("varPevaluar");
                            maquina.agregar(val_peek(2).sval);
                           }
break;
case 12:
//#line 54 "proyecto.y"
{
                                yyval = new ParserVal(val_peek(2).ival);
                                maquina.agregarOpera("MULTIPLICACION");
                                }
break;
case 13:
//#line 58 "proyecto.y"
{
                                yyval = new ParserVal(val_peek(2).ival);
                                maquina.agregarOpera("DIVISION");
                                }
break;
case 14:
//#line 62 "proyecto.y"
{
                                yyval = new ParserVal(val_peek(2).ival);
                                maquina.agregarOpera("SUMA");
                                }
break;
case 15:
//#line 66 "proyecto.y"
{
                                yyval = new ParserVal(val_peek(2).ival);
                                maquina.agregarOpera("RESTA");
                                }
break;
case 16:
//#line 70 "proyecto.y"
{
                            yyval = new ParserVal(#2.ival);
                            }
break;
case 17:
//#line 73 "proyecto.y"
{
                                        maquina.agregarOpera("EQ");/*IGUAL
*/
                                        yyval = val_peek(2);
                                        }
break;
case 18:
//#line 77 "proyecto.y"
{
                                        maquina.agregarOpera("NE");/*DIFERENTE NOT EQUALS
*/
                                        yyval = val_peek(2);
                                        }
break;
case 19:
//#line 81 "proyecto.y"
{
                                    maquina.agregarOpera("LE");/*MENOR QUE
*/
                                    yyval = val_peek(2);
                                    }
break;
case 20:
//#line 85 "proyecto.y"
{
                                        maquina.agregarOpera("LQ");/*MENOR IGUAL QUE
*/
                                        yyval = val_peek(2);
                                        }
break;
case 21:
//#line 89 "proyecto.y"
{
                                    maquina.agregarOpera("GT");/*MAYOR QUE
*/
                                    yyval = val_peek(2);
                                    }
break;
case 22:
//#line 93 "proyecto.y"
{
                                        maquina.agregarOpera("GE");/*MAYOR IGUAL QUE
*/
                                        yyval = val_peek(2);
                                        }
break;
case 23:
//#line 97 "proyecto.y"
{
                                    maquina.agregarOpera("AND");
                                        yyval = val_peek(2);
                                    }
break;
case 24:
//#line 101 "proyecto.y"
{
                                maquina.agregarOpera("OR");
                                yyval = val_peek(2);
                                }
break;
case 25:
//#line 105 "proyecto.y"
{
                        maquina.agregarOpera("NOT");
                        yyval = val_peek(0);
                        }
break;
case 26:
//#line 109 "proyecto.y"
{ yyval = val_peek(0); maquina.agregarOpera("RETURN_"); }
break;
case 27:
//#line 110 "proyecto.y"
{yyval = new ParserVal(maquina.agregarOpera("pushParam")); maquina.agregar((int) val_peek(0).ival);}
break;
case 28:
//#line 111 "proyecto.y"
{yyval = new ParserVal(maquina.agregarOperaen("invocar", (val_peek(3).ival))); maquina.agregar(null); }
break;
case 30:
//#line 114 "proyecto.y"
{yyval = val_peek(0); maquna.agregar("stop");}
break;
case 31:
//#line 115 "proyecto.y"
{yyval = val_peek(2); maquina.agregar("stop");}
break;
case 32:
//#line 117 "proyecto.y"
{yyval = new ParserVal(maquina.agregarOpera("no"));}
break;
case 33:
//#line 120 "proyecto.y"
{yyval = val_peek(12); maquina.agregar(val_peek(6).ival, val_peek(12).ival + 1);
                                                                                maquina.agregar(val_peek(1).ival, val_peek(12).ival + 2);
                                                                                maquina.agregar(maquina.numeroElementos()-1, val_peek(12).ival + 3);
                                                                                }
break;
case 34:
//#line 124 "proyecto.y"
{
                                                            yyval = val_peek(10);
                                                            maquina.agregar(val_peek(4).ival, val_peek(10).ival +1);
                                                            maquina.agregar(val_peek(1).ival, val_peek(10).ival +2);
                                                            maquina.agregar(maquina.numeroElementos() -1, val_peek(10).ival +3);
                                                            }
break;
case 35:
//#line 130 "proyecto.y"
{
                                                                yyval = val_peek(9);
                                                                maquina.agregar(val_peek(3).ival, val_peek(9).ival +1);
                                                                maquina.agregar(val_peek(0).ival, val_peek(9).ival +2);
                                                            }
break;
case 36:
//#line 135 "proyecto.y"
{
                                                                                        yyval = val_peek(15);
                                                                                        maquina.agregar(val_peek(10).ival, val_peek(15).ival + 1);
                                                                                        maquina.agregar(val_peek(7).ival, val_peek(15).ival + 2);
                                                                                        maquina.agregar(val_peek(3).ival, val_peek(15).ival + 3);
                                                                                        maquina.agregar(val_peek(0).ival, val_peek(15).ival + 4);
                                                                                        }
break;
case 39:
//#line 144 "proyecto.y"
{
                                yyval = new ParserVal(val_peek(4).ival);
                                maquina.agregar(null);
                                }
break;
case 40:
//#line 149 "proyecto.y"
{
            yyval = new ParserVal(maquina.agregar((funcion)(val_peek(0).obj)));
            }
break;
case 41:
//#line 153 "proyecto.y"
{maquina.agregarOpera("dec");}
break;
case 42:
//#line 155 "proyecto.y"
{maquina.agregarOpera("dec");}
break;
case 43:
//#line 157 "proyecto.y"
{yyval = new ParserVal(maquina.agregar(val_peek(0).sval));}
break;
case 44:
//#line 159 "proyecto.y"
{maquina.agregar(null);}
break;
case 45:
//#line 161 "proyecto.y"
{yyval = new ParserVal(maquina.agregarOpera("stop"));}
break;
case 46:
//#line 163 "proyecto.y"
{
    yyval = new ParserVal(maquina.agregarOpera("IFelse"));
    maquina.agregarOpera("stop");/*THEN
*/
    maquina.agregarOpera("stop");/*ELSE
*/
    maquina.agregarOpera("stop");
    }
break;
case 47:
//#line 170 "proyecto.y"
{
    yyval = new ParserVal(maquina.agregarOpera("WHILE"));
    maquina.agregarOpera("stop");/*codigo
*/
    maquina.agregarOpera("stop");/*final
*/
    }
break;
case 48:
//#line 176 "proyecto.y"
{
    yyval = new ParserVal(maquina.agregarOpera("FOR"));
    maquina.agregarOpera("stop");
    maquina.agregarOpera("stop");
    maquina.agregarOpera("stop");
    maquina.agregarOpera("stop");
    }
break;
case 49:
//#line 184 "proyecto.y"
{yyval = new ParserVal(maquina.agregarOpera("no"));}
break;
case 50:
//#line 185 "proyecto.y"
{yyval = val_peek(0);}
break;
case 51:
//#line 186 "proyecto.y"
{yyval = val_peek(2);}
break;
//#line 978 "Parser.java"
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
