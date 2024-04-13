package com.navi.backend.flexycup;
import java_cup.runtime.*;
import java.util.ArrayList;
import static com.navi.backend.flexycup.sym.*;
%% //separador de area

%public
%class XMLLexer
%cup
%line
%column

/* no case-sensitive */
a = [aA]
b = [bB]
c = [cC]
//d = [dD]
e = [eE]
//f = [fF]
//g = [gG]
//h = [hH]
i = [iI]
//j = [jJ]
//k = [kK]
l = [lL]
m = [mM]
n = [nN]
o = [oO]
p = [pP]
q = [qQ]
r = [rR]
s = [sS]
t = [tT]
u = [uU]
v = [vV]
//w = [wW]
//x = [xX]
//y = [yY]
//z = [zZ]

//new_site = "\""{n}{u}{e}{v}{o}"_"{s}{i}{t}{i}{o}"_"{w}{e}{b}"\""

acciones = {a}{c}{c}{i}{o}{n}{e}{s}
accion = {a}{c}{c}{i}{o}{n}
parametros = {p}{a}{r}{a}{m}{e}{t}{r}{o}{s}
parametro = {p}{a}{r}{a}{m}{e}{t}{r}{o}
atributos = {a}{t}{r}{i}{b}{u}{t}{o}{s}
atributo = {a}{t}{r}{i}{b}{u}{t}{o}
etiquetas = {e}{t}{i}{q}{u}{e}{t}{a}{s}
etiqueta = {e}{t}{i}{q}{u}{e}{t}{a}
nombre = {n}{o}{m}{b}{r}{e}
valor = {v}{a}{l}{o}{r}


LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
param = \[[^\]]*\]
Cadena = \"([^\"]*)\"

%{
    public static ArrayList<TError> errors = new ArrayList<>();

    private Symbol symbol(int type){
        System.out.println(yytext() + " " + type);
        return new Symbol(type, yyline+1,yycolumn+1);
    }
    private Symbol symbol(int type, Object value){
        System.out.println(yytext() + " " + type);
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
    private void error(){
        System.out.println("Error en linea: "+(yyline+1)+", columna: "+(yycolumn+1));
        TError err = new TError(yytext(), "Error Léxico", "Símbolo inválido", yyline+1, yycolumn+1);
        errors.add(err);
    }
%}

%%
//Reglas lexicas
    {acciones}                           { return symbol(ACCIONES, yytext());        }
    {accion}                             { return symbol(ACCION, yytext());          }
    {nombre}                             { return symbol(NOMBRE, yytext());     }
    {parametros}                         { return symbol(PARAMETROS, yytext());      }
    {parametro}                          { return symbol(PARAMETRO, yytext());      }
    {etiquetas}                          { return symbol(ETIQUETAS, yytext());           }
    {etiqueta}                           { return symbol(ETIQUETA, yytext());            }
    {valor}                              { return symbol(VALOR, yytext());   }
    {atributos}                          { return symbol(ATRIBUTOS, yytext());       }
    {atributo}                           { return symbol(ATRIBUTO, yytext());      }
    "\"NUEVO_SITIO_WEB\""                { return symbol(NUEVO_SITIO_WEB, yytext());      }
    "\"BORRRAR_SITIO_WEB\""              { return symbol(BORRRAR_SITIO_WEB, yytext());      }
    "\"NUEVA_PAGINA\""                   { return symbol(NUEVA_PAGINA, yytext());      }
    "\"BORRAR_PAGINA\""                  { return symbol(BORRAR_PAGINA, yytext());      }
    "\"MODIFICAR_PAGINA\""               { return symbol(MODIFICAR_PAGINA, yytext());      }
    "\"AGREGAR_COMPONENTE\""             { return symbol(AGREGAR_COMPONENTE, yytext());      }
    "\"BORRAR_COMPONENTE\""              { return symbol(BORRAR_COMPONENTE, yytext());      }
    "\"MODIFICAR_COMPONENTE\""           { return symbol(MODIFICAR_COMPONENTE, yytext());      }
    "\"ID\""                             { return symbol(ID, yytext());      }
    "\"USUARIO_CREACION\""               { return symbol(USUARIO_CREACION, yytext());      }
    "\"FECHA_CREACION\""                 { return symbol(FECHA_CREACION, yytext());      }
    "\"FECHA_MODIFICACION\""             { return symbol(FECHA_MODIFICACION, yytext());      }
    "\"USUARIO_MODIFICACION\""           { return symbol(USUARIO_MODIFICACION, yytext());      }
    "\"TITULO\""                         { return symbol(TITULO, yytext());      }
    "\"SITIO\""                          { return symbol(SITIO, yytext());      }
    "\"PADRE\""                          { return symbol(PADRE, yytext());      }
    "\"PAGINA\""                         { return symbol(PAGINA, yytext());      }
    "\"CLASE\""                          { return symbol(CLASE, yytext());      }
    "\"TEXTO\""                          { return symbol(TEXTO, yytext());      }
    "\"ALINEACION\""                     { return symbol(ALINEACION, yytext());      }
    "\"COLOR\""                          { return symbol(COLOR, yytext());      }
    "\"ORIGEN\""                         { return symbol(ORIGEN, yytext());      }
    "\"ALTURA\""                         { return symbol(ALTURA, yytext());      }
    "\"ANCHO\""                          { return symbol(ANCHO, yytext());      }
    "\"ETIQUETAS\""                      { return symbol(ETIQUETAS_NOMBRE, yytext());      }
    "/"                                  { return symbol(SLASH, yytext());    }
    "<"                                  { return symbol(MENOR, yytext());       }
    ">"                                  { return symbol(MAYOR, yytext());      }
    {param}                              { return symbol(PARAM, yytext());            }
    {Cadena}                             { return symbol(CADENA, yytext().replaceAll("\"",""));        }
    "="                                  { return symbol(EQUAL, yytext());         }
    {WhiteSpace}                         { /**/ }
[^]                                      {error(); }


<<EOF>>             {return symbol(EOF); }