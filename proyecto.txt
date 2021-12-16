/*
*   Carrillo Rodríguez Antonio
*   Hernández Clemente Samantha
*   Leal Avilés Carlos David
*   Tostado Navarro Jesús Maximiliano
*   Proyecto - LOGOS
*   3CM17
*   COMPILADORES
*/
//Archivo yacc para el proyecto, contiene todas las producciones y codigo de soporte
%{
    import java.lang.Math;
    import java.util.StringTokenizer;
    import java.io.*;
%}
//creación de los tokens
//OPERACIONES DE IGUALACIÓN
%token COMPARACION, DIFERENTES, MAYOR, MENOR, MAYORIGUAL, MENORIGUAL, AND, OR
//CICLOS Y CONDICIONES
%token IF, ELSE, WHILE, FOR
//FUNCIONES Y PROCEDIMIENTOS
%token FUNCT, FUNC, RETURN, PARAM, PROC
%token NUMBER, VAR
//OPERADORES
%right '='
%left '+' '-'
%left '*' '/'
%left ';'
%left COMPARACION
%left DIFERENTES
%left MAYOR, MAYORIGUAL, MENOR, MENORIGUAL, '!', AND, OR
%right RETURN
%%
list:
    | list '\n'
    | list comando '\n'
    ;
comando: expresion ';' {$$ = $1;}
       | stmt {$$ = $1;}
       | comando expresion ';' {$$ = $1;}
       | comando stmt {$$ = $1;}
       ;
expresion: VAR{
                $$ = new ParserVal(maquina.agregarOpera("varPevaluar"));
                maquina.agregar($1.sval);
                }
         | '-' expresion{
                $$ = new ParserVal(maquina.agregarOpera("neg"));
                }
        | NUMBER {
                 $$ = new ParserVal(maquina.agregarOpera("constPush"));
                 maquina.agregar($1.dval);
                 }
        | VAR '=' expresion{
                            $$ = new ParserVal($3.ival);
                            maquina.agregarOpera("varPush");
                            maquina.agregar($1.sval);
                            maquina.agregarOpera("asign");
                            maquina.agregarOpera("varPevaluar");
                            maquina.agregar($1.sval);
                           }
        | expresion '*' expresion{
                                $$ = new ParserVal($1.ival);
                                maquina.agregarOpera("MULTIPLICACION");
                                }
        | expresion '/' expresion{
                                $$ = new ParserVal($1.ival);
                                maquina.agregarOpera("DIVISION");
                                }
        | expresion '+' expresion{
                                $$ = new ParserVal($1.ival);
                                maquina.agregarOpera("SUMA");
                                }
        | expresion '-' expresion{
                                $$ = new ParserVal($1.ival);
                                maquina.agregarOpera("RESTA");
                                }
        | '(' expresion ')' {
                            $$ = new ParserVal(#2.ival);
                            }
        | expresion COMPARACION expresion{
                                        maquina.agregarOpera("EQ");//IGUAL
                                        $$ = $1;
                                        }
        | expresion DIFERENTES expresion{
                                        maquina.agregarOpera("NE");//DIFERENTE NOT EQUALS
                                        $$ = $1;
                                        }
        | expresion MENOR expresion {
                                    maquina.agregarOpera("LE");//MENOR QUE
                                    $$ = $1;
                                    }
        | expresion MENORIGUAL expresion {
                                        maquina.agregarOpera("LQ");//MENOR IGUAL QUE
                                        $$ = $1;
                                        }
        | expresion MAYOR expresion {
                                    maquina.agregarOpera("GT");//MAYOR QUE
                                    $$ = $1;
                                    }
        | expresion MAYORIGUAL expresion {
                                        maquina.agregarOpera("GE");//MAYOR IGUAL QUE
                                        $$ = $1;
                                        }
        | expresion AND expresion {
                                    maquina.agregarOpera("AND");
                                        $$ = $1;
                                    }
        | expresion OR expresion {
                                maquina.agregarOpera("OR");
                                $$ = $1;
                                }
        | '!' expresion {
                        maquina.agregarOpera("NOT");
                        $$ = $2;
                        }
        | RETURN expresion { $$ = $2; maquina.agregarOpera("RETURN_"); }
        | PARAM {$$ = new ParserVal(maquina.agregarOpera("pushParam")); maquina.agregar((int) $1.ival);}
        | nombreProcedimiento '(' arglist ')' {$$ = new ParserVal(maquina.agregarOperaen("invocar", ($1.ival))); maquina.agregar(null); }//definicion del procedimiento para el uso de argumentos
        ;
arglist:
       | expresion {$$ = $1; maquna.agregar("stop");}
       | arglist ',' expresion {$$ = $1; maquina.agregar("stop");}
       ;
no: {$$ = new ParserVal(maquina.agregarOpera("no"));}
  ;
stmt:
 '(' expresion stop')' '{' comando stop '}' ELSE '{' comando stop '}' {$$ = $1; maquina.agregar($7.ival, $1.ival + 1);
                                                                                maquina.agregar($12.ival, $1.ival + 2);
                                                                                maquina.agregar(maquina.numeroElementos()-1, $1.ival + 3);
                                                                                }
     | if '(' expresion stop')' '{' comando stop '}' no stop {
                                                            $$ = $1;
                                                            maquina.agregar($7.ival, $1.ival +1);
                                                            maquina.agregar($10.ival, $1.ival +2);
                                                            maquina.agregar(maquina.numeroElementos() -1, $1.ival +3);
                                                            }
     | while '(' expresion stop ')' '{' comando stop '}' stop{
                                                                $$ = $1;
                                                                maquina.agregar($7.ival, $1.ival +1);
                                                                maquina.agregar($10.ival, $1.ival +2);
                                                            } 
     | for '(' instr stop ';' expresion stop ';' instr stop')''{' comando stop '}' stop{
                                                                                        $$ = $1;
                                                                                        maquina.agregar($6.ival, $1.ival + 1);
                                                                                        maquina.agregar($9.ival, $1.ival + 2);
                                                                                        maquina.agregar($13.ival, $1.ival + 3);
                                                                                        maquina.agregar($16.ival, $1.ival + 4);
                                                                                        }
     | funcion nombreProcedimiento '('')''{'comando null '}'
     | proc nombreProcedimiento '('')''{' comando null '}'
     | instrr '[' arglist ']' ';'{
                                $$ = new ParserVal($1.ival);
                                maquina.agregar(null);
                                }
     ;
instrr: FUNCT{
            $$ = new ParserVal(maquina.agregar((funcion)($1.obj)));
            }
;
proc: PROC {maquina.agregarOpera("dec");}
    ;
funcion: FUNC {maquina.agregarOpera("dec");}
        ;
nombreProcedimiento: VAR {$$ = new ParserVal(maquina.agregar($1.sval));}
                    ;
null : {maquina.agregar(null);}
     ;
stop: {$$ = new ParserVal(maquina.agregarOpera("stop"));}
    ;
if: IF{
    $$ = new ParserVal(maquina.agregarOpera("IFelse"));
    maquina.agregarOpera("stop");//THEN
    maquina.agregarOpera("stop");//ELSE
    maquina.agregarOpera("stop");
    }
    ;
while: WHILE{
    $$ = new ParserVal(maquina.agregarOpera("WHILE"));
    maquina.agregarOpera("stop");//codigo
    maquina.agregarOpera("stop");//final
    }
    ;
for: FOR{
    $$ = new ParserVal(maquina.agregarOpera("FOR"));
    maquina.agregarOpera("stop");
    maquina.agregarOpera("stop");
    maquina.agregarOpera("stop");
    maquina.agregarOpera("stop");
    }
    ;
instr: {$$ = new ParserVal(maquina.agregarOpera("no"));}
     | expresion {$$ = $1;}
     | instr ',' expresion {$$ = $1;}
     ;

%%

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